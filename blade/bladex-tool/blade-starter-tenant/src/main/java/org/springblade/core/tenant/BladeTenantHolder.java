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
package org.springblade.core.tenant;

import org.springframework.core.NamedThreadLocal;

/**
 * 租户线程处理
 *
 * @author Chill
 */
public class BladeTenantHolder {

	private static final ThreadLocal<Boolean> TENANT_KEY_HOLDER = new NamedThreadLocal<Boolean>("blade-tenant") {
		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};

	public static void setIgnore(Boolean ignore) {
		TENANT_KEY_HOLDER.set(ignore);
	}

	public static Boolean isIgnore() {
		return TENANT_KEY_HOLDER.get();
	}


	public static void clear() {
		TENANT_KEY_HOLDER.remove();
	}


}
