package com.yyld.conair.commons.data.constants;

import com.yyld.conair.commons.data.abs.AbsOperatorHandler;
import com.yyld.conair.commons.data.abs.ext.*;
import com.yyld.conair.commons.data.inter.DataFieldTypeInter;
import com.yyld.conair.commons.data.inter.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Constants
 * @Description 静态类
 * @Author yangj
 * @Date 2022/1/12 10;15
 * @Vresion 1.0
 **/
public final class Constants {

    public static String GROUP_TYPE = "GROUP";
    public static String FIELD_TYPE = "FIELD";

    public static String GROUP_OPERATOR_AND = "AND";
    public static String GROUP_OPERATOR_OR = "OR";
    public static String GROUP_OPERATOR_ = "";

    public static String OPERATOR_EQ = "EQ";//EQ 就是 EQUAL等于
    public static String OPERATOR_NE = "NE";//NE 就是 NOT EQUAL不等于
    public static String OPERATOR_GT = "GT";//GT 就是 GREATER THAN大于　
    public static String OPERATOR_LT = "LT";//LT 就是 LESS THAN小于
    public static String OPERATOR_GE = "GE";//GE 就是 GREATER THAN OR EQUAL 大于等于
    public static String OPERATOR_LE = "LE";//LE 就是 LESS THAN OR EQUAL 小于等于
    public static String OPERATOR_BT = "BT";//介于 区间
    public static String OPERATOR_BE = "BE";//不介于 区间
    public static String OPERATOR_ISNULL = "ISNULL";//空
    public static String OPERATOR_NOTNULL = "NOTNULL";//非空
    public static String OPERATOR_ISLIKE = "ISLIKE";//包含
    public static String OPERATOR_NOTLIKE = "NOTLIKE";//不包含
    public static String OPERATOR_ISIN = "ISIN";//属于
    public static String OPERATOR_NOTIN = "NOTIN";//不属于

    public static String FIELD_TYPE_L = "L";//单值
    public static String FIELD_TYPE_M = "M";//多值
    public static String FIELD_TYPE_V = "V";//变量

    public static String TYPE_C = "CONAST";//常量
    public static String TYPE_V = "VAR";//变量

    public static String SQL_DATA_TYPE_TINYINT = "tinyint";
    public static String SQL_DATA_TYPE_SMALLINT = "smallint";
    public static String SQL_DATA_TYPE_MEDIUMINT = "mediumint";
    public static String SQL_DATA_TYPE_INT = "int";
    public static String SQL_DATA_TYPE_BIGINT = "bigint";
    public static String SQL_DATA_TYPE_FLOAT = "float";
    public static String SQL_DATA_TYPE_DOUBLE = "double";
    public static String SQL_DATA_TYPE_NUMBER = "number";
    public static String SQL_DATA_TYPE_DECIMAL = "decimal";
    public static String SQL_DATA_TYPE_CHAR = "char";
    public static String SQL_DATA_TYPE_NCHAR = "nchar";
    public static String SQL_DATA_TYPE_VARCHAR = "varchar";
    public static String SQL_DATA_TYPE_VARCHAR2 = "varchar2";
    public static String SQL_DATA_TYPE_NVARCHAR2 = "nvarchar2";
    public static String SQL_DATA_TYPE_TINYTEXT = "tinytext";
    public static String SQL_DATA_TYPE_TEXT = "text";
    public static String SQL_DATA_TYPE_MEDIUMTEXT = "mediumtext";
    public static String SQL_DATA_TYPE_LONGTEXT = "longtext";
    public static String SQL_DATA_TYPE_CLOB = "clob";
    public static String SQL_DATA_TYPE_LONGCLOB = "longclob";
    public static String SQL_DATA_TYPE_DATE = "date";
    public static String SQL_DATA_TYPE_TIME = "time";
    public static String SQL_DATA_TYPE_DATETIME = "datetime";
    public static String SQL_DATA_TYPE_TIMESTAMP = "timestamp";

    public static String DATA_TYPE_STRING = "string";
    public static String DATA_TYPE_INTEGER = "integer";
    public static String DATA_TYPE_LONG = "long";
    public static String DATA_TYPE_SHORT = "short";
    public static String DATA_TYPE_BYTE = "byte";
    public static String DATA_TYPE_BOOLEAN = "boolean";

    public static String DATA_TYPE_NULL = null;

    public final static Map<String , DataFieldTypeInter> fieldTypeMap = new HashMap<>();

    public final static Map<String , AbsOperatorHandler> operatorMap = new HashMap<>();

    static {

        fieldTypeMap.put(SQL_DATA_TYPE_TINYINT,new IntDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_SMALLINT,new IntDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_MEDIUMINT,new IntDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_INT,new IntDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_BIGINT,new IntDataFieldTypeImpl());

        fieldTypeMap.put(SQL_DATA_TYPE_FLOAT,new DoubleDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_DOUBLE,new DoubleDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_NUMBER,new DoubleDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_DECIMAL,new DoubleDataFieldTypeImpl());

        fieldTypeMap.put(SQL_DATA_TYPE_CHAR,new StringDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_NCHAR,new StringDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_VARCHAR,new StringDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_VARCHAR2,new StringDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_NVARCHAR2,new StringDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_TINYTEXT,new StringDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_TEXT,new StringDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_MEDIUMTEXT,new StringDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_TEXT,new StringDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_LONGTEXT,new StringDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_CLOB,new StringDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_LONGCLOB,new StringDataFieldTypeImpl());

        fieldTypeMap.put(SQL_DATA_TYPE_DATE,new DateDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_TIME,new DateDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_DATETIME,new DateDataFieldTypeImpl());
        fieldTypeMap.put(SQL_DATA_TYPE_TIMESTAMP,new DateDataFieldTypeImpl());

        fieldTypeMap.put(DATA_TYPE_STRING,new StringDataFieldTypeImpl());
        fieldTypeMap.put(DATA_TYPE_BYTE,new StringDataFieldTypeImpl());
        fieldTypeMap.put(DATA_TYPE_INTEGER,new IntDataFieldTypeImpl());
        fieldTypeMap.put(DATA_TYPE_LONG,new DoubleDataFieldTypeImpl());
        fieldTypeMap.put(DATA_TYPE_SHORT,new IntDataFieldTypeImpl());
        fieldTypeMap.put(DATA_TYPE_BOOLEAN,new BooleanDataFieldTypeImpl());

        fieldTypeMap.put(DATA_TYPE_NULL,new StringDataFieldTypeImpl());

        operatorMap.put(OPERATOR_EQ, new EQOperatorHandler());
        operatorMap.put(OPERATOR_NE, new NEOperatorHandler());
        operatorMap.put(OPERATOR_GT, new GTOperatorHandler());
        operatorMap.put(OPERATOR_GE, new GEOperatorHandler());
        operatorMap.put(OPERATOR_LT, new LTOperatorHandler());
        operatorMap.put(OPERATOR_LE, new LEOperatorHandler());
        operatorMap.put(OPERATOR_BT, new BTOperatorHandler());
        operatorMap.put(OPERATOR_BE, new BEOperatorHandler());

        operatorMap.put(OPERATOR_ISIN, new ISINOperatorHandler());
        operatorMap.put(OPERATOR_ISLIKE, new ISLIKEOperatorHandler());
        operatorMap.put(OPERATOR_ISNULL, new ISNULLOperatorHandler());
        operatorMap.put(OPERATOR_NOTIN, new NOTINOperatorHandler());
        operatorMap.put(OPERATOR_NOTLIKE, new NOTLIKEOperatorHandler());
        operatorMap.put(OPERATOR_NOTNULL, new NOTNULLOperatorHandler());

    }

}
