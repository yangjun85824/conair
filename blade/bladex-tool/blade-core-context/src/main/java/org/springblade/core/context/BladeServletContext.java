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
package org.springblade.core.context;

import lombok.RequiredArgsConstructor;
import org.springblade.core.context.props.BladeContextProperties;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.core.tool.utils.ThreadLocalUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;

import java.util.function.Function;

import static org.springblade.core.tool.constant.BladeConstant.CONTEXT_KEY;

/**
 * blade servlet 上下文，跨线程失效
 *
 * @author L.cm
 */
@RequiredArgsConstructor
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class BladeServletContext implements BladeContext {
	private final BladeContextProperties contextProperties;
	private final BladeHttpHeadersGetter httpHeadersGetter;

	@Nullable
	@Override
	public String getRequestId() {
		return get(contextProperties.getHeaders().getRequestId());
	}

	@Nullable
	@Override
	public String getAccountId() {
		return get(contextProperties.getHeaders().getAccountId());
	}

	@Nullable
	@Override
	public String getTenantId() {
		return get(contextProperties.getHeaders().getTenantId());
	}

	@Nullable
	@Override
	public String get(String ctxKey) {
		HttpHeaders headers = ThreadLocalUtil.getIfAbsent(CONTEXT_KEY, httpHeadersGetter::get);
		if (headers == null || headers.isEmpty()) {
			return null;
		}
		return headers.getFirst(ctxKey);
	}

	@Nullable
	@Override
	public <T> T get(String ctxKey, Function<String, T> function) {
		String ctxValue = get(ctxKey);
		if (StringUtil.isBlank(ctxValue)) {
			return null;
		}
		return function.apply(ctxKey);
	}

}
