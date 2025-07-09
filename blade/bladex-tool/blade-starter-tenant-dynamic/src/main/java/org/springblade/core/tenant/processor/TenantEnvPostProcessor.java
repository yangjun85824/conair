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
package org.springblade.core.tenant.processor;

import org.springblade.core.auto.annotation.AutoEnvPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 初始化分库分表配置
 *
 * @author Chill
 */
@AutoEnvPostProcessor
public class TenantEnvPostProcessor implements EnvironmentPostProcessor, Ordered {

	private static final String DYNAMIC_DATASOURCE_KEY = "spring.datasource.dynamic.enabled";

	private static final String AUTOCONFIGURE_EXCLUDE_KEY = "spring.autoconfigure.exclude";


	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		environment.getSystemProperties().put(DYNAMIC_DATASOURCE_KEY, "false");
		environment.getSystemProperties().put(AUTOCONFIGURE_EXCLUDE_KEY, "com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceAutoConfigure");
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
