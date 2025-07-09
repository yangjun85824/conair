package com.yyld.conair.ds.sap.util;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FunctionParamsVO implements Serializable {

    private static final long serialVersionUID = 4173528392044715862L;

    //sap函数名
    private String functionName;
    //返回表名
    private String resultTable;
    //参数列表
    private List<Params> paramsList;
    //定时任务结束时间
    private String endTime;
    //变式Code
    private String intVarCode;
    //业务主键（事务ID）
    private String bizKey;
    //主数据类型ID
    private String defId;
    //视图ID
    private String viewId;
    //主表ID
    private String headId;
    //数据获取的类型
    protected String sourceType;
    //批次号
    protected String batchNo;
    //操作符
    protected String operator;
    //自动或手动  自动 auto  手动 manual
    protected String mode;
    //SAP返回的数据量
    protected Integer sapCount;
    //调用次数
    protected Integer callCount;
    //运行ID
    protected String runId;
    //appCode应用编码
    protected String appCode;

    public FunctionParamsVO(){}

    public FunctionParamsVO(String functionName, String resultTable){
        this.functionName = functionName;
        this.resultTable = resultTable;
    }
    @Data
    public static class Params{
        //参数名
        private String name;
        //参数值
        private Object value;

        public Params(){}
        public Params(String name,Object value){
            this.name = name;
            this.value = value;
        }
    }
}
