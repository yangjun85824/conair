package com.yyld.conair.ds.sql;

import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBase;

import java.util.Map;

public interface SQLSelectInjectionInter {

    void handlerSql(SQLSelectQueryBase valuesQuery, Map<String, String> mapValue);
    void handlerSqlBinaryExpr(SQLBinaryOpExpr opExpr, Map<String, String> mapValue);

}
