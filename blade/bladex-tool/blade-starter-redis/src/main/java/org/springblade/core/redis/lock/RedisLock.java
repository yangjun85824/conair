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

package org.springblade.core.redis.lock;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁注解，redisson，支持的锁的种类有很多，适合注解形式的只有重入锁、公平锁
 *
 * <p>
 * 1. 可重入锁（Reentrant Lock）
 * 2. 公平锁（Fair Lock）
 * 3. 联锁（MultiLock）
 * 4. 红锁（RedLock）
 * 5. 读写锁（ReadWriteLock）
 * </p>
 *
 * @author L.cm
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RedisLock {

	/**
	 * 分布式锁的 key，必须：请保持唯一性
	 *
	 * @return key
	 */
	String value();

	/**
	 * 分布式锁参数，可选，支持 spring el # 读取方法参数和 @ 读取 spring bean
	 *
	 * @return param
	 */
	String param() default "";

	/**
	 * 等待锁超时时间，默认30
	 *
	 * @return int
	 */
	long waitTime() default 30;

	/**
	 * 自动解锁时间，自动解锁时间一定得大于方法执行时间，否则会导致锁提前释放，默认100
	 *
	 * @return int
	 */
	long leaseTime() default 100;

	/**
	 * 时间单位，默认为秒
	 *
	 * @return 时间单位
	 */
	TimeUnit timeUnit() default TimeUnit.SECONDS;

	/**
	 * 默认公平锁
	 *
	 * @return LockType
	 */
	LockType type() default LockType.FAIR;
}
