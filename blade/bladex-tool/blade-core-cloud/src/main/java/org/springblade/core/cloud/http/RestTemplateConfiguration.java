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
package org.springblade.core.cloud.http;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springblade.core.cloud.http.client.OkHttp3ClientHttpRequestFactory;
import org.springblade.core.cloud.http.logger.HttpLoggingInterceptor;
import org.springblade.core.cloud.http.logger.OkHttpSlf4jLogger;
import org.springblade.core.tool.ssl.DisableValidationTrustManager;
import org.springblade.core.tool.ssl.TrustAllHostNames;
import org.springblade.core.tool.utils.Charsets;
import org.springblade.core.tool.utils.Holder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Http RestTemplateHeaderInterceptor 配置
 *
 * @author L.cm
 */
@Slf4j
@RequiredArgsConstructor
@AutoConfiguration
@ConditionalOnClass(OkHttpClient.class)
@ConditionalOnProperty(value = "blade.http.enabled", matchIfMissing = true)
public class RestTemplateConfiguration {
	private final BladeHttpProperties properties;

	/**
	 * okhttp3 请求日志拦截器
	 *
	 * @return HttpLoggingInterceptor
	 */
	@Bean
	public HttpLoggingInterceptor loggingInterceptor() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new OkHttpSlf4jLogger());
		interceptor.setLevel(properties.getLevel());
		return interceptor;
	}

	/**
	 * okhttp3 链接池配置
	 *
	 * @return okhttp3.ConnectionPool
	 */
	@Bean
	@ConditionalOnMissingBean
	public ConnectionPool httpClientConnectionPool() {
		int maxTotalConnections = properties.getMaxConnections();
		long timeToLive = properties.getTimeToLive();
		TimeUnit ttlUnit = properties.getTimeUnit();
		return new ConnectionPool(maxTotalConnections, timeToLive, ttlUnit);
	}

	/**
	 * 配置OkHttpClient
	 *
	 * @param connectionPool 链接池配置
	 * @param interceptor    拦截器
	 * @return OkHttpClient
	 */
	@Bean
	@ConditionalOnMissingBean
	public OkHttpClient okHttpClient(ConnectionPool connectionPool, HttpLoggingInterceptor interceptor) {
		boolean followRedirects = properties.isFollowRedirects();
		int connectTimeout = properties.getConnectionTimeout();
		return this.createBuilder(properties.isDisableSslValidation())
			.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
			.writeTimeout(30, TimeUnit.SECONDS)
			.readTimeout(30, TimeUnit.SECONDS)
			.followRedirects(followRedirects)
			.connectionPool(connectionPool)
			.addInterceptor(interceptor)
			.build();
	}

	private OkHttpClient.Builder createBuilder(boolean disableSslValidation) {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		if (disableSslValidation) {
			try {
				X509TrustManager disabledTrustManager = DisableValidationTrustManager.INSTANCE;
				TrustManager[] trustManagers = new TrustManager[]{disabledTrustManager};
				SSLContext sslContext = SSLContext.getInstance("SSL");
				sslContext.init(null, trustManagers, Holder.SECURE_RANDOM);
				SSLSocketFactory disabledSslSocketFactory = sslContext.getSocketFactory();
				builder.sslSocketFactory(disabledSslSocketFactory, disabledTrustManager);
				builder.hostnameVerifier(TrustAllHostNames.INSTANCE);
			} catch (NoSuchAlgorithmException | KeyManagementException e) {
				log.warn("Error setting SSLSocketFactory in OKHttpClient", e);
			}
		}
		return builder;
	}

	@Bean
	public RestTemplateHeaderInterceptor requestHeaderInterceptor() {
		return new RestTemplateHeaderInterceptor();
	}

	@AutoConfiguration
	@RequiredArgsConstructor
	@ConditionalOnClass(OkHttpClient.class)
	@ConditionalOnProperty(value = "blade.http.rest-template.enable")
	public static class RestTemplateAutoConfiguration {
		private final ApplicationContext context;

		/**
		 * 普通的 RestTemplate，不透传请求头，一般只做外部 http 调用
		 *
		 * @param okHttpClient OkHttpClient
		 * @return RestTemplate
		 */
		@Bean
		@ConditionalOnMissingBean
		public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, OkHttpClient okHttpClient) {
			restTemplateBuilder.requestFactory(() -> new OkHttp3ClientHttpRequestFactory(okHttpClient));
			RestTemplate restTemplate = restTemplateBuilder.build();
			configMessageConverters(context, restTemplate.getMessageConverters());
			return restTemplate;
		}
	}

	@AutoConfiguration
	@RequiredArgsConstructor
	@ConditionalOnClass(OkHttpClient.class)
	@ConditionalOnProperty(value = "blade.http.lb-rest-template.enable")
	public static class LbRestTemplateAutoConfiguration {
		private final ApplicationContext context;

		/**
		 * 支持负载均衡的 LbRestTemplate
		 *
		 * @param okHttpClient OkHttpClient
		 * @return LbRestTemplate
		 */
		@Bean
		@LoadBalanced
		@ConditionalOnMissingBean
		public LbRestTemplate lbRestTemplate(RestTemplateBuilder restTemplateBuilder, OkHttpClient okHttpClient) {
			restTemplateBuilder.requestFactory(() -> new OkHttp3ClientHttpRequestFactory(okHttpClient));
			LbRestTemplate restTemplate = restTemplateBuilder.build(LbRestTemplate.class);
			restTemplate.getInterceptors().add(context.getBean(RestTemplateHeaderInterceptor.class));
			configMessageConverters(context, restTemplate.getMessageConverters());
			return restTemplate;
		}
	}

	private static void configMessageConverters(ApplicationContext context, List<HttpMessageConverter<?>> converters) {
		converters.removeIf(x -> x instanceof StringHttpMessageConverter || x instanceof MappingJackson2HttpMessageConverter);
		converters.add(new StringHttpMessageConverter(Charsets.UTF_8));
		converters.add(new MappingJackson2HttpMessageConverter(context.getBean(ObjectMapper.class)));
	}
}
