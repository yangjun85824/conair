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
package org.springblade.core.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import lombok.AllArgsConstructor;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.sms.model.SmsCode;
import org.springblade.core.sms.model.SmsData;
import org.springblade.core.sms.model.SmsResponse;
import org.springblade.core.sms.props.SmsProperties;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;

/**
 * 阿里云短信发送类
 *
 * @author Chill
 */
@AllArgsConstructor
public class AliSmsTemplate implements SmsTemplate {

	private static final int SUCCESS = 200;
	private static final String FAIL = "fail";
	private static final String OK = "ok";
	private static final String DOMAIN = "dysmsapi.aliyuncs.com";
	private static final String VERSION = "2017-05-25";
	private static final String ACTION = "SendSms";

	private final SmsProperties smsProperties;
	private final IAcsClient acsClient;
	private final BladeRedis bladeRedis;

	@Override
	public SmsResponse sendMessage(SmsData smsData, Collection<String> phones) {
		CommonRequest request = new CommonRequest();
		request.setSysMethod(MethodType.POST);
		request.setSysDomain(DOMAIN);
		request.setSysVersion(VERSION);
		request.setSysAction(ACTION);
		request.putQueryParameter("PhoneNumbers", StringUtil.join(phones));
		request.putQueryParameter("TemplateCode", smsProperties.getTemplateId());
		request.putQueryParameter("TemplateParam", JsonUtil.toJson(smsData.getParams()));
		request.putQueryParameter("SignName", smsProperties.getSignName());
		try {
			CommonResponse response = acsClient.getCommonResponse(request);
			Map<String, Object> data = JsonUtil.toMap(response.getData());
			String code = FAIL;
			if (data != null) {
				code = String.valueOf(data.get("Code"));
			}
			return new SmsResponse(response.getHttpStatus() == SUCCESS && code.equalsIgnoreCase(OK), response.getHttpStatus(), response.getData());
		} catch (ClientException e) {
			e.printStackTrace();
			return new SmsResponse(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	@Override
	public SmsCode sendValidate(SmsData smsData, String phone) {
		SmsCode smsCode = new SmsCode();
		boolean temp = sendSingle(smsData, phone);
		if (temp && StringUtil.isNotBlank(smsData.getKey())) {
			String id = StringUtil.randomUUID();
			String value = smsData.getParams().get(smsData.getKey());
			bladeRedis.setEx(cacheKey(phone, id), value, Duration.ofMinutes(30));
			smsCode.setId(id).setValue(value);
		} else {
			smsCode.setSuccess(Boolean.FALSE);
		}
		return smsCode;
	}

	@Override
	public boolean validateMessage(SmsCode smsCode) {
		String id = smsCode.getId();
		String value = smsCode.getValue();
		String phone = smsCode.getPhone();
		String cache = bladeRedis.get(cacheKey(phone, id));
		if (StringUtil.isNotBlank(value) && StringUtil.equalsIgnoreCase(cache, value)) {
			bladeRedis.del(cacheKey(phone, id));
			return true;
		}
		return false;
	}

}
