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
package org.springblade.core.boot.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.boot.error.ErrorType;
import org.springblade.core.boot.error.ErrorUtil;
import org.springblade.core.context.BladeContext;
import org.springblade.core.context.BladeRunnableWrapper;
import org.springblade.core.launch.props.BladeProperties;
import org.springblade.core.log.constant.EventConstant;
import org.springblade.core.log.event.ErrorLogEvent;
import org.springblade.core.log.model.LogError;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.task.ThreadPoolTaskExecutorCustomizer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步处理
 *
 * @author Chill
 */
@Slf4j
@Configuration
@EnableAsync
@EnableScheduling
@AllArgsConstructor
public class BladeExecutorConfiguration extends AsyncConfigurerSupport {

	private final BladeContext bladeContext;
	private final BladeProperties bladeProperties;
	private final ApplicationEventPublisher publisher;

	@Bean
	public ThreadPoolTaskExecutorCustomizer taskExecutorCustomizer() {
		return taskExecutor -> {
			taskExecutor.setThreadNamePrefix("async-task-");
			taskExecutor.setTaskDecorator(BladeRunnableWrapper::new);
			taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		};
	}

	@Bean
	public ThreadPoolTaskExecutorCustomizer taskSchedulerCustomizer() {
		return taskExecutor -> {
			taskExecutor.setThreadNamePrefix("async-scheduler");
			taskExecutor.setTaskDecorator(BladeRunnableWrapper::new);
			taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		};
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new BladeAsyncUncaughtExceptionHandler(bladeContext, bladeProperties, publisher);
	}

	@RequiredArgsConstructor
	private static class BladeAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {
		private final BladeContext bladeContext;
		private final BladeProperties bladeProperties;
		private final ApplicationEventPublisher eventPublisher;

		@Override
		public void handleUncaughtException(@NonNull Throwable error, @NonNull Method method, @NonNull Object... params) {
			log.error("Unexpected exception occurred invoking async method: {}", method, error);
			LogError logError = new LogError();
			// 服务信息、环境、异常类型
			logError.setParams(ErrorType.ASYNC.getType());
			logError.setEnv(bladeProperties.getEnv());
			logError.setServiceId(bladeProperties.getName());
			logError.setRequestUri(bladeContext.getRequestId());
			// 堆栈信息
			ErrorUtil.initErrorInfo(error, logError);
			Map<String, Object> event = new HashMap<>(16);
			event.put(EventConstant.EVENT_LOG, logError);
			eventPublisher.publishEvent(new ErrorLogEvent(event));
		}
	}

}
