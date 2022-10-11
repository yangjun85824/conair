package com.yyld.conair.ds.index.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yyld.conair.druid.entity.DataSourceInfoEntity;
import com.yyld.conair.ds.users.entity.Users;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
public interface DSService extends IService<Map<Object,Object>> {

    //查询所有数据库
    List<Map<Object, Object>> allDsLibList(String dsType, String dbName);
    //查询所有数据库表
    List<Map<Object, Object>> alltables(String dsType, String dsName, String dbName);
    //查询数据表字段
    List<Map<Object, Object>> alltableColumns(String dsType, String dsName, String tableName, String dbName);
    //查询所有的表数据
    IPage<Map<Object, Object>> alltableData(String dsType, String dsName, String tableName, String tableSchema,Integer currentPage, Integer pageSize, String dbName);

    //测试jdbc数据源连接
    String jdbctest(DataSourceInfoEntity entity);
    //动态添加数据源
    String addDs(DataSourceInfoEntity entity);
}
