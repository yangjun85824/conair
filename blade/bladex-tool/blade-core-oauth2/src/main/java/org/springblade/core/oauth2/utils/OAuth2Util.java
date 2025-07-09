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
package org.springblade.core.oauth2.utils;

import org.springblade.core.oauth2.service.OAuth2Client;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.secure.TokenInfo;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.support.Kv;

import static org.springblade.core.launch.constant.TokenConstant.*;

/**
 * OAuth2Util
 *
 * @author BladeX
 */
public class OAuth2Util extends SecureUtil {

	/**
	 * 创建accessToken
	 *
	 * @param user 用户信息
	 * @return accessToken
	 */
	public static TokenInfo createAccessToken(OAuth2User user) {
		Kv kv = Kv.create().set(TOKEN_TYPE, ACCESS_TOKEN)
			.set(CLIENT_ID, user.getClient().getClientId())
			.set(TENANT_ID, user.getTenantId())
			.set(USER_ID, user.getUserId())
			.set(DEPT_ID, user.getDeptId())
			.set(POST_ID, user.getPostId())
			.set(ROLE_ID, user.getRoleId())
			.set(OAUTH_ID, user.getOauthId())
			.set(ACCOUNT, user.getAccount())
			.set(USER_NAME, user.getAccount())
			.set(NICK_NAME, user.getName())
			.set(REAL_NAME, user.getRealName())
			.set(ROLE_NAME, user.getRoleName())
			.set(DETAIL, user.getDetail());
		return createToken(kv, user.getClient().getAccessTokenValidity());
	}

	/**
	 * 创建refreshToken
	 *
	 * @param user 用户信息
	 * @return refreshToken
	 */
	public static TokenInfo createRefreshToken(OAuth2User user) {
		Kv kv = Kv.create().set(TOKEN_TYPE, REFRESH_TOKEN)
			.set(USER_ID, user.getUserId())
			.set(DEPT_ID, user.getDeptId())
			.set(ROLE_ID, user.getRoleId());
		return createToken(kv, user.getClient().getRefreshTokenValidity());
	}


	/**
	 * 创建clientAccessToken
	 *
	 * @param client 客户端信息
	 * @return clientToken
	 */
	public static TokenInfo createClientAccessToken(OAuth2Client client) {
		Kv kv = Kv.create().set(TOKEN_TYPE, CLIENT_ACCESS_TOKEN)
			.set(CLIENT_ID, client.getClientId());
		return createToken(kv, client.getAccessTokenValidity());
	}

	/**
	 * 创建implicitAccessToken
	 *
	 * @param user 用户信息
	 * @return implicitAccessToken
	 */
	public static TokenInfo createImplicitAccessToken(OAuth2User user) {
		Kv kv = Kv.create().set(TOKEN_TYPE, IMPLICIT_ACCESS_TOKEN)
			.set(ACCOUNT, user.getAccount());
		return createToken(kv);
	}


}
