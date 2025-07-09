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
package org.springblade.core.secure.config;


import lombok.AllArgsConstructor;
import org.springblade.core.secure.handler.BladePermissionHandler;
import org.springblade.core.secure.handler.IPermissionHandler;
import org.springblade.core.secure.handler.ISecureHandler;
import org.springblade.core.secure.handler.SecureHandlerHandler;
import org.springblade.core.secure.registry.SecureRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * secure注册默认配置
 *
 * @author Chill
 */
@Order
@AutoConfiguration(before = SecureConfiguration.class)
@AllArgsConstructor
public class RegistryConfiguration {

	private final JdbcTemplate jdbcTemplate;

	@Bean
	@ConditionalOnMissingBean(SecureRegistry.class)
	public SecureRegistry secureRegistry() {
		return new SecureRegistry();
	}

	@Bean
	@ConditionalOnMissingBean(ISecureHandler.class)
	public ISecureHandler secureHandler() {
		return new SecureHandlerHandler();
	}

	@Bean
	@ConditionalOnMissingBean(IPermissionHandler.class)
	public IPermissionHandler permissionHandler() {
		return new BladePermissionHandler(jdbcTemplate);
	}

}
