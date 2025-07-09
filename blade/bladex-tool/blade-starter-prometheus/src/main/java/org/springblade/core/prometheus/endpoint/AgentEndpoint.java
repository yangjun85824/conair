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

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import lombok.RequiredArgsConstructor;
import org.springblade.core.auto.annotation.AutoIgnore;
import org.springblade.core.prometheus.data.Agent;
import org.springblade.core.prometheus.data.Config;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * consul agent api
 *
 * @author L.cm
 */
@AutoIgnore
@RestController
@RequiredArgsConstructor
public class AgentEndpoint {
	private final NacosDiscoveryProperties properties;

	@GetMapping(value = "/v1/agent/self", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Agent getNodes() {
		Config config = Config.builder().dataCenter(properties.getGroup()).build();
		return Agent.builder().config(config).build();
	}

}
