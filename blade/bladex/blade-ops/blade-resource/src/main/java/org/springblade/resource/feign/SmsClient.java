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
package org.springblade.resource.feign;

import lombok.AllArgsConstructor;
import org.springblade.core.sms.model.SmsCode;
import org.springblade.core.sms.model.SmsData;
import org.springblade.core.sms.model.SmsResponse;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.resource.builder.SmsBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springblade.resource.utils.SmsUtil.*;

/**
 * 短信远程调用服务
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
public class SmsClient implements ISmsClient {

	private final SmsBuilder smsBuilder;

	@Override
	@PostMapping(SEND_MESSAGE)
	public R<SmsResponse> sendMessage(String code, String params, String phones) {
		SmsData smsData = new SmsData(JsonUtil.readMap(params, String.class, String.class));
		SmsResponse response = smsBuilder.template(code).sendMessage(smsData, Func.toStrList(phones));
		return R.data(response);
	}

	@Override
	@PostMapping(SEND_MESSAGE_BY_TENANT_ID)
	public R<SmsResponse> sendMessage(String tenantId, String code, String params, String phones) {
		SmsData smsData = new SmsData(JsonUtil.readMap(params, String.class, String.class));
		SmsResponse response = smsBuilder.template(tenantId, code).sendMessage(smsData, Func.toStrList(phones));
		return R.data(response);
	}

	@Override
	@PostMapping(SEND_VALIDATE)
	public R sendValidate(String code, String phone) {
		Map<String, String> params = getValidateParams();
		SmsCode smsCode = smsBuilder.template(code).sendValidate(new SmsData(params).setKey(PARAM_KEY), phone);
		return smsCode.isSuccess() ? R.data(smsCode, SEND_SUCCESS) : R.fail(SEND_FAIL);
	}

	@Override
	@PostMapping(SEND_VALIDATE_BY_TENANT_ID)
	public R sendValidate(String tenantId, String code, String phone) {
		Map<String, String> params = getValidateParams();
		SmsCode smsCode = smsBuilder.template(tenantId, code).sendValidate(new SmsData(params).setKey(PARAM_KEY), phone);
		return smsCode.isSuccess() ? R.data(smsCode, SEND_SUCCESS) : R.fail(SEND_FAIL);
	}

	@Override
	@PostMapping(VALIDATE_MESSAGE)
	public R validateMessage(String code, String id, String value, String phone) {
		SmsCode smsCode = new SmsCode().setId(id).setValue(value).setPhone(phone);
		boolean validate = smsBuilder.template(code).validateMessage(smsCode);
		return validate ? R.success(VALIDATE_SUCCESS) : R.fail(VALIDATE_FAIL);
	}

	@Override
	@PostMapping(VALIDATE_MESSAGE_BY_TENANT_ID)
	public R validateMessage(String tenantId, String code, String id, String value, String phone) {
		SmsCode smsCode = new SmsCode().setId(id).setValue(value).setPhone(phone);
		boolean validate = smsBuilder.template(tenantId, code).validateMessage(smsCode);
		return validate ? R.success(VALIDATE_SUCCESS) : R.fail(VALIDATE_FAIL);
	}
}
