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
package org.springblade.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 租户常量
 *
 * @author Chill
 */
public interface TenantConstant {

	/**
	 * 租户默认密码KEY
	 */
	String PASSWORD_KEY = "tenant.default.password";

	/**
	 * 租户默认账号额度KEY
	 */
	String ACCOUNT_NUMBER_KEY = "tenant.default.accountNumber";

	/**
	 * 租户默认菜单集合KEY
	 */
	String ACCOUNT_MENU_CODE_KEY = "tenant.default.menuCode";

	/**
	 * 租户默认密码
	 */
	String DEFAULT_PASSWORD = "123456";

	/**
	 * 租户授权码默认16位密钥
	 */
	String DES_KEY = "0000000000000000";

	/**
	 * 租户默认账号额度
	 */
	Integer DEFAULT_ACCOUNT_NUMBER = -1;

	/**
	 * 租户默认菜单集合
	 */
	List<String> MENU_CODES = Arrays.asList(
		"desk", "flow", "work", "monitor", "resource", "role", "user", "dept", "dictbiz", "topmenu"
	);

}
