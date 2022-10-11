package com.yyld.conair.ds.users.controller;


import com.yyld.conair.commons.data.result.APIResult;
import com.yyld.conair.druid.consts.DSConsts;
import com.yyld.conair.druid.utils.DataSourceUtil;
import com.yyld.conair.ds.index.service.DSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private DSService dsService;

    //获取所有所有数据库
    @GetMapping("/alist")
    public APIResult<List<Map<Object,Object>>> alldsliblist(String dsType, String dbName) {

        List<Map<Object, Object>> dsListMap = dsService.allDsLibList(dsType, dbName);

        System.out.println("dsType=========="+dsType);

        return APIResult.success(dsListMap);
    }

    //获取所有所有数据库
    @PostMapping("/post")
    public APIResult<Map<Object,Object>> post(String dsType, String dbName) {
        Map<Object,Object> dsMap = new HashMap<>();

        dsMap.put("dslist", DataSourceUtil.entityList);
        dsMap.put("dbdriver", DSConsts.DB_DRIVER_);

        return APIResult.success(dsMap);
    }

}
