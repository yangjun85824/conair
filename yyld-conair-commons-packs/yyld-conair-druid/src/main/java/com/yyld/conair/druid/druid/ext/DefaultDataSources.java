package com.yyld.conair.druid.druid.ext;

import com.alibaba.druid.pool.DruidDataSource;
import com.yyld.conair.druid.consts.DSConsts;
import com.yyld.conair.druid.druid.AbsDataSources;
import com.yyld.conair.druid.entity.DataSourceInfoEntity;
import com.yyld.conair.druid.utils.DataSourceUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @ClassName DefaultDruidDataSources
 * @Description 默认数据源接口
 * @Author yangj
 * @Date 2022/6/24 15:50
 * @Vresion 1.0
 **/
@Component
public final class DefaultDataSources extends AbsDataSources {

    @Override
    public DataSource createdDataSource(Properties properties, String dsName) {

        DruidDataSource datasource = new DruidDataSource();

        if (StringUtils.isNotBlank(dsName)) {
            dsName = dsName + ".";
        }

        DataSourceUtil.defaultDs = properties.getProperty(dsName + "defaultDs");

        datasource.setUrl(properties.getProperty(dsName + "url"));
        datasource.setUsername(properties.getProperty(dsName + "username"));
        datasource.setPassword(properties.getProperty(dsName + "password"));   //这里可以做加密处理
        datasource.setDriverClassName(properties.getProperty(dsName + "driver"));

        //configuration
        if (properties.getProperty(dsName + "initialSize") != null) {
            datasource.setInitialSize(Integer.parseInt(properties.getProperty(dsName + "initialSize")));
        }
        if (properties.getProperty(dsName + "minIdle") != null) {
            datasource.setMinIdle(Integer.parseInt(properties.getProperty(dsName + "minIdle")));
        }
        if (properties.getProperty(dsName + "maxActive") != null) {
            datasource.setMaxActive(Integer.parseInt(properties.getProperty(dsName + "maxActive")));
        }
        if (properties.getProperty(dsName + "maxWait") != null) {
            datasource.setMaxWait(Integer.parseInt(properties.getProperty(dsName + "maxWait")));
        }
        if (properties.getProperty(dsName + "timeBetweenEvictionRunsMillis") != null) {
            datasource.setTimeBetweenEvictionRunsMillis(Integer.parseInt(properties.getProperty(dsName + "timeBetweenEvictionRunsMillis")));
        }
        if (properties.getProperty(dsName + "setMinEvictableIdleTimeMillis") != null) {
            datasource.setMinEvictableIdleTimeMillis(Integer.parseInt(properties.getProperty(dsName + "setMinEvictableIdleTimeMillis")));
        }
        datasource.setValidationQuery(properties.getProperty(dsName + "validationQuery"));
        String testWhileIdle = properties.getProperty(dsName + "testWhileIdle");
        if ("true".equals(testWhileIdle)) {
            datasource.setTestWhileIdle(true);
        }
        if ("false".equals(testWhileIdle)) {
            datasource.setTestWhileIdle(false);
        }
        String testOnBorrow = properties.getProperty(dsName + "testOnBorrow");
        if ("true".equals(testOnBorrow)) {
            datasource.setTestOnBorrow(true);
        }
        if ("false".equals(testOnBorrow)) {
            datasource.setTestOnBorrow(false);
        }
        String testOnReturn = properties.getProperty(dsName + "testOnReturn");
        if ("true".equals(testOnReturn)) {
            datasource.setTestOnReturn(true);
        }
        if ("false".equals(testOnReturn)) {
            datasource.setTestOnReturn(false);
        }
        String poolPreparedStatements = properties.getProperty(dsName + "poolPreparedStatements");
        if ("true".equals(poolPreparedStatements)) {
            datasource.setPoolPreparedStatements(true);
        }
        if ("false".equals(poolPreparedStatements)) {
            datasource.setPoolPreparedStatements(false);
        }

        if (properties.getProperty(dsName + "maxPoolPreparedStatementPerConnectionSize") != null) {
            datasource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(properties.getProperty(dsName + "maxPoolPreparedStatementPerConnectionSize")));
        }
        try {
            datasource.setFilters(properties.getProperty(dsName + "filters"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        datasource.setConnectionProperties(properties.getProperty(dsName + "connectionProperties"));

        setDruidDataSources(datasource, properties, dsName);
        return datasource;

    }

    private void setDruidDataSources(DruidDataSource datasource, Properties properties, String dsName) {

        DataSourceInfoEntity entity = new DataSourceInfoEntity();

        if (".".equals(dsName.substring(dsName.length()-1))){
            dsName = dsName.substring(0,dsName.length()-1);
        }

        entity.setDsName(dsName);
        entity.setUrl(datasource.getUrl());
        entity.setUsername(datasource.getUsername());
        entity.setPassword(datasource.getPassword());
        entity.setDriver(datasource.getDriverClassName());

        entity.setType(DSConsts.DBTYPE_.get(datasource.getDriverClassName()));

        entity.setInitialSize(datasource.getInitialSize());
        entity.setMinIdle(datasource.getMinIdle());
        entity.setMaxActive(datasource.getMaxActive());
        entity.setMaxWait(datasource.getMaxWait());
        entity.setTimeBetweenEvictionRunsMillis(datasource.getTimeBetweenLogStatsMillis());
        entity.setMinEvictableIdleTimeMillis(datasource.getMinEvictableIdleTimeMillis());
        entity.setValidationQuery(datasource.getValidationQuery());

        datasource.setValidationQuery(properties.getProperty(dsName + "validationQuery"));
        String testWhileIdle = properties.getProperty(dsName + "testWhileIdle");
        if ("true".equals(testWhileIdle)) {
            entity.setTestWhileIdle(true);
        }
        if ("false".equals(testWhileIdle)) {
            entity.setTestWhileIdle(false);
        }
        String testOnBorrow = properties.getProperty(dsName + "testOnBorrow");
        if ("true".equals(testOnBorrow)) {
            entity.setTestOnBorrow(true);
        }
        if ("false".equals(testOnBorrow)) {
            entity.setTestOnBorrow(false);
        }
        String testOnReturn = properties.getProperty(dsName + "testOnReturn");
        if ("true".equals(testOnReturn)) {
            entity.setTestOnReturn(true);
        }
        if ("false".equals(testOnReturn)) {
            entity.setTestOnReturn(false);
        }
        String poolPreparedStatements = properties.getProperty(dsName + "poolPreparedStatements");
        if ("true".equals(poolPreparedStatements)) {
            entity.setPoolPreparedStatements(true);
        }
        if ("false".equals(poolPreparedStatements)) {
            entity.setPoolPreparedStatements(false);
        }

        if (properties.getProperty(dsName + "maxPoolPreparedStatementPerConnectionSize") != null) {
            entity.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(properties.getProperty(dsName + "maxPoolPreparedStatementPerConnectionSize")));
        }

        entity.setFilters(properties.getProperty(dsName + "filters"));

        entity.setConnectionProperties(properties.getProperty(dsName + "connectionProperties"));

        DataSourceUtil.entityList.add(entity);
    }
}
