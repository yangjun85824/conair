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

import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.oauth2.handler.PasswordHandler;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.provider.OAuth2Token;
import org.springblade.core.oauth2.service.OAuth2Client;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.oauth2.service.impl.OAuth2UserDetail;
import org.springblade.core.oauth2.utils.OAuth2Util;
import org.springblade.core.secure.TokenInfo;
import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * ClientCredentialsGranter
 *
 * @author BladeX
 */
@Component
public class ClientCredentialsGranter extends AbstractTokenGranter {

	public ClientCredentialsGranter(OAuth2ClientService clientService, OAuth2UserService userService, PasswordHandler passwordHandler) {
		super(clientService, userService, passwordHandler);
	}

	@Override
	public String type() {
		return CLIENT_CREDENTIALS;
	}

	@Override
	public OAuth2User user(OAuth2Request request) {
		OAuth2UserDetail user = new OAuth2UserDetail();
		OAuth2Client client = client(request);
		user.setClient(client);
		user.setAccount(client.getClientId());
		user.setName(client.getClientId());
		return Optional.ofNullable(this.enhancer)
			.map(enhancer -> enhancer.enhance(user, request))
			.orElse(user);
	}

	@Override
	public OAuth2Token token(OAuth2User user, OAuth2Request request) {
		TokenInfo accessToken = OAuth2Util.createClientAccessToken(user.getClient());

		OAuth2Token token = OAuth2Token.create();

		token.getArgs().set(TokenConstant.CLIENT_ID, user.getClient().getClientId())
			.set(TokenConstant.AVATAR, Func.toStr(user.getAvatar(), TokenConstant.DEFAULT_AVATAR))
			.set(TokenConstant.ACCESS_TOKEN, accessToken.getToken())
			.set(TokenConstant.TOKEN_TYPE, TokenConstant.BEARER)
			.set(TokenConstant.EXPIRES_IN, accessToken.getExpire())
			.set(TokenConstant.DETAIL, user.getDetail())
			.set(TokenConstant.LICENSE, TokenConstant.LICENSE_NAME);

		return token.setAccessToken(accessToken.getToken()).setAccessTokenExpire(accessToken.getExpire());
	}

}
