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
package org.springblade.core.launch.service;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;

/**
 * launcher 扩展 用于一些组件发现
 *
 * @author Chill
 */
public interface LauncherService extends Ordered, Comparable<LauncherService> {

	/**
	 * 启动时 处理 SpringApplicationBuilder
	 *
	 * @param builder    SpringApplicationBuilder
	 * @param appName    SpringApplicationAppName
	 * @param profile    SpringApplicationProfile
	 * @param isLocalDev SpringApplicationIsLocalDev
	 */
	void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev);

	/**
	 * 获取排列顺序
	 *
	 * @return order
	 */
	@Override
	default int getOrder() {
		return 0;
	}

	/**
	 * 对比排序
	 *
	 * @param o LauncherService
	 * @return compare
	 */
	@Override
	default int compareTo(LauncherService o) {
		return Integer.compare(this.getOrder(), o.getOrder());
	}

}
