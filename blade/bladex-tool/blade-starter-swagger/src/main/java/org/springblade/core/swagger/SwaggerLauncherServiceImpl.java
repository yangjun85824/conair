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
package org.springblade.core.swagger;

import org.springblade.core.auto.service.AutoService;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;

import java.util.Properties;

/**
 * 初始化Swagger配置
 *
 * @author Chill
 */
@AutoService(LauncherService.class)
public class SwaggerLauncherServiceImpl implements LauncherService {
	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		Properties props = System.getProperties();
		if (profile.equals(AppConstant.PROD_CODE)) {
			props.setProperty("swagger.enabled", "false");
			props.setProperty("knife4j.enable", "false");
			props.setProperty("knife4j.production", "true");
			props.setProperty("springdoc.api-docs.enabled", "false");
			props.setProperty("springdoc.api-usage.enabled", "false");
			props.setProperty("springdoc.swagger-ui.enabled", "false");
		} else {
			props.setProperty("swagger.enabled", "true");
			props.setProperty("knife4j.enable", "true");
			props.setProperty("knife4j.production", "false");
			props.setProperty("springdoc.api-docs.enabled", "true");
			props.setProperty("springdoc.api-usage.enabled", "true");
			props.setProperty("springdoc.swagger-ui.enabled", "true");
			props.setProperty("spring.mvc.pathmatch.matching-strategy", "ANT_PATH_MATCHER");
		}
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
