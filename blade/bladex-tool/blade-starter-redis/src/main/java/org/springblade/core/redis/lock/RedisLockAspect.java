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
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springblade.core.tool.spel.BladeExpressionEvaluator;
import org.springblade.core.tool.utils.CharPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * redis 分布式锁
 *
 * @author L.cm
 */
@Aspect
@RequiredArgsConstructor
public class RedisLockAspect implements ApplicationContextAware {

	/**
	 * 表达式处理
	 */
	private static final BladeExpressionEvaluator EVALUATOR = new BladeExpressionEvaluator();
	/**
	 * redis 限流服务
	 */
	private final RedisLockClient redisLockClient;
	private ApplicationContext applicationContext;

	/**
	 * AOP 环切 注解 @RedisLock
	 */
	@Around("@annotation(redisLock)")
	public Object aroundRedisLock(ProceedingJoinPoint point, RedisLock redisLock) {
		String lockName = redisLock.value();
		Assert.hasText(lockName, "@RedisLock value must have length; it must not be null or empty");
		// el 表达式
		String lockParam = redisLock.param();
		// 表达式不为空
		String lockKey;
		if (StringUtil.isNotBlank(lockParam)) {
			String evalAsText = evalLockParam(point, lockParam);
			lockKey = lockName + CharPool.COLON + evalAsText;
		} else {
			lockKey = lockName;
		}
		LockType lockType = redisLock.type();
		long waitTime = redisLock.waitTime();
		long leaseTime = redisLock.leaseTime();
		TimeUnit timeUnit = redisLock.timeUnit();
		return redisLockClient.lock(lockKey, lockType, waitTime, leaseTime, timeUnit, point::proceed);
	}

	/**
	 * 计算参数表达式
	 *
	 * @param point     ProceedingJoinPoint
	 * @param lockParam lockParam
	 * @return 结果
	 */
	private String evalLockParam(ProceedingJoinPoint point, String lockParam) {
		MethodSignature ms = (MethodSignature) point.getSignature();
		Method method = ms.getMethod();
		Object[] args = point.getArgs();
		Object target = point.getTarget();
		Class<?> targetClass = target.getClass();
		EvaluationContext context = EVALUATOR.createContext(method, args, target, targetClass, applicationContext);
		AnnotatedElementKey elementKey = new AnnotatedElementKey(method, targetClass);
		return EVALUATOR.evalAsText(lockParam, elementKey, context);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
