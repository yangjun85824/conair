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
package org.springblade.core.boot.request;

import lombok.AllArgsConstructor;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Request全局过滤
 *
 * @author Chill
 */
@AllArgsConstructor
public class BladeRequestFilter implements Filter {

	private final RequestProperties requestProperties;
	private final XssProperties xssProperties;
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public void init(FilterConfig config) {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String path = ((HttpServletRequest) request).getServletPath();
		// 跳过 Request 包装
		if (!requestProperties.getEnabled() || isRequestSkip(path)) {
			chain.doFilter(request, response);
		}
		// 默认 Request 包装
		else if (!xssProperties.getEnabled() || isXssSkip(path)) {
			BladeHttpServletRequestWrapper bladeRequest = new BladeHttpServletRequestWrapper((HttpServletRequest) request);
			chain.doFilter(bladeRequest, response);
		}
		// Xss Request 包装
		else {
			XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
			chain.doFilter(xssRequest, response);
		}
	}

	private boolean isRequestSkip(String path) {
		return requestProperties.getSkipUrl().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
	}

	private boolean isXssSkip(String path) {
		return xssProperties.getSkipUrl().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
	}

	@Override
	public void destroy() {

	}

}
