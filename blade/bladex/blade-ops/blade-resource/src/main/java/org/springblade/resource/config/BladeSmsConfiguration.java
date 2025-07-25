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
package org.springblade.resource.config;

import com.yomahub.liteflow.core.FlowExecutor;
import lombok.AllArgsConstructor;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.sms.props.SmsProperties;
import org.springblade.resource.builder.SmsBuilder;
import org.springblade.resource.service.ISmsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sms配置类
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
public class BladeSmsConfiguration {

	private final SmsProperties smsProperties;

	private final ISmsService smsService;

	private final BladeRedis bladeRedis;

	private final FlowExecutor flowExecutor;

	@Bean
	public SmsBuilder smsBuilder() {
		return new SmsBuilder(smsProperties, smsService, bladeRedis, flowExecutor);
	}

}
