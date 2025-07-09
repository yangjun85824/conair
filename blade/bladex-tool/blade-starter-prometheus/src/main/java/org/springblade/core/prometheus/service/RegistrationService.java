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
package org.springblade.core.prometheus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.prometheus.data.ChangeItem;
import org.springblade.core.prometheus.data.Service;
import org.springblade.core.prometheus.data.ServiceHealth;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Supplier;

/**
 * Returns Services and List of Service with its last changed
 *
 * @author L.cm
 */
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {
	private static final String[] NO_SERVICE_TAGS = new String[0];
	private final DiscoveryClient discoveryClient;

	public Mono<ChangeItem<Map<String, String[]>>> getServiceNames(long waitMillis, Long index) {
		return returnDeferred(waitMillis, index, () -> {
			List<String> services = discoveryClient.getServices();
			Set<String> set = new HashSet<>(services);
			Map<String, String[]> result = new HashMap<>();
			for (String item : set) {
				result.put(item, NO_SERVICE_TAGS);
			}
			return result;
		});
	}

	public Mono<ChangeItem<List<Service>>> getService(String appName, long waitMillis, Long index) {
		return returnDeferred(waitMillis, index, () -> {
			List<ServiceInstance> instances = discoveryClient.getInstances(appName);
			List<Service> list = new ArrayList<>();
			if (instances == null || instances.isEmpty()) {
				return Collections.emptyList();
			}
			Set<ServiceInstance> instSet = new HashSet<>(instances);
			for (ServiceInstance instance : instSet) {
				Service service = Service.builder()
					.address(instance.getHost())
					.node(instance.getServiceId())
					.serviceAddress(instance.getHost())
					.servicePort(instance.getPort())
					.serviceName(instance.getServiceId())
					.serviceId(instance.getHost() + ":" + instance.getPort())
					.nodeMeta(Collections.emptyMap())
					.serviceMeta(instance.getMetadata())
					.serviceTags(Collections.emptyList())
					.build();
				list.add(service);
			}
			return list;
		});
	}

	public ServiceHealth getServiceHealth(Service instanceInfo) {
		String address = instanceInfo.getAddress();
		ServiceHealth.Node node = ServiceHealth.Node.builder()
			.name(instanceInfo.getServiceName())
			.address(address)
			.meta(Collections.emptyMap())
			.build();
		ServiceHealth.Service service = ServiceHealth.Service.builder()
			.id(instanceInfo.getServiceId())
			.name(instanceInfo.getServiceName())
			.tags(Collections.emptyList())
			.address(address)
			.meta(instanceInfo.getServiceMeta())
			.port(instanceInfo.getServicePort())
			.build();
		ServiceHealth.Check check = ServiceHealth.Check.builder()
			.node(instanceInfo.getServiceName())
			.checkId("service:" + instanceInfo.getServiceId())
			.name("Service '" + instanceInfo.getServiceId() + "' check")
			// nacos 实时性很高，可认定为健康
			.status("UP")
			.build();
		return ServiceHealth.builder()
			.node(node)
			.service(service)
			.checks(Collections.singletonList(check))
			.build();
	}

	private static <T> Mono<ChangeItem<T>> returnDeferred(long waitMillis, Long index, Supplier<T> fn) {
		return Mono.just(new ChangeItem<>(fn.get(), System.currentTimeMillis()));
	}
}
