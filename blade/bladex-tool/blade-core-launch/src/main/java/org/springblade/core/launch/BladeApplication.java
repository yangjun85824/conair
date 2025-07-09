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
package org.springblade.core.launch;

import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.launch.constant.NacosConstant;
import org.springblade.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.*;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * BladeX 应用启动类
 * <p>
 * 该类主要封装启动逻辑，包括环境变量处理、配置属性设置、以及自定义组件的加载。
 *
 * @author BladeX
 */
public class BladeApplication {

	/**
	 * 启动SpringBoot应用，不使用自定义SpringApplicationBuilder
	 *
	 * @param appName 应用名称
	 * @param source  Spring Boot应用的主类
	 * @param args    命令行参数
	 * @return 配置完成的应用上下文
	 */
	public static ConfigurableApplicationContext run(String appName, Class<?> source, String... args) {
		SpringApplicationBuilder springApplicationBuilder = createSpringApplicationBuilder(appName, source, args);
		return springApplicationBuilder.run(args);
	}

	/**
	 * 启动SpringBoot应用，使用自定义SpringApplicationBuilder
	 *
	 * @param appName 应用名称
	 * @param source  Spring Boot应用的主类
	 * @param builder 自定义的SpringApplicationBuilder
	 * @param args    命令行参数
	 * @return 配置完成的应用上下文
	 */
	public static ConfigurableApplicationContext run(String appName, Class<?> source, SpringApplicationBuilder builder, String... args) {
		SpringApplicationBuilder springApplicationBuilder = createSpringApplicationBuilder(appName, source, builder, args);
		return springApplicationBuilder.run(args);
	}

	/**
	 * 创建SpringApplicationBuilder实例，包括环境配置、系统属性设置、默认属性设置和自定义组件加载
	 *
	 * @param appName 应用名称
	 * @param source  Spring Boot应用的主类
	 * @param args    命令行参数
	 * @return 配置完成的SpringApplicationBuilder实例
	 */
	public static SpringApplicationBuilder createSpringApplicationBuilder(String appName, Class<?> source, String... args) {
		return createSpringApplicationBuilder(appName, source, null, args);
	}

	/**
	 * 创建SpringApplicationBuilder实例，包括环境配置、系统属性设置、默认属性设置和自定义组件加载
	 *
	 * @param appName 应用名称
	 * @param source  Spring Boot应用的主类
	 * @param builder 自定义的SpringApplicationBuilder
	 * @param args    命令行参数
	 * @return 配置完成的SpringApplicationBuilder实例
	 */
	public static SpringApplicationBuilder createSpringApplicationBuilder(String appName, Class<?> source, SpringApplicationBuilder builder, String... args) {
		Assert.hasText(appName, "[appName]服务名不能为空"); // 确保应用名称不为空
		ConfigurableEnvironment environment = configureEnvironment(args); // 配置环境变量，包括命令行参数、系统属性和系统环境变量
		List<String> activeProfileList = getActiveProfiles(environment); // 获取当前激活的Spring配置文件列表
		String profile = determineActiveProfile(activeProfileList); // 确定要激活的配置文件，如果存在多个则抛出异常
		if (builder == null) { // 如果没有提供自定义的SpringApplicationBuilder，则创建一个新的
			builder = new SpringApplicationBuilder(source); // 使用Spring Boot应用的主类初始化SpringApplicationBuilder
		}
		builder.profiles(profile); // 设置SpringApplicationBuilder要激活的配置文件
		setSystemProperties(appName, profile); // 设置系统属性，包括应用名称、激活的配置文件等
		Properties defaultProperties = setDefaultProperties(appName, profile); // 设置默认的一些属性
		builder.properties(defaultProperties); // 将这些默认属性添加到SpringApplicationBuilder中
		loadCustomComponents(builder, appName, profile); // 加载自定义组件，如各种启动器服务
		return builder; // 返回配置好的SpringApplicationBuilder实例
	}

	/**
	 * 判断是否为本地开发环境
	 *
	 * @return true如果是本地开发环境，否则为false
	 */
	public static boolean isLocalDev() {
		String osName = System.getProperty("os.name");
		return StringUtils.hasText(osName) && !(AppConstant.OS_NAME_LINUX.equalsIgnoreCase(osName));
	}


	/**
	 * 配置环境变量
	 *
	 * @param args 命令行参数
	 * @return 配置完成的环境
	 */
	private static ConfigurableEnvironment configureEnvironment(String... args) {
		ConfigurableEnvironment environment = new StandardEnvironment();
		MutablePropertySources propertySources = environment.getPropertySources();
		propertySources.addFirst(new SimpleCommandLinePropertySource(args));
		propertySources.addLast(new MapPropertySource(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME, environment.getSystemProperties()));
		propertySources.addLast(new SystemEnvironmentPropertySource(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, environment.getSystemEnvironment()));
		return environment;
	}

	/**
	 * 获取激活的配置文件列表
	 *
	 * @param environment 环境配置
	 * @return 激活的配置文件列表
	 */
	private static List<String> getActiveProfiles(ConfigurableEnvironment environment) {
		String[] activeProfiles = environment.getActiveProfiles();
		return new ArrayList<>(Arrays.asList(activeProfiles));
	}

	/**
	 * 确定激活的配置文件
	 *
	 * @param activeProfileList 激活的配置文件列表
	 * @return 确定的激活配置文件
	 */
	private static String determineActiveProfile(List<String> activeProfileList) {
		if (activeProfileList.isEmpty()) {
			String defaultProfile = AppConstant.DEV_CODE;
			activeProfileList.add(defaultProfile);
			return defaultProfile;
		} else if (activeProfileList.size() == 1) {
			return activeProfileList.get(0);
		} else {
			throw new IllegalStateException("不可同时存在多个环境变量: " + String.join(", ", activeProfileList));
		}
	}

	/**
	 * 设置系统属性
	 *
	 * @param appName 应用名称
	 * @param profile 激活的配置文件
	 */
	private static void setSystemProperties(String appName, String profile) {
		Properties props = System.getProperties();
		props.setProperty("spring.application.name", appName);
		props.setProperty("spring.profiles.active", profile);
		props.setProperty("info.version", AppConstant.APPLICATION_VERSION);
		props.setProperty("info.desc", appName);
		props.setProperty("file.encoding", StandardCharsets.UTF_8.name());
		props.setProperty("blade.env", profile);
		props.setProperty("blade.name", appName);
		props.setProperty("blade.is-local", String.valueOf(isLocalDev()));
		props.setProperty("blade.dev-mode", profile.equals(AppConstant.PROD_CODE) ? "false" : "true");
		props.setProperty("blade.service.version", AppConstant.APPLICATION_VERSION);
		props.setProperty("loadbalancer.client.name", appName);
	}

	/**
	 * 设置默认属性
	 *
	 * @param appName 应用名称
	 * @param profile 激活的配置文件
	 * @return 默认属性集
	 */
	private static Properties setDefaultProperties(String appName, String profile) {
		Properties defaultProperties = new Properties();
		defaultProperties.setProperty("nacos.logging.default.config.enabled", "false");
		defaultProperties.setProperty("spring.main.allow-bean-definition-overriding", "true");
		defaultProperties.setProperty("spring.sleuth.sampler.percentage", "1.0");
		defaultProperties.setProperty("spring.cloud.alibaba.seata.tx-service-group", appName.concat(NacosConstant.NACOS_GROUP_SUFFIX));
		defaultProperties.setProperty("spring.cloud.nacos.config.file-extension", NacosConstant.NACOS_CONFIG_FORMAT);
		defaultProperties.setProperty("spring.cloud.nacos.config.shared-configs[0].data-id", NacosConstant.sharedDataId());
		defaultProperties.setProperty("spring.cloud.nacos.config.shared-configs[0].group", NacosConstant.NACOS_CONFIG_GROUP);
		defaultProperties.setProperty("spring.cloud.nacos.config.shared-configs[0].refresh", NacosConstant.NACOS_CONFIG_REFRESH);
		defaultProperties.setProperty("spring.cloud.nacos.config.shared-configs[1].data-id", NacosConstant.sharedDataId(profile));
		defaultProperties.setProperty("spring.cloud.nacos.config.shared-configs[1].group", NacosConstant.NACOS_CONFIG_GROUP);
		defaultProperties.setProperty("spring.cloud.nacos.config.shared-configs[1].refresh", NacosConstant.NACOS_CONFIG_REFRESH);
		return defaultProperties;
	}

	/**
	 * 加载自定义组件
	 *
	 * @param builder SpringApplicationBuilder实例
	 * @param appName 应用名称
	 * @param profile 激活的配置文件
	 */
	private static void loadCustomComponents(SpringApplicationBuilder builder, String appName, String profile) {
		ServiceLoader<LauncherService> serviceLoader = ServiceLoader.load(LauncherService.class);
		serviceLoader.forEach(launcherService -> launcherService.launcher(builder, appName, profile, isLocalDev()));
	}
}
