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

import org.springframework.lang.Nullable;

import java.util.function.Function;

/**
 * Blade微服务上下文
 *
 * @author L.cm
 */
public interface BladeContext {

	/**
	 * 获取 请求 id
	 *
	 * @return 请求id
	 */
	@Nullable
	String getRequestId();

	/**
	 * 账号id
	 *
	 * @return 账号id
	 */
	@Nullable
	String getAccountId();

	/**
	 * 获取租户id
	 *
	 * @return 租户id
	 */
	@Nullable
	String getTenantId();

	/**
	 * 获取上下文中的数据
	 *
	 * @param ctxKey 上下文中的key
	 * @return 返回对象
	 */
	@Nullable
	String get(String ctxKey);

	/**
	 * 获取上下文中的数据
	 *
	 * @param ctxKey   上下文中的key
	 * @param function 函数式
	 * @param <T>      泛型对象
	 * @return 返回对象
	 */
	@Nullable
	<T> T get(String ctxKey, Function<String, T> function);
}
