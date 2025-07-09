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
package org.springblade.core.sms.config;

import com.qiniu.sms.SmsManager;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.sms.QiniuSmsTemplate;
import org.springblade.core.sms.props.SmsProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 阿里云短信配置类
 *
 * @author Chill
 */
@AutoConfiguration
@AllArgsConstructor
@ConditionalOnClass(SmsManager.class)
@EnableConfigurationProperties(SmsProperties.class)
@ConditionalOnProperty(value = "sms.name", havingValue = "qiniu")
public class QiniuSmsConfiguration {

	private final BladeRedis bladeRedis;

	@Bean
	public QiniuSmsTemplate qiniuSmsTemplate(SmsProperties smsProperties) {
		Auth auth = Auth.create(smsProperties.getAccessKey(), smsProperties.getSecretKey());
		SmsManager smsManager = new SmsManager(auth);
		return new QiniuSmsTemplate(smsProperties, smsManager, bladeRedis);
	}

}
