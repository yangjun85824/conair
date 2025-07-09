package com.yyld.conair.ds.sql.impl;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLQueryExpr;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement.ValuesClause;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.google.common.collect.Maps;
import com.yyld.conair.ds.sql.SQLInjectionInter;
import com.yyld.conair.ds.sql.SQLSelectInjectionInter;
import com.yyld.conair.ds.sql.utils.SQLValueHandlerUtils;

import java.util.List;
import java.util.Map;

public class SQLInsertInjectionImpl implements SQLInjectionInter {
    @Override
    public String handler(SQLStatement sqlStatement) {

        Map<String, String> mapValue = Maps.newConcurrentMap();

        String sql = sqlStatement.toString();

        SQLInsertStatement insertStatement = (SQLInsertStatement)sqlStatement;

        SQLSelect sqlSelect = insertStatement.getQuery();
        if (sqlSelect != null){
            SQLSelectQueryBlock selectQueryBlock = (SQLSelectQueryBlock) sqlSelect.getQuery();
            SQLSelectInjectionInter sqlInjectionInter = new SQLSelectInjectionImpl();
            sqlInjectionInter.handlerSql(selectQueryBlock,mapValue);
        }

        List<ValuesClause> identifierExprList = insertStatement.getValuesList();

        for (ValuesClause valuesClause : identifierExprList){

            List<SQLExpr> values = valuesClause.getValues();
            for (SQLExpr expr : values){

                if (expr instanceof SQLQueryExpr){
                    SQLQueryExpr sqlQueryExpr = (SQLQueryExpr) expr;
                    SQLSelectQueryBlock selectQueryBlock = (SQLSelectQueryBlock) sqlQueryExpr.getSubQuery().getQuery();
                    SQLSelectInjectionInter sqlInjectionInter = new SQLSelectInjectionImpl();
                    sqlInjectionInter.handlerSql(selectQueryBlock,mapValue);
                }
                if (expr instanceof SQLIdentifierExpr){
                    SQLIdentifierExpr identifierExpr = (SQLIdentifierExpr) expr;
                    String value = identifierExpr.getName();
                    String val = SQLValueHandlerUtils.valueHandler(value);
                    mapValue.put(value,val);
                    System.out.println(identifierExpr.getName());
                }

            }
            System.out.println();
        }
        sql = SQLValueHandlerUtils.handlerMapValue(sql, mapValue);
        System.out.println(sql);
        return sql;
    }


}
