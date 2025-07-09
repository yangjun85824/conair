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

package org.springblade.core.http.cache;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.utils.ClassUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Clock;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Http cache拦截器
 *
 * @author L.cm
 */
@Slf4j
@AllArgsConstructor
public class HttpCacheInterceptor implements HandlerInterceptor {
	private final HttpCacheService httpCacheService;

	@Override
	public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
		// 非控制器请求直接跳出
		if (!(handler instanceof HandlerMethod handlerMethod)) {
			return true;
		}
		// http cache 针对 HEAD 和 GET 请求
		String method = request.getMethod();
		HttpMethod httpMethod = HttpMethod.valueOf(method);
		List<HttpMethod> allowList = Arrays.asList(HttpMethod.HEAD, HttpMethod.GET);
		if (!allowList.contains(httpMethod)) {
			return true;
		}
		// 处理HttpCacheAble
		HttpCacheAble cacheAble = ClassUtil.getAnnotation(handlerMethod, HttpCacheAble.class);
		if (cacheAble == null) {
			return true;
		}

		// 最后修改时间
		long ims = request.getDateHeader(HttpHeaders.IF_MODIFIED_SINCE);
		long now = Clock.systemUTC().millis();
		// 缓存时间,秒
		long maxAge = cacheAble.maxAge();
		// 缓存时间,毫秒
		long maxAgeMicros = TimeUnit.SECONDS.toMillis(maxAge);
		String cacheKey = request.getRequestURI() + "?" + request.getQueryString();
		// 后端可控制http-cache超时
		boolean hasCache = httpCacheService.get(cacheKey);
		// 如果header头没有过期
		if (hasCache && ims + maxAgeMicros > now) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			response.setHeader(HttpHeaders.CACHE_CONTROL, "max-age=" + maxAge);
			response.addDateHeader(HttpHeaders.EXPIRES, ims + maxAgeMicros);
			response.addDateHeader(HttpHeaders.LAST_MODIFIED, ims);
			log.info("{} 304 {}", method, request.getRequestURI());
			return false;
		}
		response.setHeader(HttpHeaders.CACHE_CONTROL, "max-age=" + maxAge);
		response.addDateHeader(HttpHeaders.EXPIRES, now + maxAgeMicros);
		response.addDateHeader(HttpHeaders.LAST_MODIFIED, now);
		httpCacheService.set(cacheKey);
		return true;
	}

}
