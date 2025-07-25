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

import org.springblade.core.tool.function.CheckedSupplier;

import java.util.concurrent.TimeUnit;

/**
 * 锁客户端
 *
 * @author L.cm
 */
public interface RedisLockClient {

	/**
	 * 尝试获取锁
	 *
	 * @param lockName  锁名
	 * @param lockType  锁类型
	 * @param waitTime  等待时间
	 * @param leaseTime 自动解锁时间，自动解锁时间一定得大于方法执行时间
	 * @param timeUnit  时间参数
	 * @return 是否成功
	 * @throws InterruptedException InterruptedException
	 */
	boolean tryLock(String lockName, LockType lockType, long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException;

	/**
	 * 解锁
	 *
	 * @param lockName 锁名
	 * @param lockType 锁类型
	 */
	void unLock(String lockName, LockType lockType);

	/**
	 * 自定获取锁后执行方法
	 *
	 * @param lockName  锁名
	 * @param lockType  锁类型
	 * @param waitTime  等待锁超时时间
	 * @param leaseTime 自动解锁时间，自动解锁时间一定得大于方法执行时间，否则会导致锁提前释放，默认100
	 * @param timeUnit  时间单位
	 * @param supplier  获取锁后的回调
	 * @return 返回的数据
	 */
	<T> T lock(String lockName, LockType lockType, long waitTime, long leaseTime, TimeUnit timeUnit, CheckedSupplier<T> supplier);

	/**
	 * 公平锁
	 *
	 * @param lockName  锁名
	 * @param waitTime  等待锁超时时间
	 * @param leaseTime 自动解锁时间，自动解锁时间一定得大于方法执行时间，否则会导致锁提前释放，默认100
	 * @param supplier  获取锁后的回调
	 * @return 返回的数据
	 */
	default <T> T lockFair(String lockName, long waitTime, long leaseTime, CheckedSupplier<T> supplier) {
		return lock(lockName, LockType.FAIR, waitTime, leaseTime, TimeUnit.SECONDS, supplier);
	}

	/**
	 * 可重入锁
	 *
	 * @param lockName  锁名
	 * @param waitTime  等待锁超时时间
	 * @param leaseTime 自动解锁时间，自动解锁时间一定得大于方法执行时间，否则会导致锁提前释放，默认100
	 * @param supplier  获取锁后的回调
	 * @return 返回的数据
	 */
	default <T> T lockReentrant(String lockName, long waitTime, long leaseTime, CheckedSupplier<T> supplier) {
		return lock(lockName, LockType.REENTRANT, waitTime, leaseTime, TimeUnit.SECONDS, supplier);
	}

}
