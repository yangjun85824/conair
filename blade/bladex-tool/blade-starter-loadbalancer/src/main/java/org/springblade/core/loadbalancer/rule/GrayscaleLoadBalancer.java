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
package org.springblade.core.loadbalancer.rule;

import com.alibaba.nacos.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.loadbalancer.props.BladeLoadBalancerProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PatternMatchUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.springblade.core.loadbalancer.constant.LoadBalancerConstant.VERSION_NAME;

/**
 * LoadBalancer 负载规则
 *
 * @author Chill
 */
@Slf4j
@RequiredArgsConstructor
public class GrayscaleLoadBalancer implements ReactorServiceInstanceLoadBalancer {
	private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
	private final BladeLoadBalancerProperties bladeLoadBalancerProperties;

	@Override
	public Mono<Response<ServiceInstance>> choose(Request request) {
		ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
			.getIfAvailable(NoopServiceInstanceListSupplier::new);
		return supplier.get(request).next()
			.map(serviceInstances -> getInstanceResponse(serviceInstances, request));
	}

	/**
	 * 自定义节点规则返回目标节点
	 */
	private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, Request request) {
		// 注册中心无可用实例 返回空
		if (CollectionUtils.isEmpty(instances)) {
			return new EmptyResponse();
		}
		// 指定ip则返回满足ip的服务
		List<String> priorIpPattern = bladeLoadBalancerProperties.getPriorIpPattern();
		if (!priorIpPattern.isEmpty()) {
			String[] priorIpPatterns = priorIpPattern.toArray(new String[0]);
			List<ServiceInstance> priorIpInstances = instances.stream().filter(
				(i -> PatternMatchUtils.simpleMatch(priorIpPatterns, i.getHost()))
			).collect(Collectors.toList());
			if (!priorIpInstances.isEmpty()) {
				instances = priorIpInstances;
			}
		}

		// 获取灰度版本号
		DefaultRequestContext context = (DefaultRequestContext) request.getContext();
		RequestData requestData = (RequestData) context.getClientRequest();
		HttpHeaders headers = requestData.getHeaders();
		String versionName = headers.getFirst(VERSION_NAME);

		// 没有指定灰度版本则返回正式的服务
		if (StringUtils.isBlank(versionName)) {
			List<ServiceInstance> noneGrayscaleInstances = instances.stream().filter(
				i -> !i.getMetadata().containsKey(VERSION_NAME)
			).collect(Collectors.toList());
			return randomInstance(noneGrayscaleInstances);
		}

		// 指定灰度版本则返回标记的服务
		List<ServiceInstance> grayscaleInstances = instances.stream().filter(i -> {
			String versionNameInMetadata = i.getMetadata().get(VERSION_NAME);
			return StringUtils.equalsIgnoreCase(versionNameInMetadata, versionName);
		}).collect(Collectors.toList());
		return randomInstance(grayscaleInstances);
	}

	/**
	 * 采用随机规则返回
	 */
	private Response<ServiceInstance> randomInstance(List<ServiceInstance> instances) {
		// 若没有可用节点则返回空
		if (instances.isEmpty()) {
			return new EmptyResponse();
		}

		// 挑选随机节点返回
		int randomIndex = ThreadLocalRandom.current().nextInt(instances.size());
		ServiceInstance instance = instances.get(randomIndex % instances.size());
		return new DefaultResponse(instance);
	}
}
