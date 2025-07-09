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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springblade.core.tool.function.CheckedSupplier;
import org.springblade.core.tool.utils.Exceptions;

import java.util.concurrent.TimeUnit;

/**
 * 锁客户端
 *
 * @author L.cm
 */
@Slf4j
@RequiredArgsConstructor
public class RedisLockClientImpl implements RedisLockClient {
	private final RedissonClient redissonClient;

	@Override
	public boolean tryLock(String lockName, LockType lockType, long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException {
		RLock lock = getLock(lockName, lockType);
		return lock.tryLock(waitTime, leaseTime, timeUnit);
	}

	@Override
	public void unLock(String lockName, LockType lockType) {
		RLock lock = getLock(lockName, lockType);
		// 仅仅在已经锁定和当前线程持有锁时解锁
		if (lock.isLocked() && lock.isHeldByCurrentThread()) {
			lock.unlock();
		}
	}

	private RLock getLock(String lockName, LockType lockType) {
		RLock lock;
		if (LockType.REENTRANT == lockType) {
			lock = redissonClient.getLock(lockName);
		} else {
			lock = redissonClient.getFairLock(lockName);
		}
		return lock;
	}

	@Override
	public <T> T lock(String lockName, LockType lockType, long waitTime, long leaseTime, TimeUnit timeUnit, CheckedSupplier<T> supplier) {
		try {
			boolean result = this.tryLock(lockName, lockType, waitTime, leaseTime, timeUnit);
			if (result) {
				return supplier.get();
			}
		} catch (Throwable e) {
			throw Exceptions.unchecked(e);
		} finally {
			this.unLock(lockName, lockType);
		}
		return null;
	}

}
