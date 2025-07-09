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
package org.springblade.resource.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springblade.core.sms.model.SmsCode;
import org.springblade.core.sms.model.SmsData;
import org.springblade.core.sms.model.SmsResponse;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.resource.builder.SmsBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springblade.resource.utils.SmsUtil.*;

/**
 * 短信服务端点
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/sms/endpoint")
@Tag(name = "短信服务端点", description = "短信服务端点")
public class SmsEndpoint {

	/**
	 * 短信服务构建类
	 */
	private final SmsBuilder smsBuilder;

	//================================= 短信服务校验 =================================

	/**
	 * 短信验证码发送
	 *
	 * @param phone 手机号
	 */
	@SneakyThrows
	@PostMapping("/send-validate")
	public R sendValidate(@RequestParam String phone) {
		Map<String, String> params = getValidateParams();
		SmsCode smsCode = smsBuilder.template().sendValidate(new SmsData(params).setKey(PARAM_KEY), phone);
		return smsCode.isSuccess() ? R.data(smsCode, SEND_SUCCESS) : R.fail(SEND_FAIL);
	}

	/**
	 * 校验短信
	 *
	 * @param smsCode 短信校验信息
	 */
	@SneakyThrows
	@PostMapping("/validate-message")
	public R validateMessage(SmsCode smsCode) {
		boolean validate = smsBuilder.template().validateMessage(smsCode);
		return validate ? R.success(VALIDATE_SUCCESS) : R.fail(VALIDATE_FAIL);
	}

	//========== 通用短信自定义发送(支持自定义params参数传递, 推荐用于测试, 不推荐用于生产环境) ==========

	/**
	 * 发送信息
	 *
	 * @param params 自定义短信参数
	 * @param phones 手机号集合
	 */
	@SneakyThrows
	@PostMapping("/send-message")
	public R sendMessage(@RequestParam String code, @RequestParam String params, @RequestParam String phones) {
		SmsData smsData = new SmsData(JsonUtil.readMap(params, String.class, String.class));
		return send(code, smsData, phones);
	}

	//========== 指定短信服务发送(可根据各种场景自定拓展定制, 损失灵活性增加安全性, 推荐用于生产环境) ==========

	/**
	 * 短信通知
	 *
	 * @param phones 手机号集合
	 */
	@SneakyThrows
	@PostMapping("/send-notice")
	public R sendNotice(@RequestParam String phones) {
		Map<String, String> params = new HashMap<>(3);
		params.put("title", "通知标题");
		params.put("content", "通知内容");
		params.put("date", "通知时间");
		SmsData smsData = new SmsData(params);
		return send(smsData, phones);
	}

	/**
	 * 订单通知
	 *
	 * @param phones 手机号集合
	 */
	@SneakyThrows
	@PostMapping("/send-order")
	public R sendOrder(@RequestParam String phones) {
		Map<String, String> params = new HashMap<>(3);
		params.put("orderNo", "订单编号");
		params.put("packageNo", "快递单号");
		params.put("user", "收件人");
		SmsData smsData = new SmsData(params);
		return send(smsData, phones);
	}

	/**
	 * 会议通知
	 *
	 * @param phones 手机号集合
	 */
	@SneakyThrows
	@PostMapping("/send-meeting")
	public R sendMeeting(@RequestParam String phones) {
		Map<String, String> params = new HashMap<>(2);
		params.put("roomId", "会议室");
		params.put("topic", "会议主题");
		params.put("date", "会议时间");
		SmsData smsData = new SmsData(params);
		return send(smsData, phones);
	}

	//================================= 通用短信发送接口 =================================

	/**
	 * 通用短信发送接口
	 *
	 * @param smsData 短信内容
	 * @param phones  手机号列表
	 * @return 是否发送成功
	 */
	private R send(SmsData smsData, String phones) {
		SmsResponse response = smsBuilder.template().sendMessage(smsData, Func.toStrList(phones));
		return response.isSuccess() ? R.success(SEND_SUCCESS) : R.fail(SEND_FAIL);
	}

	/**
	 * 通用短信发送接口
	 *
	 * @param code    资源编号
	 * @param smsData 短信内容
	 * @param phones  手机号列表
	 * @return 是否发送成功
	 */
	private R send(String code, SmsData smsData, String phones) {
		SmsResponse response = smsBuilder.template(code).sendMessage(smsData, Func.toStrList(phones));
		return response.isSuccess() ? R.success(SEND_SUCCESS) : R.fail(SEND_FAIL);
	}

}
