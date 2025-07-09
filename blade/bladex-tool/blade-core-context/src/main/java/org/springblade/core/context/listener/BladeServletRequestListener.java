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
package org.springblade.core.context.listener;

import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springblade.core.context.BladeHttpHeadersGetter;
import org.springblade.core.context.props.BladeContextProperties;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.core.tool.utils.ThreadLocalUtil;
import org.springframework.http.HttpHeaders;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Servlet 请求监听器
 *
 * @author L.cm
 */
@RequiredArgsConstructor
public class BladeServletRequestListener implements ServletRequestListener {
	private final BladeContextProperties contextProperties;
	private final BladeHttpHeadersGetter httpHeadersGetter;

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		// MDC 获取透传的 变量
		BladeContextProperties.Headers headers = contextProperties.getHeaders();
		String requestId = request.getHeader(headers.getRequestId());
		if (StringUtil.isNotBlank(requestId)) {
			MDC.put(BladeConstant.MDC_REQUEST_ID_KEY, requestId);
		}
		String accountId = request.getHeader(headers.getAccountId());
		if (StringUtil.isNotBlank(accountId)) {
			MDC.put(BladeConstant.MDC_ACCOUNT_ID_KEY, accountId);
		}
		String tenantId = request.getHeader(headers.getTenantId());
		if (StringUtil.isNotBlank(tenantId)) {
			MDC.put(BladeConstant.MDC_TENANT_ID_KEY, tenantId);
		}
		// 处理 context，直接传递 request，因为 spring 中的尚未初始化完成
		HttpHeaders httpHeaders = httpHeadersGetter.get(request);
		ThreadLocalUtil.put(BladeConstant.CONTEXT_KEY, httpHeaders);
	}

	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		// 会话销毁时，清除上下文
		ThreadLocalUtil.clear();
		// 会话销毁时，清除 mdc
		MDC.remove(BladeConstant.MDC_REQUEST_ID_KEY);
		MDC.remove(BladeConstant.MDC_ACCOUNT_ID_KEY);
		MDC.remove(BladeConstant.MDC_TENANT_ID_KEY);
	}

}
