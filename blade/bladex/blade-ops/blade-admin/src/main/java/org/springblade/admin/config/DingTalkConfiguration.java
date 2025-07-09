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
 * Author: DreamLu (596392912@qq.com)
 */
package org.springblade.admin.config;

import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import org.springblade.admin.dingtalk.DingTalkNotifier;
import org.springblade.admin.dingtalk.DingTalkService;
import org.springblade.admin.dingtalk.MonitorProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 钉钉自动配置
 *
 * @author L.cm
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "monitor.ding-talk.enabled", havingValue = "true")
public class DingTalkConfiguration {

	@Bean
	public DingTalkService dingTalkService(MonitorProperties properties,
										   WebClient.Builder builder) {
		return new DingTalkService(properties, builder.build());
	}

	@Bean
	public DingTalkNotifier dingTalkNotifier(MonitorProperties properties,
											 DingTalkService dingTalkService,
											 InstanceRepository repository,
											 Environment environment) {
		return new DingTalkNotifier(dingTalkService, properties, environment, repository);
	}

}
