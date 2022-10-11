package com.yyld.conair.druid.utils;

import com.yyld.conair.druid.druid.AbsDataSources;
import com.yyld.conair.druid.druid.ext.DefaultDataSources;
import com.yyld.conair.druid.entity.DataSourceInfoEntity;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 功能描述
 */
@Slf4j
public final class DataSourceUtil {

    public static Map<Object, Object> dataSourceMap = new HashMap();

    public static List<DataSourceInfoEntity> entityList = new ArrayList<>();

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

        if (dataSource == null) {

            for (Object key : dataSourceMap.keySet()) {

                return (DataSource) dataSourceMap.get(key.toString());

            }

        }

        return dataSource;
    }

}
