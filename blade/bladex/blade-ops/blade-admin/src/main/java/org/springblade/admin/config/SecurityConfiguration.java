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

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springblade.admin.security.InternalAuthorizationManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;

import java.net.URI;

/**
 * 监控安全配置
 *
 * @author L.cm
 */
@EnableWebFluxSecurity
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(AdminServerProperties.class)
public class SecurityConfiguration {
	private final String contextPath;

	public SecurityConfiguration(AdminServerProperties adminServerProperties) {
		this.contextPath = adminServerProperties.getContextPath();
	}

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		// @formatter:off
		RedirectServerAuthenticationSuccessHandler successHandler = new RedirectServerAuthenticationSuccessHandler();
		successHandler.setLocation(URI.create(contextPath + "/"));
		return http
			// 明确调用headers()方法进行配置
			.headers(headers -> headers
				// 禁用frameOptions
				.frameOptions(ServerHttpSecurity.HeaderSpec.FrameOptionsSpec::disable)
			)
			// 配置授权规则
			.authorizeExchange(exchanges -> exchanges
				.pathMatchers(
					contextPath + "/assets/**",
					contextPath + "/login",
					contextPath + "/v1/agent/**",
					contextPath + "/v1/catalog/**",
					contextPath + "/v1/health/**"
				).permitAll()
				.pathMatchers(contextPath + "/actuator", contextPath + "/actuator/**")
				.access(new InternalAuthorizationManager())
				.anyExchange().authenticated()
			)
			// 配置表单登录
			.formLogin(formLogin -> formLogin
				.loginPage(contextPath + "/login")
				.authenticationSuccessHandler(successHandler)
			)
			// 配置登出
			.logout(logout -> logout
				.logoutUrl(contextPath + "/logout")
			)
			// 禁用HTTP Basic认证
			.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
			// 禁用CSRF
			.csrf(ServerHttpSecurity.CsrfSpec::disable)
			.build();
		// @formatter:on
	}

}
