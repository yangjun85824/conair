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

import org.slf4j.MDC;
import org.springblade.core.tool.utils.ThreadLocalUtil;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 多线程中传递 context 和 mdc
 *
 * @author L.cm
 */
public class BladeCallableWrapper<V> implements Callable<V> {
	private final Callable<V> delegate;
	private final Map<String, Object> tlMap;
	/**
	 * logback 下有可能为 null
	 */
	@Nullable
	private final Map<String, String> mdcMap;

	public BladeCallableWrapper(Callable<V> callable) {
		this.delegate = callable;
		this.tlMap = ThreadLocalUtil.getAll();
		this.mdcMap = MDC.getCopyOfContextMap();
	}

	@Override
	public V call() throws Exception {
		if (!tlMap.isEmpty()) {
			ThreadLocalUtil.put(tlMap);
		}
		if (mdcMap != null && !mdcMap.isEmpty()) {
			MDC.setContextMap(mdcMap);
		}
		try {
			return delegate.call();
		} finally {
			tlMap.clear();
			if (mdcMap != null) {
				mdcMap.clear();
			}
			ThreadLocalUtil.clear();
			MDC.clear();
		}
	}
}
