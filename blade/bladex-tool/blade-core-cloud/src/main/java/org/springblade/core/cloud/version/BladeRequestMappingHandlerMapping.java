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
package org.springblade.core.cloud.version;

import org.springblade.core.cloud.annotation.ApiVersion;
import org.springblade.core.cloud.annotation.UrlVersion;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * url版本号处理 和 header 版本处理
 *
 * <p>
 *     url: /v1/user/{id}
 *     header: Accept application/vnd.blade.VERSION+json
 * </p>
 *
 * 注意：c 代表客户端版本
 *
 * @author L.cm
 */
public class BladeRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

	@Nullable
	@Override
	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		RequestMappingInfo mappingInfo = super.getMappingForMethod(method, handlerType);
		if (mappingInfo != null) {
			RequestMappingInfo apiVersionMappingInfo = getApiVersionMappingInfo(method, handlerType);
			return apiVersionMappingInfo == null ? mappingInfo : apiVersionMappingInfo.combine(mappingInfo);
		}
		return null;
	}

	@Nullable
	private RequestMappingInfo getApiVersionMappingInfo(Method method, Class<?> handlerType) {
		// url 上的版本，优先获取方法上的版本
		UrlVersion urlVersion = AnnotatedElementUtils.findMergedAnnotation(method, UrlVersion.class);
		// 再次尝试类上的版本
		if (urlVersion == null || StringUtil.isBlank(urlVersion.value())) {
			urlVersion = AnnotatedElementUtils.findMergedAnnotation(handlerType, UrlVersion.class);
		}
		// Media Types 版本信息
		ApiVersion apiVersion = AnnotatedElementUtils.findMergedAnnotation(method, ApiVersion.class);
		// 再次尝试类上的版本
		if (apiVersion == null || StringUtil.isBlank(apiVersion.value())) {
			apiVersion = AnnotatedElementUtils.findMergedAnnotation(handlerType, ApiVersion.class);
		}
		boolean nonUrlVersion = urlVersion == null || StringUtil.isBlank(urlVersion.value());
		boolean nonApiVersion = apiVersion == null || StringUtil.isBlank(apiVersion.value());
		// 先判断同时不纯在
		if (nonUrlVersion && nonApiVersion) {
			return null;
		}
		// 如果 header 版本不存在
		RequestMappingInfo.Builder mappingInfoBuilder = null;
		if (nonApiVersion) {
			mappingInfoBuilder = RequestMappingInfo.paths(urlVersion.value());
		} else {
			mappingInfoBuilder = RequestMappingInfo.paths(StringPool.EMPTY);
		}
		// 如果url版本不存在
		if (nonUrlVersion) {
			String versionMediaTypes = new BladeMediaType(apiVersion.value()).toString();
			mappingInfoBuilder.produces(versionMediaTypes);
		}
		return mappingInfoBuilder.options(super.getBuilderConfiguration()).build();
	}

	@Override
	protected void handlerMethodsInitialized(Map<RequestMappingInfo, HandlerMethod> handlerMethods) {
		// 打印路由信息 spring boot 2.1 去掉了这个 日志的打印
		if (logger.isInfoEnabled()) {
			for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
				RequestMappingInfo mapping = entry.getKey();
				HandlerMethod handlerMethod = entry.getValue();
				logger.info("Mapped \"" + mapping + "\" onto " + handlerMethod);
			}
		}
		super.handlerMethodsInitialized(handlerMethods);
	}
}
