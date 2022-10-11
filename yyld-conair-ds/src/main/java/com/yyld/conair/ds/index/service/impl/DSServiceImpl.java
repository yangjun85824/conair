package com.yyld.conair.ds.index.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyld.conair.druid.config.DataSourceContext;
import com.yyld.conair.druid.druid.DruidDataSources;
import com.yyld.conair.druid.entity.DataSourceInfoEntity;
import com.yyld.conair.druid.utils.DataSourceUtil;
import com.yyld.conair.ds.index.constants.DSConstants;
import com.yyld.conair.ds.index.mapper.DSMapper;
import com.yyld.conair.ds.index.service.DSService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
@Service
public class DSServiceImpl extends ServiceImpl<DSMapper, Map<Object, Object>> implements DSService {

    @Resource
    private DSMapper dsMapper;

    @Override
    public List<Map<Object, Object>> allDsLibList(String dsType, String dbName) {

        List<Map<Object, Object>> dsList = new ArrayList<>();
        DataSourceContext.setDataSource(dbName);

        if (StringUtils.isNotBlank(dsType) && dsType.toUpperCase().equals("MYSQL")) {

            List<Map<Object, Object>> tempList = dsMapper.allDatabases(DSConstants.MYSQL_DS_DATABASES_);

            for (Map<Object, Object> map : tempList) {

                Map<Object, Object> tempMap = new HashMap<>();

                tempMap.put("dbType", dsType);
                tempMap.put("dbName", dbName);
                tempMap.put("dsName", map.get("Database"));

                dsList.add(tempMap);

            }

            return dsList;
        }
        if (StringUtils.isNotBlank(dsType) && dsType.toUpperCase().equals("ORACLE")) {

            List<Map<Object, Object>> tempList = dsMapper.allDatabases(DSConstants.ORACLE_DS_DATABASES_);

            for (Map<Object, Object> map : tempList) {

                Map<Object, Object> tempMap = new HashMap<>();

                tempMap.put("dbType", dsType);
                tempMap.put("dbName", dbName);
                tempMap.put("dsName", map.get("NAME"));

                dsList.add(tempMap);

            }

            return dsList;

        }
        return null;
    }

    @Override
    public List<Map<Object, Object>> alltables(String dsType, String dsName, String dbName) {

        List<Map<Object, Object>> tableList = new ArrayList<>();

        DataSourceContext.setDataSource(dbName);
        if (StringUtils.isNotBlank(dsType) && dsType.toUpperCase().equals("MYSQL")) {

            List<Map<Object, Object>> tempList = dsMapper.allDatabases(DSConstants.MYSQL_DS_ALLTABLE_ + " where table_schema='" + dsName + "' and table_type='base table';");

            for (Map<Object, Object> map : tempList) {

                Map<Object, Object> tempMap = new HashMap<>();
                tempMap.put("dbName", dbName);
                tempMap.put("tableSchema", dsName);
                tempMap.put("dbType", dsType);
                tempMap.put("dsName", dsName);
                tempMap.put("tableName", map.get("TABLE_NAME"));
                tempMap.put("tableRows", map.get("TABLE_ROWS"));

                tableList.add(tempMap);

            }

            return tableList;
        }
        if (StringUtils.isNotBlank(dsType) && dsType.toUpperCase().equals("ORACLE")) {

            List<Map<Object, Object>> tempList = dsMapper.allDatabases(DSConstants.ORACLE_DS_ALLTABLE_ + " where TABLESPACE_NAME='" + dsName + "'");

            System.out.println();
            for (Map<Object, Object> map : tempList) {

                Map<Object, Object> tempMap = new HashMap<>();
                tempMap.put("dbName", dbName);
                tempMap.put("tableSchema", map.get("OWNER"));
                tempMap.put("dbType", dsType);
                tempMap.put("dsName", map.get("TABLESPACE_NAME"));
                tempMap.put("tableName", map.get("TABLE_NAME"));
                tempMap.put("tableRows", map.get("NUM_ROWS"));

                tableList.add(tempMap);

            }

            return tableList;

        }
        return null;
    }

    @Override
    public List<Map<Object, Object>> alltableColumns(String dsType, String dsName, String tableName, String dbName) {
        List<Map<Object, Object>> tableList = new ArrayList<>();
        DataSourceContext.setDataSource(dbName);
        if (StringUtils.isNotBlank(dsType) && dsType.toUpperCase().equals("MYSQL")) {

            List<Map<Object, Object>> tempList = dsMapper.allDatabases(DSConstants.MYSQL_DS_TABLE_COLUMNS_ + " where table_schema='" + dsName + "' and table_name='" + tableName + "';");

            System.out.println();
            for (Map<Object, Object> map : tempList) {

                Map<Object, Object> tempMap = new HashMap<>();

                tempMap.put("dbName", dbName);
                tempMap.put("dbType", dsType);
                tempMap.put("dsName", dsName);
                tempMap.put("tableName", map.get("TABLE_NAME"));
                tempMap.put("columnsName", map.get("COLUMN_NAME"));
                tempMap.put("dataType", map.get("DATA_TYPE"));

                String columnType = map.get("COLUMN_TYPE").toString();

                String[] ct = getBracketContent(columnType);

                tempMap.put("dataLength", ct[0]);

                tableList.add(tempMap);
            }

            return tableList;
        }
        if (StringUtils.isNotBlank(dsType) && dsType.toUpperCase().equals("ORACLE")) {

            List<Map<Object, Object>> tempList = dsMapper.allDatabases(DSConstants.ORACLE_DS_TABLE_COLUMNS_ + " where table_name = '" + tableName + "'");

            System.out.println();
            for (Map<Object, Object> map : tempList) {

                Map<Object, Object> tempMap = new HashMap<>();

                tempMap.put("dbName", dbName);
                tempMap.put("dbType", dsType);
                tempMap.put("dsName", dsName);
                tempMap.put("tableName", map.get("TABLE_NAME"));
                tempMap.put("columnsName", map.get("COLUMN_NAME"));
                tempMap.put("dataType", map.get("DATA_TYPE"));
                tempMap.put("dataLength", map.get("DATA_LENGTH"));

                tableList.add(tempMap);

            }

            return tableList;

        }
        return null;
    }

    @Override
    public IPage<Map<Object, Object>> alltableData(String dsType, String dsName, String tableName, String tableSchema, Integer currentPage, Integer pageSize, String dbName) {

//        List<Map<Object, Object>> tableList = new ArrayList<>();
        DataSourceContext.setDataSource(dbName);
        if (StringUtils.isNotBlank(dsType) && dsType.toUpperCase().equals("MYSQL")) {

            List<Map<Object, Object>> tempList = dsMapper.allDatabases(DSConstants.MYSQL_DS_TABLE_DATA_ + tableSchema + "." + tableName);

            IPage page = new Page<>(currentPage, pageSize);

            page.setTotal(tempList.size());

            List<Map<Object, Object>> tlist = new ArrayList<>();
            f:
            for (int i = (currentPage - 1) * pageSize; i < tempList.size(); i++) {

                if (tlist.size() == pageSize) {
                    break f;
                }
                tlist.add(tempList.get(i));
            }

            page.setRecords(tlist);
            return page;
        }
        if (StringUtils.isNotBlank(dsType) && dsType.toUpperCase().equals("ORACLE")) {

            List<Map<Object, Object>> tempList = dsMapper.allDatabases(DSConstants.ORACLE_DS_TABLE_DATA_ + tableSchema + "." + tableName);
            IPage page = new Page<>(currentPage, pageSize);

            page.setTotal(tempList.size());
            List<Map<Object, Object>> tlist = new ArrayList<>();
            f:
            for (int i = (currentPage - 1) * pageSize; i < tempList.size(); i++) {
                if (tlist.size() == pageSize) {
                    break f;
                }
                tlist.add(tempList.get(i));
            }

            page.setRecords(tlist);
            return page;

        }
        return null;
    }

    @Override
    public String jdbctest(DataSourceInfoEntity entity) {

        Connection conn = null;
        try {
            Class.forName(entity.getDriver());//加载驱动类
            String url = entity.getUrl();
            String username = entity.getUsername();
            String password = entity.getPassword();
            conn = DriverManager.getConnection(url, username, password);//用参数得到连接对象
            System.out.println("连接成功！");
            System.out.println(conn);

        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        } finally {
            try {
                if (conn != null)
                    conn.close();
            }catch (Exception e){
                return "failed";
            }
        }
        return "success";
    }

    @Override
    public String addDs(DataSourceInfoEntity entity) {

        String result = jdbctest(entity);

        if ("failed".equals(result)){
            return "failed";
        }

        DruidDataSources dds = new DruidDataSources();
        dds.createdDataSource(entity);
        return "success";
    }

    private String[] getBracketContent(String content) {

        String[] arr = new String[0];
        if (content.indexOf("(") == -1) {
            return arr = content.split("");
        }

        Pattern p = Pattern.compile("(?<=\\()[^\\)]+");
        Matcher m = p.matcher(content);
        while (m.find()) {
            arr = Arrays.copyOf(arr, arr.length + 1);
            arr[arr.length - 1] = m.group();
        }
        return arr;

    }
}
