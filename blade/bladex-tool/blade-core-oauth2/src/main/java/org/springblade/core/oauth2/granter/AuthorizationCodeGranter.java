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

import org.springblade.core.oauth2.exception.ExceptionCode;
import org.springblade.core.oauth2.exception.OAuth2ErrorCode;
import org.springblade.core.oauth2.exception.UserInvalidException;
import org.springblade.core.oauth2.handler.PasswordHandler;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2Client;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.oauth2.utils.OAuth2CodeUtil;
import org.springblade.core.oauth2.utils.OAuth2ExceptionUtil;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.utils.ObjectUtil;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * AuthorizationCodeGranter
 *
 * @author BladeX
 */
@Component
public class AuthorizationCodeGranter extends AbstractTokenGranter {

	private final OAuth2UserService userService;
	private final PasswordHandler passwordHandler;
	private final BladeRedis bladeRedis;

	public AuthorizationCodeGranter(OAuth2ClientService clientService, OAuth2UserService userService, PasswordHandler passwordHandler, BladeRedis bladeRedis) {
		super(clientService, userService, passwordHandler);
		this.userService = userService;
		this.passwordHandler = passwordHandler;
		this.bladeRedis = bladeRedis;
	}

	@Override
	public String type() {
		return AUTHORIZATION_CODE;
	}

	@Override
	public OAuth2User user(OAuth2Request request) {
		// 获取客户端信息并校验
		OAuth2Client client = client(request);
		if (!StringUtil.equals(client.getWebServerRedirectUri(), request.getRedirectUri())) {
			OAuth2ExceptionUtil.throwFromCode(OAuth2ErrorCode.INVALID_CLIENT_REDIRECT_URI);
		}
		// 根据code获取用户信息
		String code = request.getCode();
		OAuth2User user = bladeRedis.get(OAuth2CodeUtil.codeKey(code));
		// 判断用户是否存在
		if (ObjectUtil.isNotEmpty(user)) {
			// 校验用户信息
			if (!userService.validateUser(user)) {
				OAuth2ExceptionUtil.throwFromCode(OAuth2ErrorCode.INVALID_USER);
			}
			// 校验用户密码
			if ((request.isCaptchaCode() || request.isPassword()) && !passwordHandler.matches(request.getPassword(), user.getPassword())) {
				OAuth2ExceptionUtil.throwFromCode(OAuth2ErrorCode.INVALID_USER);
			}
			// 设置客户端信息
			user.setClient(client);
			// 返回user
			return Optional.ofNullable(this.enhancer)
				.map(enhancer -> enhancer.enhance(user, request))
				.orElse(user);
		}
		throw new UserInvalidException(ExceptionCode.INVALID_USER.getMessage());
	}

}
