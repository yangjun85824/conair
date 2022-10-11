package com.yyld.conair.eip.camel.inter.impl;

import com.alibaba.fastjson.JSON;
import com.yyld.conair.eip.camel.inter.ParamsHandleInter;
import com.yyld.conair.eip.entity.RestEntity;
import org.springframework.stereotype.Service;

@Service("getHeader")
public class GETHttpHeader implements ParamsHandleInter {

    @Override
    public void paramHandle(RestEntity entity) {

        System.out.println(JSON.toJSONString(entity));

    }
}
