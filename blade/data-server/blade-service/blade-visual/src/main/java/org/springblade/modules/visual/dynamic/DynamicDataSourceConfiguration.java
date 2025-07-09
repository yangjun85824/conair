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


import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.druid.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.event.DataSourceInitEvent;
import com.baomidou.dynamic.datasource.event.EncDataSourceInitEvent;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceCreatorAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * 动态数据源配置类
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@AutoConfigureBefore({DruidDataSourceAutoConfigure.class, DynamicDataSourceAutoConfiguration.class})
@EnableConfigurationProperties({DataSourceProperties.class, DynamicDataSourceProperties.class})
@Import(value = {DynamicDataSourceCreatorAutoConfiguration.class})
public class DynamicDataSourceConfiguration {

	@Bean
	@Primary
	public DynamicDataSourceProvider dynamicDataSourceProvider(DefaultDataSourceCreator dataSourceCreator, DataSourceProperties dataSourceProperties, DynamicDataSourceProperties dynamicDataSourceProperties, DynamicDataSourceHelper dynamicDataSourceHelper) {
		String driverClassName = dataSourceProperties.getDriverClassName();
		String url = dataSourceProperties.getUrl();
		String username = dataSourceProperties.getUsername();
		String password = dataSourceProperties.getPassword();
		DataSourceProperty master = dynamicDataSourceProperties.getDatasource().get(dynamicDataSourceProperties.getPrimary());
		if (master != null) {
			driverClassName = master.getDriverClassName();
			url = master.getUrl();
			username = master.getUsername();
			password = master.getPassword();
		}
		return new DynamicDataSourceJdbcProvider(dataSourceCreator, dynamicDataSourceHelper, dynamicDataSourceProperties, driverClassName, url, username, password);
	}

	@Bean
	@Primary
	public DataSource dataSource(List<DynamicDataSourceProvider> providers, DynamicDataSourceProperties dynamicDataSourceProperties) {
		DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource(providers);
		dataSource.setPrimary(dynamicDataSourceProperties.getPrimary());
		dataSource.setStrict(dynamicDataSourceProperties.getStrict());
		dataSource.setStrategy(dynamicDataSourceProperties.getStrategy());
		dataSource.setP6spy(dynamicDataSourceProperties.getP6spy());
		dataSource.setSeata(dynamicDataSourceProperties.getSeata());
		return dataSource;
	}

	@Bean
	@ConditionalOnMissingBean
	public DataSourceInitEvent dataSourceInitEvent() {
		return new EncDataSourceInitEvent();
	}

	@Bean
	@ConditionalOnMissingBean
	public DefaultDataSourceCreator dataSourceCreator(List<DataSourceCreator> dataSourceCreators, DataSourceInitEvent dataSourceInitEvent, DynamicDataSourceProperties properties) {
		DefaultDataSourceCreator creator = new DefaultDataSourceCreator();
		creator.setCreators(dataSourceCreators);
		creator.setDataSourceInitEvent(dataSourceInitEvent);
		creator.setPublicKey(properties.getPublicKey());
		creator.setLazy(properties.getLazy());
		creator.setP6spy(properties.getP6spy());
		creator.setSeata(properties.getSeata());
		creator.setSeataMode(properties.getSeataMode());
		return creator;
	}

	@Bean
	public DynamicDataSourceHolder dataSourceHolder() {
		return new DynamicDataSourceHolder();
	}

	@Bean
	public DynamicDataSourceHelper dataSourceHelper() {
		return new DynamicDataSourceHelper();
	}

	@Order
	@AllArgsConstructor
	@Configuration(proxyBeanMethods = false)
	public static class DataSourceHolderConfiguration implements SmartInitializingSingleton {

		private final DataSource dataSource;
		private final DruidDataSourceCreator dataSourceCreator;
		private final JdbcTemplate jdbcTemplate;
		private final DynamicDataSourceHolder dynamicDataSourceHolder;

		@Override
		public void afterSingletonsInstantiated() {
			dynamicDataSourceHolder.setDataSource(dataSource);
			dynamicDataSourceHolder.setDataSourceCreator(dataSourceCreator);
			dynamicDataSourceHolder.setJdbcTemplate(jdbcTemplate);
		}

	}


}
