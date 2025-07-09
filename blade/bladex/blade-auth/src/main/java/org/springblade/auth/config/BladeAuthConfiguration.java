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
package org.springblade.auth.config;

import org.springblade.auth.handler.BladeAuthorizationHandler;
import org.springblade.auth.handler.BladePasswordHandler;
import org.springblade.auth.handler.BladeTokenHandler;
import org.springblade.auth.service.BladeClientDetailService;
import org.springblade.auth.service.BladeUserDetailService;
import org.springblade.core.jwt.props.JwtProperties;
import org.springblade.core.oauth2.config.OAuth2AutoConfiguration;
import org.springblade.core.oauth2.handler.AuthorizationHandler;
import org.springblade.core.oauth2.handler.PasswordHandler;
import org.springblade.core.oauth2.handler.TokenHandler;
import org.springblade.core.oauth2.props.OAuth2Properties;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tenant.BladeTenantProperties;
import org.springblade.system.feign.IUserClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * BladeAuthConfiguration
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(OAuth2AutoConfiguration.class)
public class BladeAuthConfiguration {
	@Bean
	public AuthorizationHandler authorizationHandler(BladeRedis bladeRedis, BladeTenantProperties tenantProperties) {
		return new BladeAuthorizationHandler(bladeRedis, tenantProperties);
	}

	@Bean
	public PasswordHandler passwordHandler(OAuth2Properties properties) {
		return new BladePasswordHandler(properties);
	}

	@Bean
	public TokenHandler tokenHandler(JwtProperties jwtProperties) {
		return new BladeTokenHandler(jwtProperties);
	}

	@Bean
	public OAuth2ClientService oAuth2ClientService(JdbcTemplate jdbcTemplate) {
		return new BladeClientDetailService(jdbcTemplate);
	}

	@Bean
	public OAuth2UserService oAuth2UserService(IUserClient userClient) {
		return new BladeUserDetailService(userClient);
	}

}
