package com.yyld.conair.ds.utils;

import com.yyld.conair.commons.data.utils.SpringContextUtil;
import com.yyld.conair.ds.mds.druid.AbsDataSources;
import com.yyld.conair.ds.mds.druid.ext.DefaultDataSources;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 功能描述
 */
@Slf4j
public final class DataSourceUtil {

    public static Map<Object, Object> dataSourceMap = new HashMap<>();

    public static String defaultDs;

    public static void addDataSource(String key, DataSource dataSource) {
        dataSourceMap.put(key, dataSource);
    }

    public static void initOthersDataSource() {

        //从文件添加数据源
        AbsDataSources dataSources = new DefaultDataSources();

        dataSourceMap = dataSources.dataSourceMap();
        defaultDs = dataSources.getDefaultDsName();

    }

    public static DataSource getDefaultDs() {

        DataSource dataSource = (DataSource) dataSourceMap.get(defaultDs);

        if (dataSource == null){

            for (Object key : dataSourceMap.keySet()){

                return (DataSource) dataSourceMap.get(key.toString());

            }

        }

        return dataSource;
    }
}
