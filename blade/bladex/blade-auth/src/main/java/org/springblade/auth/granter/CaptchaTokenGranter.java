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
package org.springblade.auth.granter;

import org.springblade.core.oauth2.constant.OAuth2TokenConstant;
import org.springblade.core.oauth2.exception.UserInvalidException;
import org.springblade.core.oauth2.granter.PasswordTokenGranter;
import org.springblade.core.oauth2.handler.PasswordHandler;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.stereotype.Component;

/**
 * CaptchaTokenGranter
 *
 * @author BladeX
 */
@Component
public class CaptchaTokenGranter extends PasswordTokenGranter {

	private final BladeRedis bladeRedis;

	public CaptchaTokenGranter(OAuth2ClientService clientService, OAuth2UserService userService, PasswordHandler passwordHandler, BladeRedis bladeRedis) {
		super(clientService, userService, passwordHandler);
		this.bladeRedis = bladeRedis;
	}

	@Override
	public String type() {
		return CAPTCHA;
	}

	@Override
	public OAuth2User user(OAuth2Request request) {
		// 获取验证码信息
		String key = request.getCaptchaKey();
		String code = request.getCaptchaCode();
		// 获取验证码
		String redisCode = bladeRedis.get(OAuth2TokenConstant.CAPTCHA_CACHE_KEY + key);
		// 判断验证码
		if (code == null || !StringUtil.equalsIgnoreCase(redisCode, code)) {
			throw new UserInvalidException(OAuth2TokenConstant.CAPTCHA_NOT_CORRECT);
		}
		return super.user(request);
	}
}
