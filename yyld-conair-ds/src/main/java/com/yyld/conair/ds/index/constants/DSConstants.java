package com.yyld.conair.ds.index.constants;

/**
 * @ClassName DSConstants
 * @Description //TODO
 * @Author yangj
 * @Date 2022/8/25 14:45
 * @Vresion 1.0
 **/
public final class DSConstants {

    //mysql数据库 查询 查询所有数据库
    public final static String MYSQL_DS_DATABASES_ = "show databases";

    //oracle数据库 查询 查询所有数据库
    public final static String ORACLE_DS_DATABASES_ = "select * from v$tablespace";

    //mysql数据库 查询 所有表
    public final static String MYSQL_DS_ALLTABLE_ = "select * from information_schema.tables ";

    //oracle数据库 查询 所有表
    public final static String ORACLE_DS_ALLTABLE_ = "select * from all_tables ";//user_tables ";

    //MYSQL数据库 查询 表字段及类型
    public final static String MYSQL_DS_TABLE_COLUMNS_ = "select * from information_schema.columns ";

    //oracle数据库 查询 表字段及类型
    public final static String ORACLE_DS_TABLE_COLUMNS_ = "select * from all_tab_columns ";//user_tab_columns ";

    //MYSQL数据库 查询 表数据查询
    public final static String MYSQL_DS_TABLE_DATA_ = "select * from ";
    //oracle数据库 查询 表数据查询
    public final static String ORACLE_DS_TABLE_DATA_ = "select * from ";

    //MYSQL数据库 查询建表语句
    public final static String MYSQL_DS_TABLE_DDL_ = "SHOW CREATE TABLE ";

    //oracle数据库 查询建表语句
//    public final static String ORACLE_DS_TABLE_DDL_ = "SHOW CREATE TABLE ";

}
