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
package org.springblade.core.tenant.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.config.MybatisPlusConfiguration;
import org.springblade.core.tenant.*;
import org.springblade.core.tenant.aspect.BladeTenantAspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 多租户配置类
 *
 * @author Chill
 */
@AllArgsConstructor
@AutoConfiguration(before = MybatisPlusConfiguration.class)
@EnableConfigurationProperties(BladeTenantProperties.class)
public class TenantConfiguration {

	/**
	 * 自定义多租户处理器
	 *
	 * @param tenantProperties 多租户配置类
	 * @return TenantHandler
	 */
	@Bean
	@Primary
	public TenantLineHandler bladeTenantHandler(BladeTenantProperties tenantProperties) {
		return new BladeTenantHandler(tenantProperties);
	}

	/**
	 * 自定义租户拦截器
	 *
	 * @param tenantHandler    多租户处理器
	 * @param tenantProperties 多租户配置类
	 * @return BladeTenantInterceptor
	 */
	@Bean
	@Primary
	public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantLineHandler tenantHandler, BladeTenantProperties tenantProperties) {
		BladeTenantInterceptor tenantInterceptor = new BladeTenantInterceptor();
		tenantInterceptor.setTenantLineHandler(tenantHandler);
		tenantInterceptor.setTenantProperties(tenantProperties);
		return tenantInterceptor;
	}

	/**
	 * 自定义租户id生成器
	 *
	 * @return TenantId
	 */
	@Bean
	@ConditionalOnMissingBean(TenantId.class)
	public TenantId tenantId() {
		return new BladeTenantId();
	}

	/**
	 * 自定义租户切面
	 */
	@Bean
	public BladeTenantAspect bladeTenantAspect() {
		return new BladeTenantAspect();
	}

}
