package com.yyld.conair.ds.sap.util;

import com.google.common.collect.Maps;

import java.util.Map;

public final class SapParamsUtils {

    public static final Map<String,FunctionParamsVO> FUNCTION_PARAMS_VO_MAP = Maps.newHashMap();

    public static final String FUNCTION_NAME_ZCD_SAP_BE_KNA1 = "ZCD_SAP_BE_KNA1";
    public static final String FUNCTION_NAME_ZCD_SAP_BE_BSEG = "ZCD_SAP_BE_BSEG";
    public static final String FUNCTION_NAME_ZCD_SAP_BE_LFA1 = "ZCD_SAP_BE_LFA1";
    public static final String FUNCTION_NAME_ZCD_SAP_BE_BUKRS = "ZCD_SAP_BE_BUKRS";
    public static final String FUNCTION_NAME_ZCD_SAP_BE_BSEG_PC = "ZCD_SAP_BE_BSEG_PC";
    public static final String FUNCTION_NAME_ZCD_SAP_BE_BSEG_DATA = "ZCD_SAP_BE_BSEG_DATA";
    public static final String FUNCTION_NAME_ZCD_SAP_BE_BSEG_N = "ZCD_SAP_BE_BSEG_N";

    public static final String FUNCTION_NAME_ZCD_SAP_BE_BSEG_ZC = "ZCD_SAP_BE_BSEG_ZC";

    public static final String SAP_INT_VAR_CODE = "INSPUR_GET_ITDS01";
    public static final String SAP_BIZ_KEY = "1077230246380773376";

    //ZCD_SAP_BE_KNA1	SAP-BEever客户主数据接口
    //ZCD_SAP_BE_LFA1	SAP-BEever供应商主数据接口
    //ZCD_SAP_BE_BSEG	SAP与Beever对账平台财务凭证接口
    //ZCD_SAP_BE_BUKRS	SAP-Beever公司代码与管理架构层级接口
    //ZCD_SAP_BE_BSEG_PC	对账平台获取凭证批次接口
    //ZCD_SAP_BE_BSEG_DATA	对账平台通过批次获取凭证数据


    static {
        FUNCTION_PARAMS_VO_MAP.put(FUNCTION_NAME_ZCD_SAP_BE_KNA1,new FunctionParamsVO("ZCD_SAP_BE_KNA1","T_KNA1"));
        FUNCTION_PARAMS_VO_MAP.put(FUNCTION_NAME_ZCD_SAP_BE_BSEG,new FunctionParamsVO("ZCD_SAP_BE_BSEG","T_BSEG"));
        FUNCTION_PARAMS_VO_MAP.put(FUNCTION_NAME_ZCD_SAP_BE_LFA1,new FunctionParamsVO("ZCD_SAP_BE_LFA1","T_LFA1"));
        FUNCTION_PARAMS_VO_MAP.put(FUNCTION_NAME_ZCD_SAP_BE_BUKRS,new FunctionParamsVO("ZCD_SAP_BE_BUKRS","T_BUKRS"));

        FUNCTION_PARAMS_VO_MAP.put(FUNCTION_NAME_ZCD_SAP_BE_BSEG_PC,new FunctionParamsVO("ZCD_SAP_BE_BSEG_PC","T_PICI"));
        FUNCTION_PARAMS_VO_MAP.put(FUNCTION_NAME_ZCD_SAP_BE_BSEG_DATA,new FunctionParamsVO("ZCD_SAP_BE_BSEG_DATA","T_BSEG"));
        FUNCTION_PARAMS_VO_MAP.put(FUNCTION_NAME_ZCD_SAP_BE_BSEG_N,new FunctionParamsVO("ZCD_SAP_BE_BSEG_N","T_BSEG"));
        FUNCTION_PARAMS_VO_MAP.put(FUNCTION_NAME_ZCD_SAP_BE_BSEG_ZC,new FunctionParamsVO("ZCD_SAP_BE_BSEG_ZC","T_BSEG"));

    }

}
