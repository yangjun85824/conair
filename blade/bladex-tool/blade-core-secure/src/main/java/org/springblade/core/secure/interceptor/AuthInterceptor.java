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
import org.springblade.core.secure.auth.AuthFun;
import org.springblade.core.secure.props.AuthSecure;
import org.springblade.core.secure.provider.HttpMethod;
import org.springblade.core.secure.provider.ResponseProvider;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

import static org.springblade.core.secure.constant.SecureConstant.AUTHORIZATION_FAILED;

/**
 * 自定义授权拦截器校验
 *
 * @author Chill
 */
@Slf4j
@AllArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

	/**
	 * 表达式处理
	 */
	private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
	private static final EvaluationContext EVALUATION_CONTEXT = new StandardEvaluationContext(new AuthFun());
	private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

	/**
	 * 授权集合
	 */
	private final List<AuthSecure> authSecures;

	@Override
	public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
		boolean check = authSecures.stream().filter(authSecure -> checkAuth(request, authSecure)).findFirst().map(
			authSecure -> checkExpression(authSecure.getExpression())
		).orElse(Boolean.TRUE);
		if (!check) {
			ResponseProvider.logAuthFailure(request, response, AUTHORIZATION_FAILED);
			return false;
		}
		return true;
	}

	/**
	 * 检测授权
	 */
	private boolean checkAuth(HttpServletRequest request, AuthSecure authSecure) {
		return checkMethod(request, authSecure.getMethod()) && checkPath(request, authSecure.getPattern());
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
	private boolean checkExpression(String expression) {
		Boolean result = EXPRESSION_PARSER.parseExpression(expression).getValue(EVALUATION_CONTEXT, Boolean.class);
		return result != null ? result : false;
	}

}
