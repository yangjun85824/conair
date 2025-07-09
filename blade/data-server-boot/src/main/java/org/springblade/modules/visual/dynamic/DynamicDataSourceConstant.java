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
package org.springblade.modules.visual.dynamic;

/**
 * 数据源常量.
 *
 * @author Chill
 */
public interface DynamicDataSourceConstant {

	/**
	 * 数据源缓存名
	 */
	String DYNAMIC_DATASOURCE_CACHE = "blade:visual";

	/**
	 * 数据源缓存键
	 */
	String DYNAMIC_DATASOURCE_KEY = "datasource:id:";

	/**
	 * 数据源查询基础
	 */
	String DYNAMIC_DATASOURCE_BASE_STATEMENT = "SELECT id, driver_class as driverClass, url, username, password FROM blade_visual_db";

	/**
	 * 数据源查询SQL
	 */
	String DYNAMIC_DATASOURCE_SINGLE_STATEMENT = DYNAMIC_DATASOURCE_BASE_STATEMENT + " WHERE is_deleted = 0 AND id = ?";

	/**
	 * 数据源查询SQL
	 */
	String DYNAMIC_DATASOURCE_GROUP_STATEMENT = DYNAMIC_DATASOURCE_BASE_STATEMENT + " WHERE is_deleted = 0";

	/**
	 * 数据源错误提示
	 */
	String DYNAMIC_DATASOURCE_NOT_FOUND = "数据源信息有误，数据加载失败";

	/**
	 * oracle驱动类
	 */
	String ORACLE_DRIVER_CLASS = "oracle.jdbc.OracleDriver";

	/**
	 * oracle校验
	 */
	String ORACLE_VALIDATE_STATEMENT = "select 1 from dual";

	/**
	 * 通用校验
	 */
	String COMMON_VALIDATE_STATEMENT = "select 1";

}
