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
 * Author: DreamLu (596392912@qq.com)
 */

package org.springblade.core.metrics.druid;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceUnwrapper;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * DruidDataSourceMetadata Provide
 *
 * @author L.cm
 */
@AutoConfiguration(
	after = {MetricsAutoConfiguration.class, DataSourceAutoConfiguration.class, SimpleMetricsExportAutoConfiguration.class}
)
@ConditionalOnClass({DruidDataSource.class, MeterRegistry.class})
@ConditionalOnBean({DataSource.class, MeterRegistry.class})
public class DruidMetricsConfiguration {
	private static final String DATASOURCE_SUFFIX = "dataSource";

	@Bean
	public DataSourcePoolMetadataProvider druidDataSourceMetadataProvider() {
		return (dataSource) -> {
			DruidDataSource druidDataSource = DataSourceUnwrapper.unwrap(dataSource, DruidDataSource.class);
			if (druidDataSource != null) {
				return new DruidDataSourcePoolMetadata(druidDataSource);
			}
			return null;
		};
	}

	@Bean
	@ConditionalOnMissingBean
	public StatFilter statFilter() {
		return new StatFilter();
	}

	@Bean
	public DruidMetrics druidMetrics(ObjectProvider<Map<String, DataSource>> dataSourcesProvider) {
		Map<String, DataSource> dataSourceMap = dataSourcesProvider.getIfAvailable(HashMap::new);
		Map<String, DruidDataSource> druidDataSourceMap = new HashMap<>(2);
		dataSourceMap.forEach((name, dataSource) -> {
			// 保证连接池数据和 DataSourcePoolMetadataProvider 的一致
			DruidDataSource druidDataSource = DataSourceUnwrapper.unwrap(dataSource, DruidDataSource.class);
			if (druidDataSource != null) {
				druidDataSourceMap.put(getDataSourceName(name), druidDataSource);
			}
		});
		return druidDataSourceMap.isEmpty() ? null : new DruidMetrics(druidDataSourceMap);
	}

	/**
	 * Get the name of a DataSource based on its {@code beanName}.
	 *
	 * @param beanName the name of the data source bean
	 * @return a name for the given data source
	 */
	private static String getDataSourceName(String beanName) {
		if (beanName.length() > DATASOURCE_SUFFIX.length()
			&& StringUtils.endsWithIgnoreCase(beanName, DATASOURCE_SUFFIX)) {
			return beanName.substring(0, beanName.length() - DATASOURCE_SUFFIX.length());
		}
		return beanName;
	}

}
