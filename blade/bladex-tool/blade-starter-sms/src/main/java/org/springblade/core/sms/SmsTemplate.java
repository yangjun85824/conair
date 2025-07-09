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

import org.springblade.core.sms.model.SmsCode;
import org.springblade.core.sms.model.SmsData;
import org.springblade.core.sms.model.SmsInfo;
import org.springblade.core.sms.model.SmsResponse;
import org.springblade.core.tool.utils.StringPool;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;

import static org.springblade.core.sms.constant.SmsConstant.CAPTCHA_KEY;

/**
 * 短信通用封装
 *
 * @author Chill
 */
public interface SmsTemplate {

	/**
	 * 缓存键值
	 *
	 * @param phone 手机号
	 * @param id    键值
	 * @return 缓存键值返回
	 */
	default String cacheKey(String phone, String id) {
		return CAPTCHA_KEY + phone + StringPool.COLON + id;
	}

	/**
	 * 发送短信
	 *
	 * @param smsInfo 短信信息
	 * @return 发送返回
	 */
	default boolean send(SmsInfo smsInfo) {
		return sendMulti(smsInfo.getSmsData(), smsInfo.getPhones());
	}

	/**
	 * 发送短信
	 *
	 * @param smsData 短信内容
	 * @param phone   手机号
	 * @return 发送返回
	 */
	default boolean sendSingle(SmsData smsData, String phone) {
		if (StringUtils.isEmpty(phone)) {
			return Boolean.FALSE;
		}
		return sendMulti(smsData, Collections.singletonList(phone));
	}

	/**
	 * 发送短信
	 *
	 * @param smsData 短信内容
	 * @param phones  手机号列表
	 * @return 发送返回
	 */
	default boolean sendMulti(SmsData smsData, Collection<String> phones) {
		SmsResponse response = sendMessage(smsData, phones);
		return response.isSuccess();
	}

	/**
	 * 发送短信
	 *
	 * @param smsData 短信内容
	 * @param phones  手机号列表
	 * @return 发送返回
	 */
	SmsResponse sendMessage(SmsData smsData, Collection<String> phones);

	/**
	 * 发送验证码
	 *
	 * @param smsData 短信内容
	 * @param phone   手机号
	 * @return 发送返回
	 */
	SmsCode sendValidate(SmsData smsData, String phone);

	/**
	 * 校验验证码
	 *
	 * @param smsCode 验证码内容
	 * @return 是否校验成功
	 */
	boolean validateMessage(SmsCode smsCode);

}
