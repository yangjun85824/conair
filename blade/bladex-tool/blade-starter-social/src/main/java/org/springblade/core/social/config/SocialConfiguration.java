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
package org.springblade.core.social.config;

import com.xkcoding.http.HttpUtil;
import com.xkcoding.http.support.Http;
import com.xkcoding.http.support.httpclient.HttpClientImpl;
import me.zhyd.oauth.cache.AuthStateCache;
import org.springblade.core.launch.props.BladePropertySource;
import org.springblade.core.redis.config.RedisTemplateConfiguration;
import org.springblade.core.social.cache.AuthStateRedisCache;
import org.springblade.core.social.props.SocialProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * SocialConfiguration
 *
 * @author Chill
 */
@EnableConfigurationProperties(SocialProperties.class)
@AutoConfiguration(after = RedisTemplateConfiguration.class)
@BladePropertySource(value = "classpath:/blade-social.yml")
public class SocialConfiguration {

	@Bean
	@ConditionalOnMissingBean(Http.class)
	public Http simpleHttp() {
		HttpClientImpl httpClient = new HttpClientImpl();
		HttpUtil.setHttp(httpClient);
		return httpClient;
	}

	@Bean
	@ConditionalOnMissingBean(AuthStateCache.class)
	public AuthStateCache authStateCache(RedisTemplate<String, Object> redisTemplate) {
		return new AuthStateRedisCache(redisTemplate, redisTemplate.opsForValue());
	}

}
