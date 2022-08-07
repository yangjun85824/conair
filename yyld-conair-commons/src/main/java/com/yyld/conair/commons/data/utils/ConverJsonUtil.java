package com.yyld.conair.commons.data.utils;

import com.alibaba.fastjson.JSONObject;
import com.yyld.conair.commons.data.entity.DataConditionStructureEntity;

import java.util.List;

/**
 * @ClassName ConverJsonUtil
 * @Description //TODO
 * @Author yangj
 * @Date 2022/5/31 15:55
 * @Vresion 1.0
 **/
public final class ConverJsonUtil {

    /**
     *
     * json规则数据转换为list结构
     *
     * @param json
     * @return
     * @throws Exception
     */
    public static List<DataConditionStructureEntity> convertJson(String json) throws Exception {

        if ("{}".equals(json) || "".equals(json) || json == null){

            throw new Exception("规则数据为空");

        }

        List<DataConditionStructureEntity> result;

        try {

            result = JSONObject.parseArray(json, DataConditionStructureEntity.class);

        }catch (Exception e){

            throw new Exception("规则数据格式不正确");

        }

        return result;
    }

}
