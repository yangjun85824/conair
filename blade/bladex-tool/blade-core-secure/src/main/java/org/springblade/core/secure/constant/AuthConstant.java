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
package org.springblade.core.secure.constant;

/**
 * PreAuth权限表达式
 *
 * @author Chill
 */
public interface AuthConstant {

	/**
	 * 超管别名
	 */
	String ADMINISTRATOR = "administrator";

	/**
	 * 是有超管角色
	 */
	String HAS_ROLE_ADMINISTRATOR = "hasRole('" + ADMINISTRATOR + "')";

	/**
	 * 管理员别名
	 */
	String ADMIN = "admin";

	/**
	 * 是否有管理员角色
	 */
	String HAS_ROLE_ADMIN = "hasAnyRole('" + ADMINISTRATOR + "', '" + ADMIN + "')";

	/**
	 * 用户别名
	 */
	String USER = "user";

	/**
	 * 是否有用户角色
	 */
	String HAS_ROLE_USER = "hasRole('" + USER + "')";

	/**
	 * 测试别名
	 */
	String TEST = "test";

	/**
	 * 是否有测试角色
	 */
	String HAS_ROLE_TEST = "hasRole('" + TEST + "')";

	/**
	 * 放行所有请求
	 */
	String PERMIT_ALL = "permitAll()";

	/**
	 * 只有超管才能访问
	 */
	String DENY_ALL = "denyAll()";

	/**
	 * 对所有请求进行接口权限校验
	 */
	String PERMISSION_ALL = "permissionAll()";

	/**
	 * 是否对token加密传输
	 */
	String HAS_CRYPTO = "hasCrypto()";

}
