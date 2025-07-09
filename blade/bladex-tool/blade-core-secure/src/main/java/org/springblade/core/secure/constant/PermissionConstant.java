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

import org.springblade.core.tool.utils.StringUtil;

/**
 * 权限校验常量
 *
 * @author Chill
 */
public interface PermissionConstant {

	/**
	 * 获取角色所有的权限编号
	 *
	 * @param size 数量
	 * @return string
	 */
	static String permissionAllStatement(int size) {
		return "select scope_path as path from blade_scope_api where id in (select scope_id from blade_role_scope where scope_category = 2 and role_id in (" + buildHolder(size) + "))";
	}

	/**
	 * 获取角色指定的权限编号
	 *
	 * @param size 数量
	 * @return string
	 */
	static String permissionStatement(int size) {
		return "select resource_code as code from blade_scope_api where resource_code = ? and id in (select scope_id from blade_role_scope where scope_category = 2 and role_id in (" + buildHolder(size) + "))";
	}

	/**
	 * 获取Sql占位符
	 *
	 * @param size 数量
	 * @return String
	 */
	static String buildHolder(int size) {
		StringBuilder builder = StringUtil.builder();
		for (int i = 0; i < size; i++) {
			builder.append("?,");
		}
		return StringUtil.removeSuffix(builder.toString(), ",");
	}

}
