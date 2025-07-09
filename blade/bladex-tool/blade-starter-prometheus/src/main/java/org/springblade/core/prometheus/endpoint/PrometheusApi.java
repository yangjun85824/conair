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

package org.springblade.core.prometheus.endpoint;

import lombok.RequiredArgsConstructor;
import org.springblade.core.auto.annotation.AutoIgnore;
import org.springblade.core.prometheus.pojo.AlertMessage;
import org.springblade.core.prometheus.pojo.TargetGroup;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * prometheus http sd
 *
 * @author L.cm
 */
@AutoIgnore
@RestController
@RequestMapping("actuator/prometheus")
@RequiredArgsConstructor
public class PrometheusApi {
	private final String activeProfile;
	private final DiscoveryClient discoveryClient;
	private final ApplicationEventPublisher eventPublisher;

	@GetMapping("sd")
	public List<TargetGroup> getList() {
		List<String> serviceIdList = discoveryClient.getServices();
		if (serviceIdList == null || serviceIdList.isEmpty()) {
			return Collections.emptyList();
		}
		List<TargetGroup> targetGroupList = new ArrayList<>();
		for (String serviceId : serviceIdList) {
			List<ServiceInstance> instanceList = discoveryClient.getInstances(serviceId);
			List<String> targets = new ArrayList<>();
			for (ServiceInstance instance : instanceList) {
				String instanceHost = instance.getHost();
				int managementPort = Optional.ofNullable(instance.getMetadata())
					.map(x -> x.get("management.port"))
					.map(Integer::parseInt)
					.orElseGet(instance::getPort);
				targets.add(instanceHost + ':' + managementPort);
			}
			Map<String, String> labels = new HashMap<>(4);
			// 1. 环境
			if (StringUtils.hasText(activeProfile)) {
				labels.put("profile", activeProfile);
			}
			// 2. 服务名
			labels.put("__meta_prometheus_job", serviceId);
			targetGroupList.add(new TargetGroup(targets, labels));
		}
		return targetGroupList;
	}

	@PostMapping("alerts")
	public ResponseEntity<Object> postAlerts(@RequestBody AlertMessage message) {
		eventPublisher.publishEvent(message);
		return ResponseEntity.ok().build();
	}

}
