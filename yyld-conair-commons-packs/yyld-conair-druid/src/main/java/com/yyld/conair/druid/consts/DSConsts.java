package com.yyld.conair.druid.consts;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DSConsts
 * @Description //TODO
 * @Author yangj
 * @Date 2022/9/7 22:38
 * @Vresion 1.0
 **/
public final class DSConsts {

    public final static Map<String,String> DBTYPE_ = new HashMap<>();

    public final static Map<String,String> DB_DRIVER_ = new HashMap<>();

    static {
        DBTYPE_.put("com.mysql.cj.jdbc.Driver","mysql");
        DBTYPE_.put("oracle.jdbc.OracleDriver","oracle");

        DB_DRIVER_.put("mysql","com.mysql.cj.jdbc.Driver");
        DB_DRIVER_.put("oracle","oracle.jdbc.OracleDriver");

    }

}
