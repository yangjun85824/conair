package com.yyld.conair.ds.sql;

import com.alibaba.druid.sql.ast.SQLStatement;

public interface SQLInjectionInter {

    String handler(SQLStatement sqlStatement);
}
