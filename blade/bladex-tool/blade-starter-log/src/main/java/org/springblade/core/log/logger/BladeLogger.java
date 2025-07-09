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
package org.springblade.core.log.logger;

import lombok.extern.slf4j.Slf4j;
import org.springblade.core.log.publisher.UsualLogPublisher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

/**
 * 日志工具类
 *
 * @author Chill
 */
@Slf4j
public class BladeLogger implements InitializingBean {

	@Value("${spring.application.name}")
	private String serviceId;

	public void info(String id, String data) {
		UsualLogPublisher.publishEvent("info", id, data);
	}

	public void debug(String id, String data) {
		UsualLogPublisher.publishEvent("debug", id, data);
	}

	public void warn(String id, String data) {
		UsualLogPublisher.publishEvent("warn", id, data);
	}

	public void error(String id, String data) {
		UsualLogPublisher.publishEvent("error", id, data);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info(serviceId + ": BladeLogger init success!");
	}

}
