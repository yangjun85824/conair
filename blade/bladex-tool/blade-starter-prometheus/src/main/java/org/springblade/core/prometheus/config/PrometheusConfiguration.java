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
package org.springblade.core.prometheus.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import org.springblade.core.launch.props.BladePropertySource;
import org.springblade.core.prometheus.endpoint.AgentEndpoint;
import org.springblade.core.prometheus.endpoint.PrometheusApi;
import org.springblade.core.prometheus.endpoint.ReactivePrometheusApi;
import org.springblade.core.prometheus.endpoint.ServiceEndpoint;
import org.springblade.core.prometheus.service.RegistrationService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.ConditionalOnReactiveDiscoveryEnabled;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * Prometheus配置类
 *
 * @author L.cm
 */
@AutoConfiguration
@BladePropertySource(value = "classpath:/blade-prometheus.yml")
public class PrometheusConfiguration {

	@Bean
	public RegistrationService registrationService(DiscoveryClient discoveryClient) {
		return new RegistrationService(discoveryClient);
	}

	@Bean
	public AgentEndpoint agentController(NacosDiscoveryProperties properties) {
		return new AgentEndpoint(properties);
	}

	@Bean
	public ServiceEndpoint serviceController(RegistrationService registrationService) {
		return new ServiceEndpoint(registrationService);
	}

	@AutoConfiguration
	@ConditionalOnBean(DiscoveryClient.class)
	@ConditionalOnDiscoveryEnabled
	// @ConditionalOnBlockingDiscoveryEnabled
	@ConditionalOnProperty(value = "spring.cloud.discovery.blocking.enabled")
	public static class PrometheusApiConfiguration {

		@Bean
		public PrometheusApi prometheusApi(Environment environment,
										   DiscoveryClient discoveryClient,
										   ApplicationEventPublisher eventPublisher) {
			String[] activeProfiles = environment.getActiveProfiles();
			String activeProfile = activeProfiles.length > 0 ? activeProfiles[0] : null;
			return new PrometheusApi(activeProfile, discoveryClient, eventPublisher);
		}

	}

	@AutoConfiguration
	@ConditionalOnBean(ReactiveDiscoveryClient.class)
	@ConditionalOnDiscoveryEnabled
	@ConditionalOnReactiveDiscoveryEnabled
	public static class ReactivePrometheusApiConfiguration {

		@Bean
		public ReactivePrometheusApi reactivePrometheusApi(Environment environment,
														   ReactiveDiscoveryClient discoveryClient,
														   ApplicationEventPublisher eventPublisher) {
			String[] activeProfiles = environment.getActiveProfiles();
			String activeProfile = activeProfiles.length > 0 ? activeProfiles[0] : null;
			return new ReactivePrometheusApi(activeProfile, discoveryClient, eventPublisher);
		}

	}

}
