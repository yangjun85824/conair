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

import org.springblade.core.oauth2.handler.PasswordHandler;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * PasswordTokenGranter
 *
 * @author BladeX
 */
@Component
public class PasswordTokenGranter extends AbstractTokenGranter {

	public PasswordTokenGranter(OAuth2ClientService clientService, OAuth2UserService userService, PasswordHandler passwordHandler) {
		super(clientService, userService, passwordHandler);
	}

	@Override
	public String type() {
		return PASSWORD;
	}

	@Override
	public OAuth2User user(OAuth2Request request) {
		OAuth2User user = super.user(request);
		return Optional.ofNullable(this.enhancer)
			.map(enhancer -> enhancer.enhance(user, request))
			.orElse(user);
	}

}
