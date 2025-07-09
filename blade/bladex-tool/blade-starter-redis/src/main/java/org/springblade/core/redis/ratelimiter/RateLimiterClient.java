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

package org.springblade.core.redis.ratelimiter;


import org.springblade.core.tool.function.CheckedSupplier;
import org.springblade.core.tool.utils.Exceptions;

import java.util.concurrent.TimeUnit;

/**
 * RateLimiter 限流 Client
 *
 * @author L.cm
 */
public interface RateLimiterClient {

	/**
	 * 服务是否被限流
	 *
	 * @param key 自定义的key，请保证唯一
	 * @param max 支持的最大请求
	 * @param ttl 时间,单位默认为秒（seconds）
	 * @return 是否允许
	 */
	default boolean isAllowed(String key, long max, long ttl) {
		return this.isAllowed(key, max, ttl, TimeUnit.SECONDS);
	}

	/**
	 * 服务是否被限流
	 *
	 * @param key      自定义的key，请保证唯一
	 * @param max      支持的最大请求
	 * @param ttl      时间
	 * @param timeUnit 时间单位
	 * @return 是否允许
	 */
	boolean isAllowed(String key, long max, long ttl, TimeUnit timeUnit);

	/**
	 * 服务限流，被限制时抛出 RateLimiterException 异常，需要自行处理异常
	 *
	 * @param key      自定义的key，请保证唯一
	 * @param max      支持的最大请求
	 * @param ttl      时间
	 * @param supplier Supplier 函数式
	 * @return 函数执行结果
	 */
	default <T> T allow(String key, long max, long ttl, CheckedSupplier<T> supplier) {
		return allow(key, max, ttl, TimeUnit.SECONDS, supplier);
	}

	/**
	 * 服务限流，被限制时抛出 RateLimiterException 异常，需要自行处理异常
	 *
	 * @param key      自定义的key，请保证唯一
	 * @param max      支持的最大请求
	 * @param ttl      时间
	 * @param timeUnit 时间单位
	 * @param supplier Supplier 函数式
	 * @param <T>
	 * @return 函数执行结果
	 */
	default <T> T allow(String key, long max, long ttl, TimeUnit timeUnit, CheckedSupplier<T> supplier) {
		boolean isAllowed = this.isAllowed(key, max, ttl, timeUnit);
		if (isAllowed) {
			try {
				return supplier.get();
			} catch (Throwable e) {
				throw Exceptions.unchecked(e);
			}
		}
		throw new RateLimiterException(key, max, ttl, timeUnit);
	}
}
