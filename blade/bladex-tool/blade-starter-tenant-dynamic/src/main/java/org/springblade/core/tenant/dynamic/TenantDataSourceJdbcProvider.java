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

import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.dynamic.datasource.toolkit.DsStrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tenant.utils.ShardingUtil;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.StringUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.springblade.core.tenant.constant.TenantBaseConstant.*;

/**
 * 租户数据源初始加载
 *
 * @author Chill
 */
@Slf4j
public class TenantDataSourceJdbcProvider extends AbstractJdbcDataSourceProvider {

	private final String driverClassName;
	private final String url;
	private final String username;
	private final String password;
	private final DefaultDataSourceCreator dataSourceCreator;
	private final DynamicDataSourceProperties dynamicDataSourceProperties;

	public TenantDataSourceJdbcProvider(DefaultDataSourceCreator dataSourceCreator, DynamicDataSourceProperties dynamicDataSourceProperties, String driverClassName, String url, String username, String password) {
		super(dataSourceCreator, driverClassName, url, username, password);
		this.dataSourceCreator = dataSourceCreator;
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
		masterProperty.setDruid(dynamicDataSourceProperties.getDruid());
		map.put(dynamicDataSourceProperties.getPrimary(), masterProperty);
		// 构建yml数据源
		Map<String, DataSourceProperty> datasource = dynamicDataSourceProperties.getDatasource();
		if (!datasource.isEmpty()) {
			map.putAll(datasource);
		}
		// 构建动态数据源
		ResultSet rs = statement.executeQuery(TENANT_DATASOURCE_GROUP_STATEMENT);
		while (rs.next()) {
			int category = rs.getInt("category");
			String tenantId = rs.getString("tenantId");
			String driver = rs.getString("driverClass");
			String url = rs.getString("url");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String shardingConfig = rs.getString("shardingConfig");
			// JDBC直连配置
			if (category == JDBC_CATEGORY && StringUtil.isNoneBlank(tenantId, driver, url, username, password)) {
				DataSourceProperty jdbcProperty = new DataSourceProperty();
				jdbcProperty.setDriverClassName(driver);
				jdbcProperty.setUrl(url);
				jdbcProperty.setUsername(username);
				jdbcProperty.setPassword(password);
				jdbcProperty.setDruid(dynamicDataSourceProperties.getDruid());
				map.put(tenantId, jdbcProperty);
			}
			// Sharding分库分表配置
			else if (category == SHARDING_CATEGORY && StringUtil.isNotBlank(shardingConfig)) {
				DataSource dataSource = ShardingUtil.createDataSource(shardingConfig);
				TenantDataSourceProperty shardingProperty = new TenantDataSourceProperty();
				shardingProperty.setTenantId(tenantId);
				shardingProperty.setDataSource(dataSource);
				map.put(tenantId, shardingProperty);
			}
		}
		return map;
	}

	@Override
	public Map<String, DataSource> loadDataSources() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// 由于 SPI 的支持，现在已无需显示加载驱动了
			// 但在用户显示配置的情况下，进行主动加载
			if (!DsStrUtils.isEmpty(driverClassName)) {
				Class.forName(driverClassName);
				log.info("成功加载数据库驱动程序");
			}
			conn = DriverManager.getConnection(url, username, password);
			log.info("成功获取数据库连接");
			stmt = conn.createStatement();
			Map<String, DataSourceProperty> dataSourcePropertiesMap = executeStmt(stmt);
			return createDataSourceMap(dataSourcePropertiesMap);
		} catch (Exception e) {
			log.error("获取数据库连接失败", e);
		} finally {
			closeResource(conn);
			closeResource(stmt);
		}
		return null;
	}

	/**
	 * 关闭资源
	 *
	 * @param con 资源
	 */
	private static void closeResource(AutoCloseable con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				log.debug("关闭连接失败", ex);
			} catch (Throwable ex) {
				log.debug("关闭连接时遇到异常", ex);
			}
		}
	}

	@Override
	protected Map<String, DataSource> createDataSourceMap(Map<String, DataSourceProperty> dataSourcePropertiesMap) {
		Map<String, DataSource> dataSourceMap = new HashMap<>(dataSourcePropertiesMap.size() * 2);
		for (Map.Entry<String, DataSourceProperty> item : dataSourcePropertiesMap.entrySet()) {
			String dsName = item.getKey();
			DataSourceProperty dataSourceProperty = item.getValue();
			String poolName = dataSourceProperty.getPoolName();
			if (StringUtil.isBlank(poolName)) {
				poolName = dsName;
			}
			TenantDataSourceProperty tenantDataSourceProperty = BeanUtil.copyProperties(dataSourceProperty, TenantDataSourceProperty.class);
			DataSource dataSource = Objects.requireNonNull(tenantDataSourceProperty).getDataSource();
			if (dataSource == null) {
				dataSourceProperty.setPoolName(poolName);
				dataSourceMap.put(dsName, dataSourceCreator.createDataSource(dataSourceProperty));
			} else {
				dataSourceMap.put(dsName, dataSource);
			}
		}
		return dataSourceMap;
	}

}
