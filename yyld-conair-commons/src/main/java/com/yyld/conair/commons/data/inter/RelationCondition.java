package com.yyld.conair.commons.data.inter;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PreRelationCondition
 * @Description 关联条件解析类。该类主要用于过滤结果数据
 * @Author yangj
 * @Date 2022/1/11 10:00
 * @Vresion 1.0
 **/
public interface RelationCondition {

    /**
     * @MonthedName resultHandler 数据过滤方法
     * @Description 操作流程： 后台配置关联关系规则 保存 -> 前台传入 结果数据列表 及过滤参数 -> 过滤数据 -> 返回过滤后的数据列表
     *              业务描述： 关联关系用于筛选过滤数据。
     * @Date 2022/1/13 15:46
     * @param data 过滤参数
     * @param list 过滤列表
     * @param rule 过滤规则
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>  过滤后返回的结果集
     *
     **/
    <T> List<T> resultHandler(Map<String, Object> data, List<T> list, String rule) throws ParseException;

}
