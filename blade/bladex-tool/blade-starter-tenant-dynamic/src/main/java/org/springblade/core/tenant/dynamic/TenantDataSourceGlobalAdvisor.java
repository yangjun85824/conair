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
 * Author: Chill Zhuang (bladejava@qq.com)
 */
package org.springblade.core.tenant.dynamic;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.lang.NonNull;

import static org.springblade.core.launch.constant.AppConstant.BASE_PACKAGES;

/**
 * 租户数据源全局处理器
 *
 * @author Chill
 */
public class TenantDataSourceGlobalAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

	private final Advice advice;

	private final Pointcut pointcut;

	public TenantDataSourceGlobalAdvisor(@NonNull TenantDataSourceGlobalInterceptor tenantDataSourceGlobalInterceptor) {
		this.advice = tenantDataSourceGlobalInterceptor;
		this.pointcut = buildPointcut();
	}

	@NonNull
	@Override
	public Pointcut getPointcut() {
		return this.pointcut;
	}

	@NonNull
	@Override
	public Advice getAdvice() {
		return this.advice;
	}

	@Override
	public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
		if (this.advice instanceof BeanFactoryAware) {
			((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
		}
	}

	private Pointcut buildPointcut() {
		AspectJExpressionPointcut cut = new AspectJExpressionPointcut();
		cut.setExpression(
			"(@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)) && " +
				"(!@annotation(" + BASE_PACKAGES + ".core.tenant.annotation.NonDS) && !@within(" + BASE_PACKAGES + ".core.tenant.annotation.NonDS))"
		);
		return new ComposablePointcut((Pointcut) cut);
	}

}
