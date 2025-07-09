package com.yyld.conair.ds.sql.impl;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.google.common.collect.Maps;
import com.yyld.conair.ds.sql.SQLInjectionInter;
import com.yyld.conair.ds.sql.SQLSelectInjectionInter;
import com.yyld.conair.ds.sql.utils.SQLValueHandlerUtils;

import java.util.Map;

public class SQLDeleteInjectionImpl implements SQLInjectionInter {
    @Override
    public String handler(SQLStatement sqlStatement) {

        Map<String, String> mapValue = Maps.newConcurrentMap();

        String sql = sqlStatement.toString();

        SQLDeleteStatement deleteStatement = (SQLDeleteStatement)sqlStatement;

        Object where = deleteStatement.getWhere();
        if (where != null){
            SQLBinaryOpExpr selectQueryBlock = (SQLBinaryOpExpr) where;
            SQLSelectInjectionInter sqlInjectionInter = new SQLSelectInjectionImpl();
            sqlInjectionInter.handlerSqlBinaryExpr(selectQueryBlock,mapValue);
        }

        sql = SQLValueHandlerUtils.handlerMapValue(sql, mapValue);
        System.out.println(sql);
        return sql;
    }

}
