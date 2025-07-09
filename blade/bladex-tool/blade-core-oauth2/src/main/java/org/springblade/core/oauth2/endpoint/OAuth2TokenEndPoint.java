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

import com.wf.captcha.SpecCaptcha;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.jwt.JwtUtil;
import org.springblade.core.jwt.props.JwtProperties;
import org.springblade.core.oauth2.constant.OAuth2ParameterConstant;
import org.springblade.core.oauth2.granter.TokenGranter;
import org.springblade.core.oauth2.granter.TokenGranterFactory;
import org.springblade.core.oauth2.handler.AuthorizationHandler;
import org.springblade.core.oauth2.handler.TokenHandler;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.provider.OAuth2Response;
import org.springblade.core.oauth2.provider.OAuth2Token;
import org.springblade.core.oauth2.provider.OAuth2Validation;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.utils.OAuth2ExceptionUtil;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.support.Kv;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

import static org.springblade.core.cache.constant.CacheConstant.*;
import static org.springblade.core.oauth2.constant.OAuth2TokenConstant.CAPTCHA_CACHE_KEY;

/**
 * OAuth2认证端点
 *
 * @author BladeX
 */
@RestController
@AllArgsConstructor
@Tag(name = "用户授权认证", description = "1 - OAuth2授权认证端点")
public class OAuth2TokenEndPoint {

	private final BladeRedis bladeRedis;
	private final JwtProperties jwtProperties;

	private final TokenGranterFactory granterFactory;
	private final AuthorizationHandler authorizationHandler;
	private final TokenHandler tokenHandler;


	@PostMapping("/oauth/token")
	@Operation(
		summary = "获取Token",
		description = "OAuth2认证接口",
		parameters = {
			@Parameter(in = ParameterIn.QUERY, name = OAuth2ParameterConstant.USERNAME, description = "账号", schema = @Schema(type = "string")),
			@Parameter(in = ParameterIn.QUERY, name = OAuth2ParameterConstant.PASSWORD, description = "密码", schema = @Schema(type = "string")),
			@Parameter(in = ParameterIn.QUERY, name = OAuth2ParameterConstant.GRANT_TYPE, description = "授权类型", schema = @Schema(type = "string")),
			@Parameter(in = ParameterIn.QUERY, name = OAuth2ParameterConstant.REFRESH_TOKEN, description = "刷新token", schema = @Schema(type = "string")),
			@Parameter(in = ParameterIn.QUERY, name = OAuth2ParameterConstant.SCOPE, description = "权限范围", schema = @Schema(type = "string"))
		}
	)
	public ResponseEntity<Kv> token() {
		// 创建 OAuth2 请求对象并构建参数
		OAuth2Request request = OAuth2Request.create().buildArgs();

		// 根据请求的授权类型创建对应的 TokenGranter
		TokenGranter tokenGranter = granterFactory.create(request.getGrantType());

		// 使用 TokenGranter 获取用户信息
		OAuth2User user = tokenGranter.user(request);

		// 使用授权处理器对用户进行验证
		OAuth2Validation validation = authorizationHandler.authValidation(user, request);

		// 检查验证是否成功
		if (!validation.isSuccess()) {
			// 验证失败处理逻辑
			authorizationHandler.authFailure(user, request, validation);

			// 根据验证失败的错误代码抛出异常
			OAuth2ExceptionUtil.throwFromCode(validation.getCode());
		}

		// 创建令牌
		OAuth2Token token = tokenGranter.token(user, request);

		// 对令牌进行增强处理
		OAuth2Token enhanceToken = tokenHandler.enhance(user, token, request);

		// 验证成功处理逻辑
		authorizationHandler.authSuccessful(user, request);

		// 返回增强后的令牌
		return ResponseEntity.ok(enhanceToken.getArgs());
	}


	@GetMapping("/oauth/logout")
	@Operation(summary = "退出登录")
	public ResponseEntity<Kv> logout() {
		BladeUser user = AuthUtil.getUser();
		if (user != null && jwtProperties.getState()) {
			OAuth2Request request = OAuth2Request.create().buildHeaderArgs();
			String token = JwtUtil.getToken(request.getToken());
			JwtUtil.removeAccessToken(user.getTenantId(), user.getClientId(), String.valueOf(user.getUserId()), token);
			JwtUtil.removeRefreshToken(user.getTenantId(), user.getClientId(), String.valueOf(user.getUserId()), token);
		}
		return ResponseEntity.ok(OAuth2Response.create().ofSuccessful("退出登录成功"));
	}

	@GetMapping("/oauth/captcha")
	@Operation(summary = "获取验证码")
	public ResponseEntity<Kv> captcha() {
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
		String verCode = specCaptcha.text().toLowerCase();
		String key = UUID.randomUUID().toString();
		// 存入redis并设置过期时间为30分钟
		bladeRedis.setEx(CAPTCHA_CACHE_KEY + key, verCode, Duration.ofMinutes(30));
		// 将key和base64返回给前端
		return ResponseEntity.ok(OAuth2Response.create().ofSuccessful("获取验证码成功").set("key", key).set("image", specCaptcha.toBase64()));
	}

	@GetMapping("/oauth/clear-cache")
	@Operation(summary = "清除缓存")
	public ResponseEntity<Kv> clearCache() {
		CacheUtil.clear(BIZ_CACHE);
		CacheUtil.clear(USER_CACHE);
		CacheUtil.clear(DICT_CACHE);
		CacheUtil.clear(FLOW_CACHE);
		CacheUtil.clear(SYS_CACHE);
		CacheUtil.clear(PARAM_CACHE);
		CacheUtil.clear(RESOURCE_CACHE);
		CacheUtil.clear(MENU_CACHE);
		CacheUtil.clear(DICT_CACHE, Boolean.FALSE);
		CacheUtil.clear(MENU_CACHE, Boolean.FALSE);
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		CacheUtil.clear(PARAM_CACHE, Boolean.FALSE);
		return ResponseEntity.ok(OAuth2Response.create().ofSuccessful("清除缓存成功"));
	}
}
