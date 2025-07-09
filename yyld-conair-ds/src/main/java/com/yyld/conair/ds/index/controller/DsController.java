package com.yyld.conair.ds.index.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yyld.conair.commons.data.result.APIResult;
import com.yyld.conair.druid.consts.DSConsts;
import com.yyld.conair.druid.entity.DataSourceInfoEntity;
import com.yyld.conair.druid.utils.DataSourceUtil;
import com.yyld.conair.ds.index.service.DSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
@RestController
@RequestMapping("/ds")
public class DsController {

    @Autowired
    private DSService dsService;

    //获取所有数据源
    @GetMapping("/alldslist")
    public APIResult<Map<Object,Object>> list() {

        Map<Object,Object> dsMap = new HashMap<>();

        dsMap.put("dslist",DataSourceUtil.entityList);
        dsMap.put("dbdriver", DSConsts.DB_DRIVER_);

        return APIResult.success(dsMap);

    }

    //测试数据源连接是否成功
    @PostMapping("/jdbctest")
    public APIResult<Void> jdbctest(@RequestBody DataSourceInfoEntity entity) {

        String  result = dsService.jdbctest(entity);

        if ("success".equals(result)){
            return APIResult.success();
        }

        return APIResult.failure();

    }

    //测试数据源连接是否成功
    @PostMapping("/addDs")
    public APIResult<Void> addDs(@RequestBody DataSourceInfoEntity entity) {

        Map<Object,Object> dsMap = DataSourceUtil.dataSourceMap;

        if (dsMap.get(entity.getDsName()) != null){

            return APIResult.failure("数据源重复");
        }

        String  result = dsService.addDs(entity);

        if ("success".equals(result)){
            return APIResult.success();
        }

        return APIResult.failure("数据源添加失败");

    }

    //获取所有所有数据库
    @GetMapping("/alldsliblist")
    public APIResult<List<Map<Object,Object>>> alldsliblist(String dsType,String dbName) {

        List<Map<Object,Object>> dsListMap = dsService.allDsLibList(dsType,dbName);

        return APIResult.success(dsListMap);

    }

    //根据数据库查询数据库所有表
    @GetMapping("/alltables")
    public APIResult<List<Map<Object,Object>>> alltables(String dsType,String dsName, String dbName) {

        List<Map<Object,Object>> dsListMap = dsService.alltables(dsType,dsName,dbName);

        return APIResult.success(dsListMap);

    }

    //根据数据库查询数据库所有表
    @GetMapping("/alltableColumns")
    public APIResult<List<Map<Object,Object>>> alltableColumns(String dsType,String dsName,String tableName, String dbName) {

        List<Map<Object,Object>> dsListMap = dsService.alltableColumns(dsType,dsName,tableName,dbName);

        return APIResult.success(dsListMap);

    }

    //根据数据库查询数据库表数据
    @GetMapping("/alltableData")
    public APIResult<IPage<Map<Object,Object>>> alltableData(String dsType,String dsName,String tableName,String tableSchema,Integer currentPage, Integer pageSize, String dbName) {

        IPage<Map<Object,Object>> dsListMap = dsService.alltableData(dsType,dsName,tableName,tableSchema,currentPage, pageSize,dbName);

        return APIResult.success(dsListMap);

    }
}
