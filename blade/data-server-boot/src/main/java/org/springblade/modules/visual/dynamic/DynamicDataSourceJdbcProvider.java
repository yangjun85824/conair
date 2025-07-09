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

import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.druid.DruidConfig;
import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import org.springblade.core.tool.utils.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static org.springblade.modules.visual.dynamic.DynamicDataSourceConstant.*;

/**
 * 动态数据源初始加载
 *
 * @author Chill
 */
public class DynamicDataSourceJdbcProvider extends AbstractJdbcDataSourceProvider {

	private final String driverClassName;
	private final String url;
	private final String username;
	private final String password;
	private final DynamicDataSourceProperties dynamicDataSourceProperties;
	private final DynamicDataSourceHelper dynamicDataSourceHelper;

	public DynamicDataSourceJdbcProvider(DefaultDataSourceCreator dataSourceCreator, DynamicDataSourceHelper dynamicDataSourceHelper, DynamicDataSourceProperties dynamicDataSourceProperties, String driverClassName, String url, String username, String password) {
		super(dataSourceCreator, driverClassName, url, username, password);
		this.dynamicDataSourceHelper = dynamicDataSourceHelper;
		this.dynamicDataSourceProperties = dynamicDataSourceProperties;
		this.driverClassName = driverClassName;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {
		// 构建数据源集合
		Map<String, DataSourceProperty> map = new HashMap<>(16);
		// 构建主数据源
		DataSourceProperty masterProperty = new DataSourceProperty();
		masterProperty.setDriverClassName(driverClassName);
		masterProperty.setUrl(url);
		masterProperty.setUsername(username);
		masterProperty.setPassword(password);
		map.put(dynamicDataSourceProperties.getPrimary(), masterProperty);
		// 构建yml数据源
		Map<String, DataSourceProperty> datasource = dynamicDataSourceProperties.getDatasource();
		if (!datasource.isEmpty()) {
			datasource.remove(dynamicDataSourceProperties.getPrimary());
			map.putAll(datasource);
		}
		// 构建动态数据源
		ResultSet rs = statement.executeQuery(DYNAMIC_DATASOURCE_GROUP_STATEMENT);
		while (rs.next()) {
			String id = rs.getString("id");
			String driver = rs.getString("driverClass");
			String url = rs.getString("url");
			String username = rs.getString("username");
			String password = rs.getString("password");
			if (StringUtil.isNoneBlank(id, driver, url, username, password)) {
				// 测试链接是否生效
				Boolean result = dynamicDataSourceHelper.dbTest(driver, url, username, password);
				if (result) {
					DataSourceProperty jdbcProperty = new DataSourceProperty();
					// 设置SQL校验
					DruidConfig druid = jdbcProperty.getDruid();
					if (StringUtil.equals(driver, ORACLE_DRIVER_CLASS)) {
						druid.setValidationQuery(ORACLE_VALIDATE_STATEMENT);
					} else {
						druid.setValidationQuery(COMMON_VALIDATE_STATEMENT);
					}
					// 设置SQL链接
					jdbcProperty.setDriverClassName(driver);
					jdbcProperty.setUrl(url);
					jdbcProperty.setUsername(username);
					jdbcProperty.setPassword(password);
					map.put(id, jdbcProperty);
				}
			}
		}
		return map;
	}
}
