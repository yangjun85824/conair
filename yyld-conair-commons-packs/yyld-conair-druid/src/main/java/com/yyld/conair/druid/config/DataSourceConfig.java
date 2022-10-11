package com.yyld.conair.druid.config;

import com.yyld.conair.druid.utils.DataSourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @Description: 功能描述
 */
@Slf4j
@Configuration
@EnableTransactionManagement(order = 2)//由于引入多数据源，所以让spring声明式事务的aop要在多数据源切换的aop的后面
public class DataSourceConfig extends AbstractRoutingDataSource {

    public DataSourceConfig() {

        DataSourceUtil.initOthersDataSource();

        super.setTargetDataSources(DataSourceUtil.dataSourceMap);
        //设置默认的数据源，当拿不到数据源时，使用此配置
//        super.setDefaultTargetDataSource(DataSourceUtil.getDefaultDs());
    }

    //数据源路由，此方用于产生要选取的数据源逻辑名称
    @Override
    protected Object determineCurrentLookupKey() {

        // 通过绑定线程的数据源上下文实现多数据源的动态切换,有兴趣的可以去查阅资料或源码
        String datasource = DataSourceContext.getDataSource();
        /*if (StringUtils.isBlank(datasource)) {
            return DataSourceUtil.getDefaultDs();
        }*/
        logger.info("datasource=" + datasource);

        return datasource;
    }

    public void setTargetDataSources(String dbName, DataSource dataSource){

        DataSourceUtil.addDataSource(dbName,dataSource);
        //DataSourceUtil.initOthersDataSource();
        super.setTargetDataSources(DataSourceUtil.dataSourceMap);
        super.afterPropertiesSet();
    }

    /*@Bean(name = "defaultDs")
    @Primary
    public DataSource defaultDataSource() {
        log.info("创建masterDataSource");

        return DataSourceUtil.getDefaultDs();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(defaultDataSource());
    }*/

}
