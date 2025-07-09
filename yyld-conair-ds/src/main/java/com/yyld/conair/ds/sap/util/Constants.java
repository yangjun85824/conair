package com.yyld.conair.ds.sap.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Constants
 * @Description 常量
 * @Author yangj
 * @Date 2022/6/7 11:40
 * @Vresion 1.0
 **/
public final class Constants {

    /**
     KUNNR	客户编号	CHAR	10
     NAME1	名称 1	CHAR	35
     LAND1	国家/地区代码	CHAR	3
     ERDAT	记录创建日期	DATS	8
     VBUND	公司标识	CHAR	6
     STCEG	增值税登记号 	CHAR	20
     **/

    /**
     * I_LIFNR	供应商	CHAR	10
     * NAME1	名称 1	CHAR	35
     * LAND1	国家/地区代码	CHAR	3
     * ERDAT	记录创建日期	DATS	8
     * VBUND	公司标识	CHAR	6
     * STCEG	增值税登记号 	CHAR	20
     */
    public static Map<String,Map<String,String>> RESULT_TABLE_NAME_MAP_ = new HashMap<>();
    //客户主数据接口 字段类型
    public static Map<String,String> T_KNA1_FILED_ = new HashMap<>();
    //供应商主数据接口 字段类型
    public static Map<String,String> T_LFA1_FILED_ = new HashMap<>();
    //参数 字段类型
    public static Map<String,String> PARAM_FILED_ = new HashMap<>();
    //参数字段映射
    public static Map<String,String> BUKRS_PARAM_FILED_ = new HashMap<>();
    //关联方信息表
    public static String ZCD_SAP_BE_BUKRS_BO_CODE = "BE_BO_LC_GLFXX";
    //公司代码主数据表
    public static String ZCD_SAP_BE_BUKRS_LC_BUKRS_BO_CODE = "BE_BO_LC_BUKRS";

    static {

        T_KNA1_FILED_.put("KUNNR","CHAR");
        T_KNA1_FILED_.put("NAME1","CHAR");
        T_KNA1_FILED_.put("LAND1","CHAR");
        T_KNA1_FILED_.put("ERDAT","DATS");
        T_KNA1_FILED_.put("VBUND","CHAR");
        T_KNA1_FILED_.put("STCEG","CHAR");

        T_LFA1_FILED_.put("I_LIFNR","CHAR");
        T_LFA1_FILED_.put("NAME1","CHAR");
        T_LFA1_FILED_.put("LAND1","CHAR");
        T_LFA1_FILED_.put("ERDAT","DATS");
        T_LFA1_FILED_.put("VBUND","CHAR");
        T_LFA1_FILED_.put("STCEG","CHAR");

        PARAM_FILED_.put("I_CPUDT_FROM","DATS");
        PARAM_FILED_.put("I_CPUDT_TO","DATS");
        PARAM_FILED_.put("I_BLDAT","DATS");
        PARAM_FILED_.put("I_BUDAT","DATS");
        PARAM_FILED_.put("I_FROMDATE","DATS");
        PARAM_FILED_.put("I_TODATE","DATS");

        RESULT_TABLE_NAME_MAP_.put("T_KNA1",T_KNA1_FILED_);
        RESULT_TABLE_NAME_MAP_.put("T_LFA1",T_LFA1_FILED_);
        RESULT_TABLE_NAME_MAP_.put("PARAM_FILED_",PARAM_FILED_);

        BUKRS_PARAM_FILED_.put("ID_","ID_");
        BUKRS_PARAM_FILED_.put("BUKRS_S","BUKRS");
        BUKRS_PARAM_FILED_.put("BUTXT_ST","BUTXT");
    }

}
