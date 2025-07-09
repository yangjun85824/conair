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
package org.springblade.core.oauth2.config;

import lombok.AllArgsConstructor;
import org.springblade.core.jwt.props.JwtProperties;
import org.springblade.core.oauth2.granter.TokenGranter;
import org.springblade.core.oauth2.granter.TokenGranterEnhancer;
import org.springblade.core.oauth2.granter.TokenGranterFactory;
import org.springblade.core.oauth2.handler.*;
import org.springblade.core.oauth2.props.OAuth2Properties;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.oauth2.service.impl.OAuth2ClientDetailService;
import org.springblade.core.oauth2.service.impl.OAuth2UserDetailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * OAuth2Configuration
 *
 * @author BladeX
 */
@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(OAuth2Properties.class)
@ConditionalOnProperty(value = OAuth2Properties.PREFIX + ".enabled", havingValue = "true", matchIfMissing = true)
public class OAuth2AutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public AuthorizationHandler authorizationHandler() {
		return new OAuth2AuthorizationHandler();
	}

	@Bean
	@ConditionalOnMissingBean
	public PasswordHandler passwordHandler(OAuth2Properties properties) {
		return new OAuth2PasswordHandler(properties);
	}

	@Bean
	@ConditionalOnMissingBean
	public TokenHandler tokenHandler(JwtProperties properties) {
		return new OAuth2TokenHandler(properties);
	}

	@Bean
	@ConditionalOnMissingBean
	public OAuth2ClientService oAuth2ClientService(JdbcTemplate jdbcTemplate) {
		return new OAuth2ClientDetailService(jdbcTemplate);
	}

	@Bean
	@ConditionalOnMissingBean
	public OAuth2UserService oAuth2UserService(JdbcTemplate jdbcTemplate) {
		return new OAuth2UserDetailService(jdbcTemplate);
	}

	@Bean
	public TokenGranterFactory tokenGranterFactory(List<TokenGranter> tokenGranters, List<TokenGranterEnhancer> tokenGranterEnhancers, OAuth2Properties properties) {
		return new TokenGranterFactory(tokenGranters, tokenGranterEnhancers, properties);
	}

}
