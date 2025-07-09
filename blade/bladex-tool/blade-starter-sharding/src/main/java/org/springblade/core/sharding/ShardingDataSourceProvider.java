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
package org.springblade.core.sharding;

import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.sharding.constant.ShardingConstant;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 分库分表数据源初始加载
 *
 * @author Chill
 */
@Slf4j
public class ShardingDataSourceProvider extends AbstractDataSourceProvider {

	private final String driverClassName;
	private final String url;
	private final String username;
	private final String password;
	private final DynamicDataSourceProperties dynamicDataSourceProperties;
	private final DataSource shardingSphereDataSource;

	public ShardingDataSourceProvider(DefaultDataSourceCreator dataSourceCreator, DynamicDataSourceProperties dynamicDataSourceProperties, String driverClassName, String url, String username, String password, DataSource shardingSphereDataSource) {
		super(dataSourceCreator);
		this.dynamicDataSourceProperties = dynamicDataSourceProperties;
		this.driverClassName = driverClassName;
		this.url = url;
		this.username = username;
		this.password = password;
		this.shardingSphereDataSource = shardingSphereDataSource;
	}

	@Override
	public Map<String, DataSource> loadDataSources() {
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
		// 构建分库分表数据源
		Map<String, DataSource> dataSourceMap = createDataSourceMap(map);
		dataSourceMap.put(ShardingConstant.SHARDING_DATASOURCE_KEY, shardingSphereDataSource);
		return dataSourceMap;
	}


}
