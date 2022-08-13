package com.yyld.conair.commons.data.inter.impl;

import com.yyld.conair.commons.data.inter.FaceDataHandlerInter;
import com.yyld.conair.commons.data.inter.RelationCondition;
import com.yyld.conair.commons.data.inter.SQLManyRelationCondition;
import com.yyld.conair.commons.data.inter.SQLRelationCondition;

import java.util.List;
import java.util.Map;

/**
 * @ClassName FaceDataHandlerImpl
 * @Description //TODO
 * @Author yangj
 * @Date 2022/6/1 9:16
 * @Vresion 1.0
 **/
public final class FaceDataHandlerImpl implements FaceDataHandlerInter {

    private RelationCondition relationCondition;

    private SQLRelationCondition sqlRelationCondition;

    private SQLManyRelationCondition sqlManyRelationCondition;

    @Override
    public <T> List<T> handler(List<T> data, Map<String, Object> paramMap, String json) throws Exception {

        relationCondition = new RelationConditionImpl();

        return relationCondition.resultHandler(paramMap, data, json);
    }

    @Override
    public <T> String sqlWhereHandler(Map<String, Object> paramMap, String json) throws Exception {

        sqlRelationCondition = new SQLRelationConditionImpl();

        return sqlRelationCondition.resultHandler(paramMap, json);
    }

    @Override
    public <T> String sqlManyWhereHandler(List<Map<String, Object>> mapList, String json, String relType) throws Exception {

        sqlManyRelationCondition = new SQLManyRelationConditionImpl();

        return sqlManyRelationCondition.resultHandler(mapList, json, relType);
    }
}
