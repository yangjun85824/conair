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
import org.springblade.core.launch.props.BladeProperties;
import org.springblade.core.secure.aspect.AuthAspect;
import org.springblade.core.secure.handler.ISecureHandler;
import org.springblade.core.secure.props.AuthSecure;
import org.springblade.core.secure.props.BasicSecure;
import org.springblade.core.secure.props.BladeSecureProperties;
import org.springblade.core.secure.props.SignSecure;
import org.springblade.core.secure.registry.SecureRegistry;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 安全配置类
 *
 * @author Chill
 */
@Order
@AutoConfiguration
@AllArgsConstructor
@EnableConfigurationProperties({BladeSecureProperties.class})
public class SecureConfiguration implements WebMvcConfigurer {

	private final SecureRegistry secureRegistry;

	private final BladeProperties bladeProperties;

	private final BladeSecureProperties secureProperties;

	private final ISecureHandler secureHandler;

	@Override
	public void addInterceptors(@NonNull InterceptorRegistry registry) {
		// 设置请求授权
		if (secureRegistry.isAuthEnabled() || secureProperties.getAuthEnabled()) {
			List<AuthSecure> authSecures = this.secureRegistry.addAuthPatterns(secureProperties.getAuth()).getAuthSecures();
			if (!authSecures.isEmpty()) {
				registry.addInterceptor(secureHandler.authInterceptor(secureProperties, authSecures));
				// 设置路径放行
				secureRegistry.excludePathPatterns(authSecures.stream().map(AuthSecure::getPattern).collect(Collectors.toList()));
			}
		}
		// 设置基础认证授权
		if (secureRegistry.isBasicEnabled() || secureProperties.getBasicEnabled()) {
			List<BasicSecure> basicSecures = this.secureRegistry.addBasicPatterns(secureProperties.getBasic()).getBasicSecures();
			if (!basicSecures.isEmpty()) {
				registry.addInterceptor(secureHandler.basicInterceptor(basicSecures));
				// 设置路径放行
				secureRegistry.excludePathPatterns(basicSecures.stream().map(BasicSecure::getPattern).collect(Collectors.toList()));
			}
		}
		// 设置签名认证授权
		if (secureRegistry.isSignEnabled() || secureProperties.getSignEnabled()) {
			List<SignSecure> signSecures = this.secureRegistry.addSignPatterns(secureProperties.getSign()).getSignSecures();
			if (!signSecures.isEmpty()) {
				registry.addInterceptor(secureHandler.signInterceptor(signSecures));
				// 设置路径放行
				secureRegistry.excludePathPatterns(signSecures.stream().map(SignSecure::getPattern).collect(Collectors.toList()));
			}
		}
		// 设置客户端授权
		if (secureRegistry.isClientEnabled() || secureProperties.getClientEnabled()) {
			secureProperties.getClient().forEach(
				clientSecure -> registry.addInterceptor(secureHandler.clientInterceptor(clientSecure.getClientId()))
					.addPathPatterns(clientSecure.getPathPatterns())
			);
		}
		// 设置令牌严格模式
		if (!secureRegistry.isStrictToken()) {
			secureProperties.setStrictToken(false);
		}
		// 设置请求头严格模式
		if (!secureRegistry.isStrictHeader()) {
			secureProperties.setStrictHeader(false);
		}
		// 设置路径放行
		if (secureRegistry.isEnabled() || secureProperties.getEnabled()) {
			InterceptorRegistration interceptorRegistration = registry.addInterceptor(secureHandler.tokenInterceptor(secureProperties))
				.excludePathPatterns(secureRegistry.getExcludePatterns())
				.excludePathPatterns(secureRegistry.getDefaultExcludePatterns())
				.excludePathPatterns(secureProperties.getSkipUrl());
			// 宽松模式下获取放行路径且再新建一套自定义放行路径，用于处理cloud网关虚拟路径导致未匹配的问题
			// 严格模式下不予处理，应严格按照cloud和boot的路由进行匹配
			if (!secureProperties.getStrictToken()) {
				interceptorRegistration.excludePathPatterns(secureProperties.getSkipUrl().stream()
					.map(url -> StringUtil.removePrefix(url, StringPool.SLASH + bladeProperties.getName())).toList());
			}
		}
	}

	@Bean
	public AuthAspect authAspect() {
		return new AuthAspect();
	}

}
