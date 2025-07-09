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
package org.springblade.core.oauth2.granter;

import lombok.RequiredArgsConstructor;
import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.oauth2.exception.OAuth2ErrorCode;
import org.springblade.core.oauth2.handler.PasswordHandler;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.provider.OAuth2Token;
import org.springblade.core.oauth2.service.OAuth2Client;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.oauth2.utils.OAuth2ExceptionUtil;
import org.springblade.core.oauth2.utils.OAuth2Util;
import org.springblade.core.secure.TokenInfo;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;

import java.util.Objects;

/**
 * AbstractTokenGranter
 *
 * @author BladeX
 */
@RequiredArgsConstructor
public abstract class AbstractTokenGranter implements TokenGranter {

	private final OAuth2ClientService clientService;
	private final OAuth2UserService userService;
	private final PasswordHandler passwordHandler;

	protected TokenGranterEnhancer enhancer;

	public abstract String type();

	@Override
	public void enhancer(TokenGranterEnhancer enhancer) {
		this.enhancer = enhancer;
	}

	@Override
	public OAuth2Client client(OAuth2Request request) {
		// 获取clientId
		String clientId = request.getClientId();
		// 获取clientSecret
		String clientSecret = request.getClientSecret();

		// 获取客户端信息
		OAuth2Client client = clientService.loadByClientId(clientId);

		// 校验客户端信息
		if (!clientService.validateClient(client, clientId, clientSecret)) {
			OAuth2ExceptionUtil.throwFromCode(OAuth2ErrorCode.INVALID_CLIENT);
		}

		// 校验授权类型
		if (!clientService.validateGranter(client, type())) {
			OAuth2ExceptionUtil.throwFromCode(OAuth2ErrorCode.INVALID_GRANTER);
		}

		// 返回客户端信息
		return client;
	}

	@Override
	public OAuth2User user(OAuth2Request request) {
		// 获取用户信息
		OAuth2User user = (StringUtil.isNotBlank(request.getUserId()) && request.isRefreshToken())
			? userService.loadByUserId(request.getUserId(), request)
			: userService.loadByUsername(request.getUsername(), request);

		// 用户不存在
		if (user == null) {
			OAuth2ExceptionUtil.throwFromCode(OAuth2ErrorCode.USER_NOT_FOUND);
		}

		// 校验用户信息
		if (!userService.validateUser(Objects.requireNonNull(user))) {
			OAuth2ExceptionUtil.throwFromCode(OAuth2ErrorCode.INVALID_USER);
		}

		// 校验用户密码
		if ((request.isCaptchaCode() || request.isPassword()) && !passwordHandler.matches(request.getPassword(), user.getPassword())) {
			OAuth2ExceptionUtil.throwFromCode(OAuth2ErrorCode.INVALID_USER);
		}

		// 设置客户端信息
		user.setClient(client(request));

		// 返回用户信息
		return user;
	}

	@Override
	public OAuth2Token token(OAuth2User user, OAuth2Request request) {
		TokenInfo accessToken = OAuth2Util.createAccessToken(user);
		TokenInfo refreshToken = OAuth2Util.createRefreshToken(user);

		OAuth2Token token = OAuth2Token.create();

		token.getArgs().set(TokenConstant.TENANT_ID, user.getTenantId())
			.set(TokenConstant.USER_ID, user.getUserId())
			.set(TokenConstant.DEPT_ID, user.getDeptId())
			.set(TokenConstant.POST_ID, user.getPostId())
			.set(TokenConstant.ROLE_ID, user.getRoleId())
			.set(TokenConstant.OAUTH_ID, user.getOauthId())
			.set(TokenConstant.ACCOUNT, user.getAccount())
			.set(TokenConstant.USER_NAME, user.getAccount())
			.set(TokenConstant.NICK_NAME, user.getName())
			.set(TokenConstant.REAL_NAME, user.getRealName())
			.set(TokenConstant.ROLE_NAME, Func.join(user.getAuthorities()))
			.set(TokenConstant.AVATAR, Func.toStr(user.getAvatar(), TokenConstant.DEFAULT_AVATAR))
			.set(TokenConstant.ACCESS_TOKEN, accessToken.getToken())
			.set(TokenConstant.REFRESH_TOKEN, refreshToken.getToken())
			.set(TokenConstant.TOKEN_TYPE, TokenConstant.BEARER)
			.set(TokenConstant.EXPIRES_IN, accessToken.getExpire())
			.set(TokenConstant.DETAIL, user.getDetail())
			.set(TokenConstant.LICENSE, TokenConstant.LICENSE_NAME);

		return token.setAccessToken(accessToken.getToken())
			.setAccessTokenExpire(accessToken.getExpire())
			.setRefreshToken(refreshToken.getToken())
			.setRefreshTokenExpire(refreshToken.getExpire());
	}


}
