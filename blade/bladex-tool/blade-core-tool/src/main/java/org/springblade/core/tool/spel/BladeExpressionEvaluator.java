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

package org.springblade.core.tool.spel;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存 spEl 提高性能
 *
 * @author L.cm
 */
public class BladeExpressionEvaluator extends CachedExpressionEvaluator {
	private final Map<ExpressionKey, Expression> expressionCache = new ConcurrentHashMap<>(64);
	private final Map<AnnotatedElementKey, Method> methodCache = new ConcurrentHashMap<>(64);

	/**
	 * Create an {@link EvaluationContext}.
	 *
	 * @param method      the method
	 * @param args        the method arguments
	 * @param target      the target object
	 * @param targetClass the target class
	 * @return the evaluation context
	 */
	public EvaluationContext createContext(Method method, Object[] args, Object target, Class<?> targetClass, @Nullable BeanFactory beanFactory) {
		Method targetMethod = getTargetMethod(targetClass, method);
		BladeExpressionRootObject rootObject = new BladeExpressionRootObject(method, args, target, targetClass, targetMethod);
		MethodBasedEvaluationContext evaluationContext = new MethodBasedEvaluationContext(rootObject, targetMethod, args, getParameterNameDiscoverer());
		if (beanFactory != null) {
			evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
		}
		return evaluationContext;
	}

	/**
	 * Create an {@link EvaluationContext}.
	 *
	 * @param method      the method
	 * @param args        the method arguments
	 * @param rootObject  rootObject
	 * @param targetClass the target class
	 * @return the evaluation context
	 */
	public EvaluationContext createContext(Method method, Object[] args, Class<?> targetClass, Object rootObject, @Nullable BeanFactory beanFactory) {
		Method targetMethod = getTargetMethod(targetClass, method);
		MethodBasedEvaluationContext evaluationContext = new MethodBasedEvaluationContext(rootObject, targetMethod, args, getParameterNameDiscoverer());
		if (beanFactory != null) {
			evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
		}
		return evaluationContext;
	}

	@Nullable
	public Object eval(String expression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
		return eval(expression, methodKey, evalContext, null);
	}

	@Nullable
	public <T> T eval(String expression, AnnotatedElementKey methodKey, EvaluationContext evalContext, @Nullable Class<T> valueType) {
		return getExpression(this.expressionCache, methodKey, expression).getValue(evalContext, valueType);
	}

	@Nullable
	public String evalAsText(String expression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
		return eval(expression, methodKey, evalContext, String.class);
	}

	public boolean evalAsBool(String expression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
		return Boolean.TRUE.equals(eval(expression, methodKey, evalContext, Boolean.class));
	}

	private Method getTargetMethod(Class<?> targetClass, Method method) {
		AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
		return methodCache.computeIfAbsent(methodKey, (key) -> AopUtils.getMostSpecificMethod(method, targetClass));
	}

	/**
	 * Clear all caches.
	 */
	public void clear() {
		this.expressionCache.clear();
		this.methodCache.clear();
	}
}
