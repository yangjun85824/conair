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

import org.springblade.core.sms.model.SmsResponse;
import org.springblade.core.tool.api.R;
import org.springframework.stereotype.Component;

/**
 * 流程远程调用失败处理类
 *
 * @author Chill
 */
@Component
public class ISmsClientFallback implements ISmsClient {
	@Override
	public R<SmsResponse> sendMessage(String code, String params, String phones) {
		return R.fail("远程调用失败");
	}

	@Override
	public R<SmsResponse> sendMessage(String tenantId, String code, String params, String phones) {
		return R.fail("远程调用失败");
	}

	@Override
	public R sendValidate(String code, String phone) {
		return R.fail("远程调用失败");
	}

	@Override
	public R sendValidate(String tenantId, String code, String phone) {
		return R.fail("远程调用失败");
	}

	@Override
	public R validateMessage(String code, String id, String value, String phone) {
		return R.fail("远程调用失败");
	}

	@Override
	public R validateMessage(String tenantId, String code, String id, String value, String phone) {
		return R.fail("远程调用失败");
	}

}
