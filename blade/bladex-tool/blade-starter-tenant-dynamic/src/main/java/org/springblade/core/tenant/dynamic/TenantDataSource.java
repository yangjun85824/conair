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

import lombok.Data;

/**
 * 租户数据源
 *
 * @author Chill
 */
@Data
public class TenantDataSource {

	/**
	 * 数据源类型
	 */
	private int category;
	/**
	 * 租户ID
	 */
	private String tenantId;
	/**
	 * 数据源ID
	 */
	private String datasourceId;
	/**
	 * 驱动类
	 */
	private String driverClass;
	/**
	 * 数据库链接
	 */
	private String url;
	/**
	 * 数据库账号名
	 */
	private String username;
	/**
	 * 数据库密码
	 */
	private String password;
	/**
	 * 分库分表配置
	 */
	private String shardingConfig;

}
