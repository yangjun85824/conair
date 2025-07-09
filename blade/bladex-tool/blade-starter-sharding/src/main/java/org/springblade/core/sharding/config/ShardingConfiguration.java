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
package org.springblade.core.sharding.config;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.aop.DynamicDataSourceAnnotationAdvisor;
import com.baomidou.dynamic.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.event.DataSourceInitEvent;
import com.baomidou.dynamic.datasource.event.EncDataSourceInitEvent;
import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.processor.DsSpelExpressionProcessor;
import com.baomidou.dynamic.datasource.processor.DsJakartaHeaderProcessor;
import com.baomidou.dynamic.datasource.processor.DsJakartaSessionProcessor;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceCreatorAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springblade.core.sharding.ShardingDataSourceProvider;
import org.springblade.core.sharding.constant.ShardingConstant;
import org.springblade.core.sharding.props.ShardingProperties;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.util.List;

/**
 * ShardingSphere与DynamicDatasource配置类
 * 此配置类用于未开启租户模块数据库隔离功能的场景
 *
 * @author Chill
 */
@Configuration
@EnableConfigurationProperties({DynamicDataSourceProperties.class, ShardingProperties.class})
@AutoConfiguration(before = {DruidDataSourceAutoConfigure.class, DynamicDataSourceAutoConfiguration.class})
@Import(value = {DynamicDataSourceCreatorAutoConfiguration.class})
@ConditionalOnProperty(value = ShardingProperties.PREFIX + ".enabled", havingValue = "true")
public class ShardingConfiguration {

	@Lazy
	@Resource(name = "shardingSphereDataSource")
	private DataSource shardingSphereDataSource;

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(value = ShardingConstant.TENANT_DYNAMIC_DATASOURCE_PROP, havingValue = "false")
	public DataSourceInitEvent dataSourceInitEvent() {
		return new EncDataSourceInitEvent();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(value = ShardingConstant.TENANT_DYNAMIC_DATASOURCE_PROP, havingValue = "false")
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
	@ConditionalOnMissingBean
	@ConditionalOnProperty(value = ShardingConstant.TENANT_DYNAMIC_DATASOURCE_PROP, havingValue = "false")
	public DynamicDataSourceAnnotationInterceptor tenantDataSourceAnnotationInterceptor(DsProcessor dsProcessor, DynamicDataSourceProperties dynamicDataSourceProperties) {
		return new DynamicDataSourceAnnotationInterceptor(dynamicDataSourceProperties.getAop().getAllowedPublicOnly(), dsProcessor);
	}

	@Bean
	@ConditionalOnMissingBean
	@Role(value = BeanDefinition.ROLE_INFRASTRUCTURE)
	@ConditionalOnProperty(value = ShardingConstant.TENANT_DYNAMIC_DATASOURCE_PROP, havingValue = "false")
	public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor(DynamicDataSourceAnnotationInterceptor dynamicDataSourceAnnotationInterceptor, DynamicDataSourceProperties dynamicDataSourceProperties) {
		DynamicDataSourceAnnotationAdvisor advisor = new DynamicDataSourceAnnotationAdvisor(dynamicDataSourceAnnotationInterceptor, DS.class);
		advisor.setOrder(dynamicDataSourceProperties.getAop().getOrder());
		return advisor;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(value = ShardingConstant.TENANT_DYNAMIC_DATASOURCE_PROP, havingValue = "false")
	public DsProcessor dsProcessor() {
		DsProcessor headerProcessor = new DsJakartaHeaderProcessor();
		DsProcessor sessionProcessor = new DsJakartaSessionProcessor();
		DsSpelExpressionProcessor spelExpressionProcessor = new DsSpelExpressionProcessor();
		headerProcessor.setNextProcessor(sessionProcessor);
		sessionProcessor.setNextProcessor(spelExpressionProcessor);
		return headerProcessor;
	}

	/**
	 * 自定义分库分表动态数据源加载逻辑
	 */
	@Bean
	@Primary
	@ConditionalOnProperty(value = ShardingConstant.TENANT_DYNAMIC_DATASOURCE_PROP, havingValue = "false")
	public DynamicDataSourceProvider dynamicDataSourceProvider(DefaultDataSourceCreator dataSourceCreator, DataSourceProperties dataSourceProperties, DynamicDataSourceProperties dynamicDataSourceProperties) {
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
		return new ShardingDataSourceProvider(dataSourceCreator, dynamicDataSourceProperties, driverClassName, url, username, password, shardingSphereDataSource);
	}

	/**
	 * 配置分库分表动态数据源
	 */
	@Bean
	@Primary
	@ConditionalOnProperty(value = ShardingConstant.TENANT_DYNAMIC_DATASOURCE_PROP, havingValue = "false")
	public DataSource dataSource(List<DynamicDataSourceProvider> providers, DynamicDataSourceProperties dynamicDataSourceProperties) {
		DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource(providers);
		dataSource.setPrimary(dynamicDataSourceProperties.getPrimary());
		dataSource.setStrict(dynamicDataSourceProperties.getStrict());
		dataSource.setStrategy(dynamicDataSourceProperties.getStrategy());
		dataSource.setP6spy(dynamicDataSourceProperties.getP6spy());
		dataSource.setSeata(dynamicDataSourceProperties.getSeata());
		return dataSource;
	}

	@Order
	@AutoConfiguration
	@AllArgsConstructor
	@ConditionalOnProperty(value = ShardingConstant.TENANT_DYNAMIC_DATASOURCE_PROP, havingValue = "true")
	public static class ShardingSphereDataSourceConfiguration implements SmartInitializingSingleton {
		@Lazy
		@Resource(name = "shardingSphereDataSource")
		private final DataSource shardingSphereDataSource;

		private final DataSource dataSource;

		@Override
		public void afterSingletonsInstantiated() {
			if (shardingSphereDataSource != null) {
				// 获取储存的数据源集合
				DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
				// 设置ShardingSphere数据源
				ds.addDataSource(ShardingConstant.SHARDING_DATASOURCE_KEY, shardingSphereDataSource);
			}

		}
	}


}
