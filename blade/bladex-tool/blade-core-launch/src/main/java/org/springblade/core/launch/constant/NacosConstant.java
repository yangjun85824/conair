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
package org.springblade.core.launch.constant;

/**
 * Nacos常量.
 *
 * @author Chill
 */
public interface NacosConstant {

	/**
	 * nacos 地址
	 */
	String NACOS_ADDR = "10.125.238.205:31114";

	/**
	 * nacos 配置前缀
	 */
	String NACOS_CONFIG_PREFIX = "blade";

	/**
	 * nacos 组配置后缀
	 */
	String NACOS_GROUP_SUFFIX = "-group";

	/**
	 * nacos 配置文件类型
	 */
	String NACOS_CONFIG_FORMAT = "yaml";

	/**
	 * nacos json配置文件类型
	 */
	String NACOS_CONFIG_JSON_FORMAT = "json";

	/**
	 * nacos 是否刷新
	 */
	String NACOS_CONFIG_REFRESH = "true";

	/**
	 * nacos 分组
	 */
	String NACOS_CONFIG_GROUP = "DEFAULT_GROUP";

	/**
	 * seata 分组
	 */
	String NACOS_SEATA_GROUP = "SEATA_GROUP";

	/**
	 * 构建服务对应的 dataId
	 *
	 * @param appName 服务名
	 * @return dataId
	 */
	static String dataId(String appName) {
		return appName + "." + NACOS_CONFIG_FORMAT;
	}

	/**
	 * 构建服务对应的 dataId
	 *
	 * @param appName 服务名
	 * @param profile 环境变量
	 * @return dataId
	 */
	static String dataId(String appName, String profile) {
		return dataId(appName, profile, NACOS_CONFIG_FORMAT);
	}

	/**
	 * 构建服务对应的 dataId
	 *
	 * @param appName 服务名
	 * @param profile 环境变量
	 * @param format  文件类型
	 * @return dataId
	 */
	static String dataId(String appName, String profile, String format) {
		return appName + "-" + profile + "." + format;
	}

	/**
	 * 服务默认加载的配置
	 *
	 * @return sharedDataIds
	 */
	static String sharedDataId() {
		return NACOS_CONFIG_PREFIX + "." + NACOS_CONFIG_FORMAT;
	}

	/**
	 * 服务默认加载的配置
	 *
	 * @param profile 环境变量
	 * @return sharedDataIds
	 */
	static String sharedDataId(String profile) {
		return NACOS_CONFIG_PREFIX + "-" + profile + "." + NACOS_CONFIG_FORMAT;
	}

	/**
	 * 服务默认加载的配置
	 *
	 * @param profile 环境变量
	 * @return sharedDataIds
	 */
	static String sharedDataIds(String profile) {
		return NACOS_CONFIG_PREFIX + "." + NACOS_CONFIG_FORMAT + "," + NACOS_CONFIG_PREFIX + "-" + profile + "." + NACOS_CONFIG_FORMAT;
	}

}
