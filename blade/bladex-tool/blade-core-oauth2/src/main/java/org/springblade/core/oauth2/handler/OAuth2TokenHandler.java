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
package org.springblade.core.oauth2.handler;

import lombok.RequiredArgsConstructor;
import org.springblade.core.jwt.JwtUtil;
import org.springblade.core.jwt.props.JwtProperties;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.provider.OAuth2Token;
import org.springblade.core.oauth2.service.OAuth2User;

/**
 * BladeTokenHandler
 *
 * @author BladeX
 */
@RequiredArgsConstructor
public class OAuth2TokenHandler implements TokenHandler {

	private final JwtProperties properties;

	/**
	 * 令牌增强
	 *
	 * @param user    用户信息
	 * @param token   令牌信息
	 * @param request 授权参数
	 * @return OAuth2Token
	 */
	@Override
	public OAuth2Token enhance(OAuth2User user, OAuth2Token token, OAuth2Request request) {

		//令牌状态配置, 仅在生成AccessToken时候执行
		if (properties.getState() && token.hasAccessToken()) {
			JwtUtil.addAccessToken(
				user.getTenantId(),
				user.getClient().getClientId(),
				user.getUserId(),
				token.getAccessToken(),
				token.getAccessTokenExpire()
			);
		}
		//令牌状态配置, 仅在生成RefreshToken时候执行
		if (properties.getState() && properties.getSingle() && token.hasRefreshToken()) {
			JwtUtil.addRefreshToken(
				user.getTenantId(),
				user.getClient().getClientId(),
				user.getUserId(),
				token.getRefreshToken(),
				token.getRefreshTokenExpire()
			);
		}

		// 返回令牌
		return token;
	}
}
