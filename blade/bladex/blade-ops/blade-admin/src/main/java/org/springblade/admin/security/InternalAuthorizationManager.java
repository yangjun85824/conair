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
package org.springblade.admin.security;

import org.springblade.core.launch.utils.INetUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Optional;

/**
 * 内网认证管理，内网放行，外网认证
 *
 * @author L.cm
 */
public class InternalAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
	private static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";

	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
		return Mono.just(getAuthorizationDecision(context));
	}

	private static AuthorizationDecision getAuthorizationDecision(AuthorizationContext context) {
		return new AuthorizationDecision(isInternalNet(context));
	}

	/**
	 * 判断是否内网 ip 请求
	 *
	 * @param context AuthorizationContext
	 * @return 是否内网 ip
	 */
	private static boolean isInternalNet(AuthorizationContext context) {
		ServerHttpRequest request = Optional.ofNullable(context)
			.map(AuthorizationContext::getExchange)
			.map(ServerWebExchange::getRequest)
			.orElse(null);
		if (request == null) {
			return false;
		}
		HttpHeaders headers = request.getHeaders();
		// 如果没有 X-Forwarded-For 代表为 admin 拉取
		if (!headers.containsKey(HEADER_X_FORWARDED_FOR)) {
			return true;
		}
		return Optional.of(request)
			.map(ServerHttpRequest::getRemoteAddress)
			.map(InetSocketAddress::getAddress)
			.map(INetUtil::isInternalIp)
			.orElse(false);
	}

}
