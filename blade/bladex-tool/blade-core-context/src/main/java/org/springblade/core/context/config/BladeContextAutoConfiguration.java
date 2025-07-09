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
package org.springblade.core.context.config;

import org.springblade.core.context.BladeContext;
import org.springblade.core.context.BladeHttpHeadersGetter;
import org.springblade.core.context.BladeServletContext;
import org.springblade.core.context.ServletHttpHeadersGetter;
import org.springblade.core.context.props.BladeContextProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * blade 服务上下文配置
 *
 * @author L.cm
 */
@AutoConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(BladeContextProperties.class)
public class BladeContextAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public BladeHttpHeadersGetter bladeHttpHeadersGetter(BladeContextProperties contextProperties) {
		return new ServletHttpHeadersGetter(contextProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	public BladeContext bladeContext(BladeContextProperties contextProperties, BladeHttpHeadersGetter httpHeadersGetter) {
		return new BladeServletContext(contextProperties, httpHeadersGetter);
	}

}
