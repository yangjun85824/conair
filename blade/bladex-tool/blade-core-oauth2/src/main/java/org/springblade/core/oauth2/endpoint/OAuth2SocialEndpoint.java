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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springblade.core.social.props.SocialProperties;
import org.springblade.core.social.utils.SocialUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 第三方登录端点
 *
 * @author BladeX
 */
@Slf4j
@RestController
@AllArgsConstructor
@ConditionalOnProperty(value = "social.enabled", havingValue = "true")
@Tag(name = "开放平台登录", description = "3 - 开放平台登录端点")
public class OAuth2SocialEndpoint {

	private final SocialProperties socialProperties;

	/**
	 * 授权完毕跳转
	 */
	@Operation(summary = "授权完毕跳转")
	@RequestMapping("/oauth/render/{source}")
	public void renderAuth(@PathVariable("source") String source, HttpServletResponse response) throws IOException {
		AuthRequest authRequest = SocialUtil.getAuthRequest(source, socialProperties);
		String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
		response.sendRedirect(authorizeUrl);
	}

	/**
	 * 获取认证信息
	 */
	@Operation(summary = "获取认证信息")
	@RequestMapping("/oauth/callback/{source}")
	public Object login(@PathVariable("source") String source, AuthCallback callback) {
		AuthRequest authRequest = SocialUtil.getAuthRequest(source, socialProperties);
		return authRequest.login(callback);
	}

	/**
	 * 撤销授权
	 */
	@Operation(summary = "撤销授权")
	@RequestMapping("/oauth/revoke/{source}/{token}")
	public Object revokeAuth(@PathVariable("source") String source, @PathVariable("token") String token) {
		AuthRequest authRequest = SocialUtil.getAuthRequest(source, socialProperties);
		return authRequest.revoke(AuthToken.builder().accessToken(token).build());
	}

	/**
	 * 续期accessToken
	 */
	@Operation(summary = "续期令牌")
	@RequestMapping("/oauth/refresh/{source}")
	public Object refreshAuth(@PathVariable("source") String source, String token) {
		AuthRequest authRequest = SocialUtil.getAuthRequest(source, socialProperties);
		return authRequest.refresh(AuthToken.builder().refreshToken(token).build());
	}


}
