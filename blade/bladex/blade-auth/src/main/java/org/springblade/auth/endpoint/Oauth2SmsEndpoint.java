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
package org.springblade.auth.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springblade.core.oauth2.props.OAuth2Properties;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.SM2Util;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.resource.feign.ISmsClient;
import org.springblade.resource.utils.SmsUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springblade.core.oauth2.constant.OAuth2TokenConstant.USER_PHONE_NOT_FOUND;

/**
 * Oauth2SmsEndpoint
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@Tag(name = "用户短信认证", description = "4 - OAuth2短信认证端点")
public class Oauth2SmsEndpoint {

	/**
	 * 短信服务构建类
	 */
	private final ISmsClient smsClient;

	/**
	 * 用户服务类
	 */
	private final OAuth2UserService userService;

	/**
	 * OAuth2配置类
	 */
	private final OAuth2Properties properties;

	/**
	 * 短信验证码发送
	 *
	 * @param tenantId 租户ID
	 * @param phone    手机号
	 */
	@SneakyThrows
	@PostMapping("/oauth/sms/send-validate")
	public R sendValidate(@RequestParam String tenantId, @RequestParam String phone) {
		// 校验手机加密认证，防止恶意发送验证码
		String decryptedPhone = SM2Util.decrypt(phone, properties.getPublicKey(), properties.getPrivateKey());
		if (StringUtil.isBlank(decryptedPhone)) {
			return R.fail(USER_PHONE_NOT_FOUND);
		}
		// 校验手机是否已注册，防止恶意发送验证码
		OAuth2Request request = OAuth2Request.create();
		request.setTenantId(tenantId);
		OAuth2User oAuth2User = userService.loadByPhone(decryptedPhone, request);
		if (oAuth2User == null) {
			return R.fail(USER_PHONE_NOT_FOUND);
		}
		// 用户存在则发送验证码
		R result = smsClient.sendValidate(tenantId, StringPool.EMPTY, decryptedPhone);
		return result.isSuccess() ? R.data(result.getData(), SmsUtil.SEND_SUCCESS) : R.fail(SmsUtil.SEND_FAIL);
	}

}
