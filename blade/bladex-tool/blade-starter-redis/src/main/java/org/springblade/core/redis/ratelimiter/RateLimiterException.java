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

import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * 限流异常
 *
 * @author L.cm
 */
@Getter
public class RateLimiterException extends RuntimeException {
	private final String key;
	private final long max;
	private final long ttl;
	private final TimeUnit timeUnit;

	public RateLimiterException(String key, long max, long ttl, TimeUnit timeUnit) {
		super(String.format("您的访问次数已超限：%s，速率：%d/%ds", key, max, timeUnit.toSeconds(ttl)));
		this.key = key;
		this.max = max;
		this.ttl = ttl;
		this.timeUnit = timeUnit;
	}
}
