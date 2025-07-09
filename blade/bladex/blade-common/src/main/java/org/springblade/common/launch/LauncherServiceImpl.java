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
package org.springblade.common.launch;

import org.springblade.common.constant.LauncherConstant;
import org.springblade.core.auto.service.AutoService;
import org.springblade.core.launch.service.LauncherService;
import org.springblade.core.launch.utils.PropsUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Properties;

/**
 * 启动参数拓展
 *
 * @author smallchil
 */
@AutoService(LauncherService.class)
public class LauncherServiceImpl implements LauncherService {

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		Properties props = System.getProperties();
		// 通用注册
		PropsUtil.setProperty(props, "spring.cloud.nacos.username", LauncherConstant.NACOS_USERNAME);
		PropsUtil.setProperty(props, "spring.cloud.nacos.password", LauncherConstant.NACOS_PASSWORD);
		PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.server-addr", LauncherConstant.nacosAddr(profile));
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.server-addr", LauncherConstant.nacosAddr(profile));
		PropsUtil.setProperty(props, "spring.cloud.sentinel.transport.dashboard", LauncherConstant.sentinelAddr(profile));
		PropsUtil.setProperty(props, "spring.zipkin.base-url", LauncherConstant.zipkinAddr(profile));
		PropsUtil.setProperty(props, "spring.datasource.dynamic.enabled", "false");

		// 开启elk日志
		// PropsUtil.setProperty(props, "blade.log.elk.destination", LauncherConstant.elkAddr(profile));

		// seata注册地址
		// PropsUtil.setProperty(props, "seata.service.grouplist.default", LauncherConstant.seataAddr(profile));
		// seata注册group格式
		// PropsUtil.setProperty(props, "seata.tx-service-group", LauncherConstant.seataServiceGroup(appName));
		// seata配置服务group
		// PropsUtil.setProperty(props, "seata.service.vgroup-mapping.".concat(LauncherConstant.seataServiceGroup(appName)), LauncherConstant.DEFAULT_MODE);
		// seata注册模式配置
		// PropsUtil.setProperty(props, "seata.registry.type", LauncherConstant.NACOS_MODE);
		// PropsUtil.setProperty(props, "seata.registry.nacos.server-addr", LauncherConstant.nacosAddr(profile));
		// PropsUtil.setProperty(props, "seata.config.type", LauncherConstant.NACOS_MODE);
		// PropsUtil.setProperty(props, "seata.config.nacos.server-addr", LauncherConstant.nacosAddr(profile));
	}

}
