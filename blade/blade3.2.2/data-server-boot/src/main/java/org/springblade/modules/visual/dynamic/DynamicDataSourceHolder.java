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

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.druid.DruidConfig;
import lombok.Setter;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.utils.CacheUtil;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Set;

import static org.springblade.modules.visual.dynamic.DynamicDataSourceConstant.*;

/**
 * 动态数据源核心处理类
 *
 * @author Chill
 */
@Setter
public class DynamicDataSourceHolder {

	private DataSource dataSource;
	private DataSourceCreator dataSourceCreator;
	private JdbcTemplate jdbcTemplate;


	/**
	 * 数据源缓存处理
	 *
	 * @param id 数据源ID
	 */
	public void handleDataSource(String id) {
		// 获取储存的数据源集合
		DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
		Set<String> keys = ds.getDataSources().keySet();
		// 配置不存在则动态添加数据源，以懒加载的模式解决分布式场景的配置同步
		// 为了保证数据完整性，配置后生成数据源缓存，后台便无法修改更换数据源，若一定要修改请迁移数据后重启服务或自行修改底层逻辑
		if (!keys.contains(id)) {
			DynamicDataSource dynamicDataSource = getDataSource(id);
			if (dynamicDataSource != null) {
				// 创建数据源配置
				DataSourceProperty dataSourceProperty = new DataSourceProperty();
				// 拷贝数据源配置
				BeanUtils.copyProperties(dynamicDataSource, dataSourceProperty);
				// 设置驱动类
				dataSourceProperty.setDriverClassName(dynamicDataSource.getDriverClass());
				// 关闭懒加载
				dataSourceProperty.setLazy(Boolean.FALSE);
				// 设置SQL校验
				DruidConfig druid = dataSourceProperty.getDruid();
				if (StringUtil.equals(dataSourceProperty.getDriverClassName(), ORACLE_DRIVER_CLASS)) {
					druid.setValidationQuery(ORACLE_VALIDATE_STATEMENT);
				} else {
					druid.setValidationQuery(COMMON_VALIDATE_STATEMENT);
				}
				// 创建动态数据源
				DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
				// 添加最新数据源
				ds.addDataSource(id, dataSource);
			} else {
				throw new ServiceException(DYNAMIC_DATASOURCE_NOT_FOUND);
			}
		}
	}

	/**
	 * 获取对应的数据源配置
	 *
	 * @param id 数据源ID
	 */
	private DynamicDataSource getDataSource(String id) {
		// 获取数据源信息
		try {
			DynamicDataSource dynamicDataSource = CacheUtil.get(DYNAMIC_DATASOURCE_CACHE, DYNAMIC_DATASOURCE_KEY, id, DynamicDataSource.class);
			if (dynamicDataSource == null) {
				dynamicDataSource = jdbcTemplate.queryForObject(DYNAMIC_DATASOURCE_SINGLE_STATEMENT, new String[]{id}, new BeanPropertyRowMapper<>(DynamicDataSource.class));
				if (dynamicDataSource != null && StringUtil.isNoneBlank(dynamicDataSource.getId(), dynamicDataSource.getDriverClass(), dynamicDataSource.getUrl(), dynamicDataSource.getUsername(), dynamicDataSource.getPassword())) {
					CacheUtil.put(DYNAMIC_DATASOURCE_CACHE, DYNAMIC_DATASOURCE_KEY, id, dynamicDataSource);
				}
			}
			return dynamicDataSource;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
