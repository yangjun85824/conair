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
package org.springblade.core.log.listener;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;
import org.springblade.core.log.utils.ElkPropsUtil;
import org.springblade.core.tool.utils.StringUtil;

/**
 * logback监听类
 *
 * @author Chill
 */
public class LoggerStartupListener extends ContextAwareBase implements LoggerContextListener, LifeCycle {

	@Override
	public void start() {
		Context context = getContext();
		context.putProperty("ELK_MODE", "FALSE");
		context.putProperty("STDOUT_APPENDER", "STDOUT");
		context.putProperty("INFO_APPENDER", "INFO");
		context.putProperty("ERROR_APPENDER", "ERROR");
		context.putProperty("DESTINATION", "127.0.0.1:9000");
		String destination = ElkPropsUtil.getDestination();
		if (StringUtil.isNotBlank(destination)) {
			context.putProperty("ELK_MODE", "TRUE");
			context.putProperty("STDOUT_APPENDER", "STDOUT_LOGSTASH");
			context.putProperty("INFO_APPENDER", "INFO_LOGSTASH");
			context.putProperty("ERROR_APPENDER", "ERROR_LOGSTASH");
			context.putProperty("DESTINATION", destination);
		}
	}

	@Override
	public void stop() {

	}

	@Override
	public boolean isStarted() {
		return false;
	}

	@Override
	public boolean isResetResistant() {
		return false;
	}

	@Override
	public void onStart(LoggerContext context) {

	}

	@Override
	public void onReset(LoggerContext context) {

	}

	@Override
	public void onStop(LoggerContext context) {

	}

	@Override
	public void onLevelChange(Logger logger, Level level) {

	}
}
