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
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.props.BladeSecureProperties;
import org.springblade.core.secure.provider.ResponseProvider;
import org.springblade.core.secure.utils.AuthUtil;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

import static org.springblade.core.secure.constant.SecureConstant.*;


/**
 * 签名认证拦截器
 *
 * @author Chill
 */
@Slf4j
@AllArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

	private final BladeSecureProperties secureProperties;
	private final static List<String> DEFAULT_STRICT_SKIP_URL = new ArrayList<>();

	static {
		// 严格模式默认跳过的URL
		// 为了安全考虑目前不支持通过外部配置方式增加严格模式放行的功能
		DEFAULT_STRICT_SKIP_URL.add("/menu/routes");
		DEFAULT_STRICT_SKIP_URL.add("/menu/buttons");
		DEFAULT_STRICT_SKIP_URL.add("/menu/top-menu");
		DEFAULT_STRICT_SKIP_URL.add("/user/register-guest");
		DEFAULT_STRICT_SKIP_URL.add("/blade-system/menu/routes");
		DEFAULT_STRICT_SKIP_URL.add("/blade-system/menu/buttons");
		DEFAULT_STRICT_SKIP_URL.add("/blade-system/menu/top-menu");
		DEFAULT_STRICT_SKIP_URL.add("/blade-system/user/register-guest");
	}

	@Override
	public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
		BladeUser currentUser = AuthUtil.getUser();
		if (currentUser == null) {
			ResponseProvider.logAuthFailure(request, response, SIGN_FAILED);
			return false;
		}
		if (checkStrictToken(request, currentUser)) {
			ResponseProvider.logAuthFailure(request, response, USER_INCOMPLETE);
			return false;
		}
		if (checkStrictHeader()) {
			ResponseProvider.logAuthFailure(request, response, SECURE_HEADER_INCOMPLETE);
			return false;
		}
		return true;
	}

	private boolean checkStrictToken(HttpServletRequest request, BladeUser currentUser) {
		String requestUrl = request.getRequestURI(); // 获取当前请求的URL
		boolean skip = DEFAULT_STRICT_SKIP_URL.stream()
			.anyMatch(requestUrl::equals); // 判断当前请求的URL是否在跳过列表中

		// 如果请求的URL需要跳过检查或者不需要严格检查Token，则返回false
		return !skip && secureProperties.getStrictToken() && AuthUtil.userIncomplete(currentUser);
	}

	private boolean checkStrictHeader() {
		return secureProperties.getStrictHeader() && AuthUtil.secureHeaderIncomplete();
	}


}
