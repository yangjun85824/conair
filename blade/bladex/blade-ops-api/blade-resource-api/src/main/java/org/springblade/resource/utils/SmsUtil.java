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
package org.springblade.resource.utils;

import org.springblade.core.sms.model.SmsCode;
import org.springblade.core.sms.model.SmsResponse;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.RandomType;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.resource.feign.ISmsClient;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信服务工具类
 *
 * @author Chill
 */
public class SmsUtil {

	public static final String PARAM_KEY = "code";
	public static final String SEND_SUCCESS = "短信发送成功";
	public static final String SEND_FAIL = "短信发送失败";
	public static final String VALIDATE_SUCCESS = "短信校验成功";
	public static final String VALIDATE_FAIL = "短信校验失败";

	private static ISmsClient smsClient;

	/**
	 * 获取短信服务构建类
	 *
	 * @return SmsBuilder
	 */
	public static ISmsClient getSmsClient() {
		if (smsClient == null) {
			smsClient = SpringUtil.getBean(ISmsClient.class);
		}
		return smsClient;
	}

	/**
	 * 获取短信验证码参数
	 *
	 * @return 验证码参数
	 */
	public static Map<String, String> getValidateParams() {
		Map<String, String> params = new HashMap<>(1);
		params.put(PARAM_KEY, StringUtil.random(6, RandomType.INT));
		return params;
	}

	/**
	 * 发送短信
	 *
	 * @param code   资源编号
	 * @param params 模板参数
	 * @param phones 手机号集合
	 * @return 发送结果
	 */
	public static SmsResponse sendMessage(String code, Map<String, String> params, String phones) {
		R<SmsResponse> result = getSmsClient().sendMessage(code, JsonUtil.toJson(params), phones);
		return result.getData();
	}

	/**
	 * 发送验证码
	 *
	 * @param code  资源编号
	 * @param phone 手机号
	 * @return 发送结果
	 */
	public static SmsCode sendValidate(String code, String phone) {
		SmsCode smsCode = new SmsCode();
		R result = getSmsClient().sendValidate(code, phone);
		if (result.isSuccess()) {
			smsCode = JsonUtil.parse(JsonUtil.toJson(result.getData()), SmsCode.class);
		} else {
			smsCode.setSuccess(Boolean.FALSE);
		}
		return smsCode;
	}

	/**
	 * 校验短信
	 *
	 * @param code  资源编号
	 * @param id    校验id
	 * @param value 校验值
	 * @return 发送结果
	 */
	public static boolean validateMessage(String code, String id, String value, String phone) {
		R result = getSmsClient().validateMessage(code, id, value, phone);
		return result.isSuccess();
	}

}
