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
package org.springblade.auth.constant;

/**
 * AuthorizationConstant
 *
 * @author Chill
 */
public interface BladeAuthConstant {

	/**
	 * 是否开启注册参数key
	 */
	String REGISTER_USER_VALUE = "account.registerUser";
	/**
	 * 账号锁定错误次数参数key
	 */
	String FAIL_COUNT_VALUE = "account.failCount";
	/**
	 * 账号锁定默认错误次数
	 */
	Integer FAIL_COUNT = 5;
}
