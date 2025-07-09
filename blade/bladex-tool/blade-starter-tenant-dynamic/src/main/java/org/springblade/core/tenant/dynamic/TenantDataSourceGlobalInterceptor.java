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

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tenant.exception.TenantDataSourceException;
import org.springblade.core.tool.utils.StringUtil;

/**
 * 租户数据源全局拦截器
 *
 * @author Chill
 */
public class TenantDataSourceGlobalInterceptor implements MethodInterceptor {

	@Setter
	private TenantDataSourceHolder holder;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String tenantId = AuthUtil.getTenantId();
		try {
			if (StringUtil.isNotBlank(tenantId)) {
				holder.handleDataSource(tenantId);
				DynamicDataSourceContextHolder.push(tenantId);
			}
			return invocation.proceed();
		} catch (Exception exception) {
			throw new TenantDataSourceException(exception.getMessage());
		} finally {
			if (StringUtil.isNotBlank(tenantId)) {
				DynamicDataSourceContextHolder.poll();
			}
		}
	}
}
