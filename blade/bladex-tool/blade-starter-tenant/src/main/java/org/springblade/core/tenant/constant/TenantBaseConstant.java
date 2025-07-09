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
package org.springblade.core.tenant.constant;

/**
 * 租户常量.
 *
 * @author Chill
 */
public interface TenantBaseConstant {

	/**
	 * 租户数据源缓存名
	 */
	String TENANT_DATASOURCE_CACHE = "blade:datasource";

	/**
	 * 租户数据源缓存键
	 */
	String TENANT_DATASOURCE_KEY = "tenant:id:";

	/**
	 * 租户数据源缓存键
	 */
	String TENANT_DATASOURCE_EXIST_KEY = "tenant:exist:";

	/**
	 * 租户动态数据源键
	 */
	String TENANT_DYNAMIC_DATASOURCE_PROP = "blade.tenant.dynamic-datasource";

	/**
	 * 租户全局动态数据源切面键
	 */
	String TENANT_DYNAMIC_GLOBAL_PROP = "blade.tenant.dynamic-global";

	/**
	 * 租户是否存在数据源
	 */
	String TENANT_DATASOURCE_EXIST_STATEMENT = "select datasource_id from blade_tenant WHERE is_deleted = 0 AND tenant_id = ?";

	/**
	 * 租户数据源基础SQL
	 */
	String TENANT_DATASOURCE_BASE_STATEMENT = "SELECT category, tenant_id as tenantId, driver_class as driverClass, url, username, password, sharding_config as shardingConfig from blade_tenant tenant LEFT JOIN blade_datasource datasource ON tenant.datasource_id = datasource.id ";

	/**
	 * 租户单数据源SQL
	 */
	String TENANT_DATASOURCE_SINGLE_STATEMENT = TENANT_DATASOURCE_BASE_STATEMENT + "WHERE tenant.is_deleted = 0 AND tenant.tenant_id = ?";

	/**
	 * 租户集动态数据源SQL
	 */
	String TENANT_DATASOURCE_GROUP_STATEMENT = TENANT_DATASOURCE_BASE_STATEMENT + "WHERE tenant.is_deleted = 0";

	/**
	 * 租户未找到返回信息
	 */
	String TENANT_DATASOURCE_NOT_FOUND = "未找到租户信息,数据源加载失败!";

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

	/**
	 * jdbc数据源分类
	 */
	int JDBC_CATEGORY = 1;

	/**
	 * sharding数据源分类
	 */
	int SHARDING_CATEGORY = 2;

}
