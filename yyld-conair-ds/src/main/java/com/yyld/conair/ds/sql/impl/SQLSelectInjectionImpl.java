package com.yyld.conair.ds.sql.impl;

import com.alibaba.druid.sql.ast.SQLExprImpl;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.google.common.collect.Maps;
import com.yyld.conair.ds.sql.SQLInjectionInter;
import com.yyld.conair.ds.sql.SQLSelectInjectionInter;
import com.yyld.conair.ds.sql.utils.SQLValueHandlerUtils;

import java.util.Map;

public class SQLSelectInjectionImpl implements SQLInjectionInter, SQLSelectInjectionInter {
    @Override
    public String handler(SQLStatement sqlStatement) {

        Map<String, String> mapValue = Maps.newConcurrentMap();

        String sql = sqlStatement.toString();
        SQLSelectQueryBase valuesQuery = (SQLSelectQueryBase) ((SQLSelectStatement) sqlStatement).getSelect().getQuery();

        handlerSql(valuesQuery, mapValue);

        sql = SQLValueHandlerUtils.handlerMapValue(sql, mapValue);

        return sql;
    }

    public void handlerSql(SQLSelectQueryBase valuesQuery, Map<String, String> mapValue) {

        SQLBinaryOpExpr sqlBinaryExpr = (SQLBinaryOpExpr) ((SQLSelectQueryBlock) valuesQuery).getWhere();
        Object obj = ((SQLSelectQueryBlock) valuesQuery).getFrom();

        SQLExprImpl expr = null;
        if (obj instanceof SQLJoinTableSource) {
            SQLJoinTableSource joinTableSource = (SQLJoinTableSource) obj;
            expr = (SQLExprImpl) joinTableSource.getCondition();
        }
        if (obj instanceof SQLExprTableSource) {
            SQLExprTableSource tableSource = (SQLExprTableSource) obj;
            expr = (SQLExprImpl) tableSource.getExpr();
        }

        if (expr instanceof SQLBinaryOpExpr) {
            handlerSqlBinaryExpr((SQLBinaryOpExpr) expr, mapValue);
        }
        if (sqlBinaryExpr != null) {
            handlerSqlBinaryExpr(sqlBinaryExpr, mapValue);
        }
    }

    public void handlerSqlBinaryExpr(SQLBinaryOpExpr opExpr, Map<String, String> mapValue) {

        Object obj = opExpr.getRight();
        Object lobj = opExpr.getLeft();
        if (obj instanceof SQLInSubQueryExpr) {
            SQLInSubQueryExpr subQueryExpr = (SQLInSubQueryExpr) obj;
            handlerSql((SQLSelectQueryBase) subQueryExpr.getSubQuery().getQuery(), mapValue);
        }
        if (lobj instanceof SQLInSubQueryExpr) {
            SQLInSubQueryExpr subQueryExpr = (SQLInSubQueryExpr) lobj;
            handlerSql((SQLSelectQueryBase) subQueryExpr.getSubQuery().getQuery(), mapValue);
        }
        if (obj instanceof SQLQueryExpr) {
            SQLQueryExpr queryExpr = (SQLQueryExpr) obj;
            handlerSql((SQLSelectQueryBase) queryExpr.getSubQuery().getQuery(), mapValue);
        }
        if (lobj instanceof SQLQueryExpr) {
            SQLQueryExpr queryExpr = (SQLQueryExpr) lobj;
            handlerSql((SQLSelectQueryBase) queryExpr.getSubQuery().getQuery(), mapValue);
        }
        if (obj instanceof SQLIdentifierExpr) {
            SQLIdentifierExpr identifierExpr = (SQLIdentifierExpr) obj;
            String value = identifierExpr.getName();
            String resultValue = SQLValueHandlerUtils.valueHandler(value);
            mapValue.put(value, resultValue);
            //System.out.println(value + "    " + resultValue);
        }
        if (lobj instanceof SQLIdentifierExpr) {
            SQLIdentifierExpr identifierExpr = (SQLIdentifierExpr) lobj;
            String value = identifierExpr.getName();
            String resultValue = SQLValueHandlerUtils.valueHandler(value);
            mapValue.put(value, resultValue);
            //System.out.println(value + "    " + resultValue);
        }

        if (obj instanceof SQLPropertyExpr) {
            SQLPropertyExpr right = (SQLPropertyExpr) obj;
            String value = right.getName();
            String resultValue = SQLValueHandlerUtils.valueHandler(value);
            mapValue.put(value, resultValue);
            //System.out.println(value + "    " + resultValue);
        }
        if (lobj instanceof SQLPropertyExpr) {
            SQLPropertyExpr left = (SQLPropertyExpr) lobj;
            String value = left.getName();
            String resultValue = SQLValueHandlerUtils.valueHandler(value);
            mapValue.put(value, resultValue);
            //System.out.println(value + "    " + resultValue);
        }

        if (obj instanceof SQLBinaryOpExpr) {
            handlerSqlBinaryExpr((SQLBinaryOpExpr) obj, mapValue);
        }
        if (lobj instanceof SQLBinaryOpExpr) {
            handlerSqlBinaryExpr((SQLBinaryOpExpr) lobj, mapValue);
        }
    }

}
