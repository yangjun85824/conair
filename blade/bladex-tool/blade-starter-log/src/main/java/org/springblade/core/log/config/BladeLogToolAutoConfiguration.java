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

package org.springblade.core.log.config;

import org.springblade.core.launch.props.BladeProperties;
import org.springblade.core.launch.props.BladePropertySource;
import org.springblade.core.launch.server.ServerInfo;
import org.springblade.core.log.aspect.ApiLogAspect;
import org.springblade.core.log.aspect.LogTraceAspect;
import org.springblade.core.log.event.ApiLogListener;
import org.springblade.core.log.event.ErrorLogListener;
import org.springblade.core.log.event.UsualLogListener;
import org.springblade.core.log.feign.ILogClient;
import org.springblade.core.log.filter.LogTraceFilter;
import org.springblade.core.log.logger.BladeLogger;
import org.springblade.core.log.props.BladeRequestLogProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import jakarta.servlet.DispatcherType;

/**
 * 日志工具自动配置
 *
 * @author Chill
 */
@AutoConfiguration
@ConditionalOnWebApplication
@EnableConfigurationProperties(BladeRequestLogProperties.class)
@BladePropertySource(value = "classpath:/blade-log.yml")
public class BladeLogToolAutoConfiguration {

	@Bean
	public ApiLogAspect apiLogAspect() {
		return new ApiLogAspect();
	}

	@Bean
	public LogTraceAspect logTraceAspect() {
		return new LogTraceAspect();
	}

	@Bean
	public BladeLogger bladeLogger() {
		return new BladeLogger();
	}

	@Bean
	public FilterRegistrationBean<LogTraceFilter> logTraceFilterRegistration() {
		FilterRegistrationBean<LogTraceFilter> registration = new FilterRegistrationBean<>();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new LogTraceFilter());
		registration.addUrlPatterns("/*");
		registration.setName("LogTraceFilter");
		registration.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registration;
	}

	@Bean
	@ConditionalOnMissingBean(name = "apiLogListener")
	public ApiLogListener apiLogListener(ILogClient logService, ServerInfo serverInfo, BladeProperties bladeProperties) {
		return new ApiLogListener(logService, serverInfo, bladeProperties);
	}

	@Bean
	@ConditionalOnMissingBean(name = "errorEventListener")
	public ErrorLogListener errorEventListener(ILogClient logService, ServerInfo serverInfo, BladeProperties bladeProperties) {
		return new ErrorLogListener(logService, serverInfo, bladeProperties);
	}

	@Bean
	@ConditionalOnMissingBean(name = "usualEventListener")
	public UsualLogListener usualEventListener(ILogClient logService, ServerInfo serverInfo, BladeProperties bladeProperties) {
		return new UsualLogListener(logService, serverInfo, bladeProperties);
	}

}
