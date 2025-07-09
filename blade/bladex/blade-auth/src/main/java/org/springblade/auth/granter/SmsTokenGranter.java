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

import jakarta.servlet.http.HttpServletRequest;
import org.springblade.core.oauth2.constant.OAuth2TokenConstant;
import org.springblade.core.oauth2.exception.UserInvalidException;
import org.springblade.core.oauth2.granter.AbstractTokenGranter;
import org.springblade.core.oauth2.handler.PasswordHandler;
import org.springblade.core.oauth2.props.OAuth2Properties;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.sms.model.SmsCode;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.SM2Util;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springblade.resource.feign.ISmsClient;
import org.springframework.stereotype.Component;

/**
 * SmsTokenGranter
 *
 * @author BladeX
 */
@Component
public class SmsTokenGranter extends AbstractTokenGranter {

	private final OAuth2UserService userService;
	private final ISmsClient smsClient;
	private final OAuth2Properties properties;

	public SmsTokenGranter(OAuth2ClientService clientService, OAuth2UserService userService, PasswordHandler passwordHandler, ISmsClient smsClient, OAuth2Properties properties) {
		super(clientService, userService, passwordHandler);
		this.userService = userService;
		this.smsClient = smsClient;
		this.properties = properties;
	}

	@Override
	public String type() {
		return SMS_CODE;
	}

	@Override
	public OAuth2User user(OAuth2Request request) {
		// 获取基础信息
		String tenantId = request.getTenantId();
		SmsCode smsCode = buildSmsCode();
		// 校验手机加密认证
		String decryptedPhone = SM2Util.decrypt(smsCode.getPhone(), properties.getPublicKey(), properties.getPrivateKey());
		if (StringUtil.isBlank(decryptedPhone)) {
			throw new UserInvalidException(OAuth2TokenConstant.USER_PHONE_NOT_FOUND);
		}
		// 获取短信验证信息
		R result = smsClient.validateMessage(tenantId, StringPool.EMPTY, smsCode.getId(), smsCode.getValue(), decryptedPhone);
		if (!result.isSuccess()) {
			throw new UserInvalidException(OAuth2TokenConstant.CAPTCHA_NOT_CORRECT);
		}
		// 获取用户信息
		OAuth2User user = userService.loadByPhone(decryptedPhone, request);
		// 校验用户信息
		if (!userService.validateUser(user)) {
			throw new UserInvalidException(OAuth2TokenConstant.TOKEN_NOT_CORRECT);
		}
		// 设置客户端信息
		user.setClient(client(request));
		return user;
	}

	private SmsCode buildSmsCode() {
		HttpServletRequest request = WebUtil.getRequest();
		return new SmsCode().setId(request.getParameter("id"))
			.setPhone(request.getParameter("phone"))
			.setValue(request.getParameter("value"));
	}
}
