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
package org.springblade.core.auto.common;

import org.springblade.core.auto.annotation.*;

/**
 * 注解类型
 *
 * @author L.cm
 */
public enum BootAutoType {
	/**
	 * Component，组合注解，添加到 spring.factories
	 */
	COMPONENT(BootAutoType.COMPONENT_ANNOTATION, "org.springframework.boot.autoconfigure.EnableAutoConfiguration"),
	/**
	 * ApplicationContextInitializer 添加到 spring.factories
	 */
	CONTEXT_INITIALIZER(AutoContextInitializer.class.getName(), "org.springframework.context.ApplicationContextInitializer"),
	/**
	 * ApplicationListener 添加到 spring.factories
	 */
	LISTENER(AutoListener.class.getName(), "org.springframework.context.ApplicationListener"),
	/**
	 * SpringApplicationRunListener 添加到 spring.factories
	 */
	RUN_LISTENER(AutoRunListener.class.getName(), "org.springframework.boot.SpringApplicationRunListener"),
	/**
	 * FailureAnalyzer 添加到 spring.factories
	 */
	FAILURE_ANALYZER(AutoFailureAnalyzer.class.getName(), "org.springframework.boot.diagnostics.FailureAnalyzer"),
	/**
	 * EnvironmentPostProcessor 添加到 spring.factories
	 */
	ENV_POST_PROCESSOR(AutoEnvPostProcessor.class.getName(), "org.springframework.boot.env.EnvironmentPostProcessor");

	private final String annotationName;
	private final String configureKey;

	BootAutoType(String annotationName, String configureKey) {
		this.annotationName = annotationName;
		this.configureKey = configureKey;
	}

	public final String getAnnotationName() {
		return annotationName;
	}

	public final String getConfigureKey() {
		return configureKey;
	}

	public static final String COMPONENT_ANNOTATION = "org.springframework.stereotype.Component";
}
