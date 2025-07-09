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

import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.sms.model.SmsResponse;
import org.springblade.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ISmsClient
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_RESOURCE_NAME,
	fallback = ISmsClientFallback.class
)
public interface ISmsClient {
	String API_PREFIX = "/feign/client/sms";
	String SEND_MESSAGE = API_PREFIX + "/send-message";
	String SEND_MESSAGE_BY_TENANT_ID = API_PREFIX + "/send-message-by-tenant-id";
	String SEND_VALIDATE = API_PREFIX + "/send-validate";
	String SEND_VALIDATE_BY_TENANT_ID = API_PREFIX + "/send-validate-by-tenant-id";
	String VALIDATE_MESSAGE = API_PREFIX + "/validate-message";
	String VALIDATE_MESSAGE_BY_TENANT_ID = API_PREFIX + "/validate-message-by-tenant-id";

	/**
	 * 通用短信发送
	 *
	 * @param code   资源编号
	 * @param params 模板参数
	 * @param phones 手机号集合
	 * @return R
	 */
	@PostMapping(SEND_MESSAGE)
	R<SmsResponse> sendMessage(@RequestParam("code") String code, @RequestParam("params") String params, @RequestParam("phones") String phones);

	/**
	 * 通用短信发送
	 *
	 * @param code   资源编号
	 * @param params 模板参数
	 * @param phones 手机号集合
	 * @return R
	 */
	@PostMapping(SEND_MESSAGE_BY_TENANT_ID)
	R<SmsResponse> sendMessage(@RequestParam("tenantId") String tenantId, @RequestParam("code") String code, @RequestParam("params") String params, @RequestParam("phones") String phones);

	/**
	 * 短信验证码发送
	 *
	 * @param code  资源编号
	 * @param phone 手机号
	 * @return R
	 */
	@PostMapping(SEND_VALIDATE)
	R sendValidate(@RequestParam("code") String code, @RequestParam("phone") String phone);

	/**
	 * 短信验证码发送
	 *
	 * @param code  资源编号
	 * @param phone 手机号
	 * @return R
	 */
	@PostMapping(SEND_VALIDATE_BY_TENANT_ID)
	R sendValidate(@RequestParam("tenantId") String tenantId, @RequestParam("code") String code, @RequestParam("phone") String phone);

	/**
	 * 校验短信
	 *
	 * @param code  资源编号
	 * @param id    校验id
	 * @param value 校验值
	 * @param phone 手机号
	 * @return R
	 */
	@PostMapping(VALIDATE_MESSAGE)
	R validateMessage(@RequestParam("code") String code, @RequestParam("id") String id, @RequestParam("value") String value, @RequestParam("phone") String phone);

	/**
	 * 校验短信
	 *
	 * @param code  资源编号
	 * @param id    校验id
	 * @param value 校验值
	 * @param phone 手机号
	 * @return R
	 */
	@PostMapping(VALIDATE_MESSAGE_BY_TENANT_ID)
	R validateMessage(@RequestParam("tenantId") String tenantId, @RequestParam("code") String code, @RequestParam("id") String id, @RequestParam("value") String value, @RequestParam("phone") String phone);

}
