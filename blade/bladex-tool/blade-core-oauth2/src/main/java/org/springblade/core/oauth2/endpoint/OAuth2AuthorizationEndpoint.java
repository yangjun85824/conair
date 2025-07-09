/**
 * BladeX Commercial License Agreement
 * Copyright (c) 2018-2099, https://bladex.cn. All rights reserved.
 * <p>
 * Use of this software is governed by the Commercial License Agreement
 * obtained after purchasing a license from BladeX.
 * <p>
 * 1. This software is for development use only under a valid license
 * from BladeX.
 * <p>
 * 2. Redistribution of this software's source code to any third party
 * without a commercial license is strictly prohibited.
 * <p>
 * 3. Licensees may copyright their own code but cannot use segments
 * from this software for such purposes. Copyright of this software
 * remains with BladeX.
 * <p>
 * Using this software signifies agreement to this License, and the software
 * must not be used for illegal purposes.
 * <p>
 * THIS SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY. The author is
 * not liable for any claims arising from secondary or illegal development.
 * <p>
 * Author: Chill Zhuang (bladejava@qq.com)
 */
package org.springblade.core.oauth2.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springblade.core.oauth2.constant.OAuth2AuthorizationConstant;
import org.springblade.core.oauth2.handler.PasswordHandler;
import org.springblade.core.oauth2.props.OAuth2Properties;
import org.springblade.core.oauth2.provider.OAuth2AuthorizationRequest;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2Client;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.oauth2.utils.OAuth2CodeUtil;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.util.Optional;

import static org.springblade.core.oauth2.constant.OAuth2ParameterConstant.*;

/**
 * AuthorizationEndpoint
 *
 * @author BladeX
 */
@Controller
@RequiredArgsConstructor
@Tag(name = "用户授权码模式认证", description = "2 - OAuth2授权码模式端点")
public class OAuth2AuthorizationEndpoint implements OAuth2AuthorizationConstant {

	private final BladeRedis bladeRedis;

	private final OAuth2ClientService clientService;

	private final OAuth2UserService userService;

	private final PasswordHandler passwordHandler;

	private final OAuth2Properties oAuth2Properties;

	@GetMapping("/oauth/login")
	public String loginPage(HttpSession session, Model model) {
		// 从session中获取授权请求参数
		Optional.ofNullable((OAuth2AuthorizationRequest) session.getAttribute(AUTHORIZATION_REQUEST_KEY))
			.ifPresent(authorizationRequest -> {
				model.addAttribute(PUBLIC_KEY, oAuth2Properties.getPublicKey());
				model.addAttribute(AUTHORIZATION_REQUEST_KEY, authorizationRequest);
			});
		// 返回登录页面视图
		return LOGIN_MODEL;
	}


	@PostMapping("/oauth/login/perform")
	public String performLogin(@SessionAttribute(AUTHORIZATION_REQUEST_KEY) OAuth2AuthorizationRequest authorizationRequest,
							   RedirectAttributes redirectAttributes, HttpSession session) {
		// 根据用户名和密码验证用户
		return Optional.ofNullable(authenticateUser(session, authorizationRequest))
			.map(user -> {
				// 用户验证成功，处理授权请求参数和重定向
				authorizationRequest.setTenantId(user.getTenantId());
				session.setAttribute(AUTHORIZATION_REQUEST_KEY, authorizationRequest);
				redirectAttributes.addAllAttributes(authorizationRequest.getParameters());
				return REDIRECT_URL + AUTHORIZE_URL; // 重定向回授权视图
			})
			.orElse(REDIRECT_URL + ERROR_URL); // 用户验证失败，重定向回失败视图
	}

	@GetMapping("/oauth/authorize")
	public String authorize(@SessionAttribute(value = AUTHORIZATION_REQUEST_KEY, required = false) OAuth2AuthorizationRequest authorizationRequest,
							HttpSession session, Model model) {
		// 获取授权请求参数
		OAuth2AuthorizationRequest request = OAuth2AuthorizationRequest.create().buildParameters();

		// 设置请求参数
		Optional.ofNullable(authorizationRequest).ifPresentOrElse(authReq -> {
			if (request.getState() == null) {
				request.setState(authReq.getState());
			}
			if (request.getClientId() != null) {
				session.setAttribute(AUTHORIZATION_REQUEST_KEY, request);
			}
		}, () -> session.setAttribute(AUTHORIZATION_REQUEST_KEY, request));

		// 获取用户信息并跳转
		return Optional.ofNullable(session.getAttribute(AUTHORIZATION_SESSION_KEY))
			.map(obj -> (OAuth2User) obj)
			.map(user -> {
				String clientId = request.getParameters().get(CLIENT_ID);
				OAuth2Client client = clientService.loadByClientId(clientId);
				if (client == null) {
					return ERROR_MODEL;
				}
				model.addAttribute(AUTO_APPROVE, client.getAutoapprove());
				model.addAttribute(USERNAME, user.getAccount());
				model.addAllAttributes(request.getParameters());
				return AUTHORIZE_MODEL; // 用户已登录，显示授权页面
			})
			.orElse(REDIRECT_URL + LOGIN_URL); // 用户未登录，重定向到登录页面
	}

	@PostMapping("/oauth/authorize/perform")
	public String performAuthorize(@RequestParam(required = false) String state,
								   @SessionAttribute(AUTHORIZATION_REQUEST_KEY) OAuth2AuthorizationRequest authorizationRequest,
								   RedirectAttributes redirectAttributes, HttpSession session) {

		// 获取客户端信息
		OAuth2Client client = clientService.loadByClientId(authorizationRequest.getClientId());

		// 校验回调地址信息
		if (!clientService.validateRedirectUri(client, authorizationRequest.getRedirectUri())) {
			// 重定向URI参数不匹配，返回错误页面
			return ERROR_MODEL;
		}

		// 生成授权码
		String code = createCode();

		// 设置用户信息
		OAuth2User user = (OAuth2User) session.getAttribute(AUTHORIZATION_SESSION_KEY);
		if (user == null) {
			// 用户未登录，重定向到登录页面
			return REDIRECT_URL + LOGIN_URL;
		}

		// 校验state参数
		if (Func.equalsSafe(authorizationRequest.getState(), state)) {
			// 保存授权码
			saveCode(code, user);
		} else {
			// 重定向URI和state参数不匹配，返回错误页面
			return ERROR_MODEL;
		}

		// 使用RedirectAttributes添加授权码和state参数
		redirectAttributes.addAttribute(CODE, code);

		// 添加tenantId参数为state参数
		if (authorizationRequest.getTenantId() != null) {
			redirectAttributes.addAttribute(STATE, authorizationRequest.getTenantId());
		}
		// 用户自定义state参数则覆盖
		if (state != null) {
			redirectAttributes.addAttribute(STATE, state);
		}

		// 重定向到客户端提供的重定向URI
		return REDIRECT_URL + authorizationRequest.getRedirectUri();
	}

	@GetMapping("/oauth/authorize/logout")
	public String logout(HttpSession session) {
		// 退出登录，清除session中的用户信息
		session.removeAttribute(AUTHORIZATION_SESSION_KEY);
		return REDIRECT_URL + LOGIN_URL;
	}

	@GetMapping("/oauth/error")
	public String error() {
		// 返回错误页面
		return ERROR_MODEL;
	}

	private OAuth2User authenticateUser(HttpSession session, OAuth2AuthorizationRequest authorizationRequest) {
		// 创建 OAuth2 请求对象并构建参数
		OAuth2Request request = OAuth2Request.create().buildParameterArgs().buildHeaderArgs();

		// 获取请求参数
		String username = request.getUsername();
		String password = request.getPassword();
		String clientId = authorizationRequest.getClientId();
		String redirectUri = authorizationRequest.getRedirectUri();

		// 获取客户端信息
		OAuth2Client client = clientService.loadByClientId(clientId);

		// 校验回调地址信息
		if (!clientService.validateRedirectUri(client, redirectUri)) {
			return null;
		}

		// 获取用户信息
		OAuth2User user = userService.loadByUsername(username, request);

		// 校验用户信息
		if (!userService.validateUser(user)) {
			return null;
		}

		// 校验用户密码
		if (!passwordHandler.matches(password, user.getPassword())) {
			return null;
		}

		// 将用户信息存入session
		session.setAttribute(AUTHORIZATION_SESSION_KEY, user);

		// 返回用户信息
		return user;
	}

	/**
	 * 授权码模式获取授权码
	 *
	 * @return 授权码
	 */
	private String createCode() {
		// 生成6位随机数作为授权码
		String code = StringUtil.random(6);
		if (bladeRedis.exists(OAuth2CodeUtil.codeKey(code))) {
			// 如果生成的授权码已存在，则递归调用重新生成
			return createCode();
		}
		return code;
	}

	/**
	 * 保存code信息
	 */
	private void saveCode(String code, OAuth2User user) {
		// 保存code信息到redis，30分钟过期
		bladeRedis.setEx(OAuth2CodeUtil.codeKey(code), user, Duration.ofMinutes(30));
	}

	/**
	 * 根据code获取用户信息
	 *
	 * @param code code
	 * @return 用户信息
	 */
	public OAuth2User getUserByCode(String code) {
		// 根据code从redis中获取用户信息
		return bladeRedis.get(OAuth2CodeUtil.codeKey(code));
	}

}
