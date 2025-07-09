package com.yyld.conair.ds.sql;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.*;
import com.google.common.collect.Lists;
import com.yyld.conair.ds.sql.impl.SQLDeleteInjectionImpl;
import com.yyld.conair.ds.sql.impl.SQLInsertInjectionImpl;
import com.yyld.conair.ds.sql.impl.SQLSelectInjectionImpl;
import com.yyld.conair.ds.sql.impl.SQLUpdateInjectionImpl;

import java.util.List;

public final class SQLInjection {

    public List<String> sqlHandler(String sql, DbType dbType){

        sql = sql.replaceAll("'","！！！");
        List<SQLStatement> statementList = SQLUtils.parseStatements(sql, dbType);
        List<String> result = Lists.newArrayList();
        SQLInjectionInter injectionInter = null;

        for (SQLStatement sqlStatement : statementList) {

            if (sqlStatement instanceof SQLSelectStatement) {
                injectionInter = new SQLSelectInjectionImpl();
            }
            if (sqlStatement instanceof SQLInsertStatement) {
                injectionInter = new SQLInsertInjectionImpl();
            }
            if (sqlStatement instanceof SQLUpdateStatement) {
                injectionInter = new SQLUpdateInjectionImpl();
            }
            if (sqlStatement instanceof SQLDeleteStatement) {
                injectionInter = new SQLDeleteInjectionImpl();
            }

            if (injectionInter != null) {
                result.add(handler(injectionInter,sqlStatement));
            }
        }

        return result;
    }

    private String handler(SQLInjectionInter injectionInter, SQLStatement sqlStatement) {

        String sql = injectionInter.handler(sqlStatement);

        return sql;
    }

}
