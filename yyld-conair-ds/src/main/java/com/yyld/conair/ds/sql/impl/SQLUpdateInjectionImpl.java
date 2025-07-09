package com.yyld.conair.ds.sql.impl;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLQueryExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.google.common.collect.Maps;
import com.yyld.conair.ds.sql.SQLInjectionInter;
import com.yyld.conair.ds.sql.SQLSelectInjectionInter;
import com.yyld.conair.ds.sql.utils.SQLValueHandlerUtils;

import java.util.List;
import java.util.Map;

public class SQLUpdateInjectionImpl implements SQLInjectionInter {
    @Override
    public String handler(SQLStatement sqlStatement) {

        Map<String, String> mapValue = Maps.newConcurrentMap();

        String sql = sqlStatement.toString();

        SQLUpdateStatement updateStatement = (SQLUpdateStatement)sqlStatement;

        List<SQLUpdateSetItem> updateSetItemList = updateStatement.getItems();
        for (SQLUpdateSetItem item : updateSetItemList){
            Object obj = item.getValue();
            if (obj instanceof SQLIdentifierExpr) {
                SQLIdentifierExpr identifierExpr = (SQLIdentifierExpr) obj;
                String value = identifierExpr.getName();
                String resultValue = SQLValueHandlerUtils.valueHandler(value);
                mapValue.put(value, resultValue);
                //System.out.println(value + "    " + resultValue);
            }
            if (obj instanceof SQLQueryExpr){
                SQLQueryExpr sqlQueryExpr = (SQLQueryExpr) obj;
                SQLSelectQueryBlock selectQueryBlock = (SQLSelectQueryBlock) sqlQueryExpr.getSubQuery().getQuery();
                SQLSelectInjectionInter sqlInjectionInter = new SQLSelectInjectionImpl();
                sqlInjectionInter.handlerSql(selectQueryBlock,mapValue);
            }
        }

        Object where = updateStatement.getWhere();
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
