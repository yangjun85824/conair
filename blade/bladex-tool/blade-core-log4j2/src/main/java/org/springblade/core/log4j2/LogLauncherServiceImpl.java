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
package org.springblade.core.log4j2;

import org.springblade.core.auto.service.AutoService;
import org.springblade.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 日志启动器
 *
 * @author L.cm
 */
@AutoService(LauncherService.class)
public class LogLauncherServiceImpl implements LauncherService {

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		System.setProperty("logging.config", String.format("classpath:log/log4j2_%s.xml", profile));
		// RocketMQ-Client 4.2.0 Log4j2 配置文件冲突问题解决：https://www.jianshu.com/p/b30ae6dd3811
		System.setProperty("rocketmq.client.log.loadconfig", "false");
		//  RocketMQ-Client 4.3 设置默认为 slf4j
		System.setProperty("rocketmq.client.logUseSlf4j", "true");
		// 非本地 将 全部的 System.err 和 System.out 替换为log
		if (!isLocalDev) {
			System.setOut(LogPrintStream.out());
			System.setErr(LogPrintStream.err());
		}
	}

}
