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

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式 限流注解，默认速率为 600/ms
 *
 * @author L.cm
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RateLimiter {

	/**
	 * 限流的 key 支持，必须：请保持唯一性
	 *
	 * @return key
	 */
	String value();

	/**
	 * 限流的参数，可选，支持 spring el # 读取方法参数和 @ 读取 spring bean
	 *
	 * @return param
	 */
	String param() default "";

	/**
	 * 支持的最大请求，默认: 100
	 *
	 * @return 请求数
	 */
	long max() default 100L;

	/**
	 * 持续时间，默认: 3600
	 *
	 * @return 持续时间
	 */
	long ttl() default 1L;

	/**
	 * 时间单位，默认为分
	 *
	 * @return TimeUnit
	 */
	TimeUnit timeUnit() default TimeUnit.MINUTES;
}
