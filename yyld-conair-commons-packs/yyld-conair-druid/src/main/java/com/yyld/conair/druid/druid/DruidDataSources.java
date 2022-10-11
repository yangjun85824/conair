package com.yyld.conair.druid.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.yyld.conair.commons.data.utils.SpringContextUtil;
import com.yyld.conair.druid.config.DataSourceConfig;
import com.yyld.conair.druid.entity.DataSourceInfoEntity;
import com.yyld.conair.druid.utils.DataSourceUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @ClassName DruidDataSources
 * @Description 创建数据源接口
 * @Author yangj
 * @Date 2022/6/24 15:50
 * @Vresion 1.0
 **/
public final class DruidDataSources {

    public DataSource createdDataSource(DataSourceInfoEntity entity) {

        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(entity.getUrl());
        datasource.setUsername(entity.getUsername());
        datasource.setPassword(entity.getPassword());   //这里可以做加密处理
        datasource.setDriverClassName(entity.getDriver());

        if (entity.getInitialSize() != null) {
            datasource.setInitialSize(entity.getInitialSize());
        }
        if (entity.getMinIdle() != null) {
            datasource.setMinIdle(entity.getMinIdle());
        }
        if (entity.getMaxActive() != null) {
            datasource.setMaxActive(entity.getMaxActive());
        }
        if (entity.getMaxWait() != null) {
            datasource.setMaxWait(entity.getMaxWait());
        }
        if (entity.getTimeBetweenEvictionRunsMillis() != null) {
            datasource.setTimeBetweenEvictionRunsMillis(entity.getTimeBetweenEvictionRunsMillis());
        }
        if (entity.getMinEvictableIdleTimeMillis() != null) {
            datasource.setMinEvictableIdleTimeMillis(entity.getMinEvictableIdleTimeMillis());
        }
        datasource.setValidationQuery(entity.getValidationQuery());

        if (entity.getTestWhileIdle() != null && entity.getTestWhileIdle().booleanValue()) {
            datasource.setTestWhileIdle(true);
        }
        if (entity.getTestWhileIdle() == null) {
            datasource.setTestWhileIdle(false);
        }
        if (entity.getTestOnBorrow() != null && entity.getTestOnBorrow().booleanValue()) {
            datasource.setTestOnBorrow(true);
        }
        if (entity.getTestOnBorrow() == null) {
            datasource.setTestOnBorrow(false);
        }
        if (entity.getTestOnReturn() != null && entity.getTestOnReturn().booleanValue()) {
            datasource.setTestOnReturn(true);
        }
        if (entity.getTestOnReturn() == null) {
            datasource.setTestOnReturn(false);
        }
        if (entity.getPoolPreparedStatements() != null && entity.getPoolPreparedStatements().booleanValue()) {
            datasource.setPoolPreparedStatements(true);
        }
        if (entity.getPoolPreparedStatements() == null) {
            datasource.setPoolPreparedStatements(false);
        }

        if (entity.getMaxPoolPreparedStatementPerConnectionSize() != null) {
            datasource.setMaxPoolPreparedStatementPerConnectionSize(entity.getMaxPoolPreparedStatementPerConnectionSize());
        }
        try {
            datasource.setFilters(entity.getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        datasource.setConnectionProperties(entity.getConnectionProperties());

        DataSourceUtil.entityList.add(entity);
        DataSourceUtil.dataSourceMap.put(entity.getDsName(),datasource);

        //重新初始化数据源
        SpringContextUtil.getBean(DataSourceConfig.class).setTargetDataSources(entity.getDsName(),datasource);
        //SpringContextUtil.getBean(DataSourceConfig.class).setTargetDataSources(DataSourceUtil.dataSourceMap);

        return datasource;

    }
}
