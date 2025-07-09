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

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import org.aopalliance.intercept.MethodInvocation;
import org.springblade.core.secure.utils.AuthUtil;

/**
 * 租户动态数据源解析器
 *
 * @author Chill
 */
public class DsTenantIdProcessor extends DsProcessor {

	public static final String TENANT_ID_KEY = "#token.tenantId";

	@Override
	public boolean matches(String key) {
		return key.equals(TENANT_ID_KEY);
	}

	@Override
	public String doDetermineDatasource(MethodInvocation invocation, String key) {
		return AuthUtil.getTenantId();
	}

}
