package com.yyld.conair.friend.controller;


import com.alibaba.fastjson2.JSON;
import com.yyld.conair.commons.data.result.APIResult;
import com.yyld.conair.friend.entity.Test;
import com.yyld.conair.friend.test.FrontSubmit;
import com.yyld.conair.friend.test.FrontSubmitType;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
@RestController
@RequestMapping("")
public class IndexController {

    //测试数据源连接是否成功
    @PostMapping("/index")
    public APIResult<Void> jdbctest(@RequestParam(name = "a") String a, @RequestBody Test entity) {

        String  result = JSON.toJSONString(entity);

        System.out.println("result ========== "+result);
        if ("success".equals(result)){
            return APIResult.success();
        }

        return APIResult.failure();

    }
    //测试数据源连接是否成功
    @GetMapping("/index")
    public APIResult<Void> get(String a, Test entity) {

        String  result = JSON.toJSONString(entity);
        System.out.println("result ========== "+result);
        if ("success".equals(result)){
            return APIResult.success();
        }

        return APIResult.failure();

    }

    //测试数据源连接是否成功
    @GetMapping("/abc")
    public APIResult<Void> get() {

        System.out.println("方法执行开始");
        new Thread(new Runnable() {
            @Override
            public void run() {
                aspect();
            }
        }).start();
        System.out.println("方法执行结束");
        return APIResult.success();

    }

    @FrontSubmit(type = FrontSubmitType.CHECK_TYPE)
    public APIResult<Void> aspect() {

        System.out.println("切面执行");

        return APIResult.success();

    }
}
