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

package org.springblade.core.http.cache;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashSet;
import java.util.Set;

/**
 * Http Cache 配置
 *
 * @author L.cm
 */
@AutoConfiguration
@AllArgsConstructor
@EnableConfigurationProperties(BladeHttpCacheProperties.class)
@ConditionalOnProperty(value = "blade.http.cache.enabled", havingValue = "true")
public class HttpCacheConfiguration implements WebMvcConfigurer {
	private static final String DEFAULT_STATIC_PATH_PATTERN = "/**";
	private final WebMvcProperties webMvcProperties;
	private final BladeHttpCacheProperties properties;
	private final CacheManager cacheManager;

	@Bean
	public HttpCacheService httpCacheService() {
		return new HttpCacheService(properties, cacheManager);
	}

	@Override
	public void addInterceptors(@NonNull InterceptorRegistry registry) {
		Set<String> excludePatterns = new HashSet<>(properties.getExcludePatterns());
		String staticPathPattern = webMvcProperties.getStaticPathPattern();
		// 如果静态 目录 不为 /**
		if (!DEFAULT_STATIC_PATH_PATTERN.equals(staticPathPattern.trim())) {
			excludePatterns.add(staticPathPattern);
		}
		HttpCacheInterceptor httpCacheInterceptor = new HttpCacheInterceptor(httpCacheService());
		registry.addInterceptor(httpCacheInterceptor)
			.addPathPatterns(properties.getIncludePatterns().toArray(new String[0]))
			.excludePathPatterns(excludePatterns.toArray(new String[0]));
	}
}
