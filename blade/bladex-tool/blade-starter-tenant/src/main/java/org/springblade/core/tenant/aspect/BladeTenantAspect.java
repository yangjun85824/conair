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

package org.springblade.core.tenant.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springblade.core.tenant.BladeTenantHolder;
import org.springblade.core.tenant.annotation.TenantIgnore;

/**
 * 自定义租户切面
 *
 * @author Chill
 */
@Slf4j
@Aspect
public class BladeTenantAspect {

	@Around("@annotation(tenantIgnore)")
	public Object around(ProceedingJoinPoint point, TenantIgnore tenantIgnore) throws Throwable {
		try {
			//开启忽略
			BladeTenantHolder.setIgnore(Boolean.TRUE);
			//执行方法
			return point.proceed();
		} finally {
			//关闭忽略
			BladeTenantHolder.clear();
		}
	}

}
