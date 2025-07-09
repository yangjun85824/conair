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
package org.springblade.core.secure.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.secure.props.BasicSecure;
import org.springblade.core.secure.provider.HttpMethod;
import org.springblade.core.secure.provider.ResponseProvider;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

import static org.springblade.core.secure.constant.SecureConstant.BASIC_REALM_HEADER_KEY;
import static org.springblade.core.secure.constant.SecureConstant.BASIC_REALM_HEADER_VALUE;

/**
 * 基础认证拦截器校验
 *
 * @author Chill
 */
@Slf4j
@AllArgsConstructor
public class BasicInterceptor implements HandlerInterceptor {

	/**
	 * 表达式匹配
	 */
	private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

	/**
	 * 授权集合
	 */
	private final List<BasicSecure> basicSecures;

	@Override
	public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
		boolean check = basicSecures.stream().filter(basicSecure -> checkAuth(request, basicSecure)).findFirst().map(
			authSecure -> checkBasic(authSecure.getUsername(), authSecure.getPassword())
		).orElse(Boolean.TRUE);
		if (!check) {
			log.warn("授权认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), WebUtil.getIP(request), JsonUtil.toJson(request.getParameterMap()));
			response.setHeader(BASIC_REALM_HEADER_KEY, BASIC_REALM_HEADER_VALUE);
			ResponseProvider.write(response);
			return false;
		}
		return true;
	}

	/**
	 * 检测授权
	 */
	private boolean checkAuth(HttpServletRequest request, BasicSecure basicSecure) {
		return checkMethod(request, basicSecure.getMethod()) && checkPath(request, basicSecure.getPattern());
	}

	/**
	 * 检测请求方法
	 */
	private boolean checkMethod(HttpServletRequest request, HttpMethod method) {
		return method == HttpMethod.ALL || (
			method != null && method == HttpMethod.of(request.getMethod())
		);
	}

	/**
	 * 检测路径匹配
	 */
	private boolean checkPath(HttpServletRequest request, String pattern) {
		String servletPath = request.getServletPath();
		String pathInfo = request.getPathInfo();
		if (pathInfo != null && !pathInfo.isEmpty()) {
			servletPath = servletPath + pathInfo;
		}
		return ANT_PATH_MATCHER.match(pattern, servletPath);
	}

	/**
	 * 检测表达式
	 */
	private boolean checkBasic(String username, String password) {
		try {
			String[] tokens = SecureUtil.extractAndDecodeAuthorization();
			return username.equals(tokens[0]) && password.equals(tokens[1]);
		} catch (Exception e) {
			log.warn("授权认证失败，错误信息：{}", e.getMessage());
			return false;
		}
	}


}
