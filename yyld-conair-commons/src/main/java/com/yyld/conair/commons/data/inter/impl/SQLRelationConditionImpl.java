package com.yyld.conair.commons.data.inter.impl;

import com.alibaba.fastjson.JSON;
import com.yyld.conair.commons.data.abs.AbsOperatorHandler;
import com.yyld.conair.commons.data.constants.Constants;
import com.yyld.conair.commons.data.entity.DataConditionStructureEntity;
import com.yyld.conair.commons.data.inter.SQLRelationCondition;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PreRelationConditionImpl
 * @Description 关联条件解析实现类。该类主要用于过滤结果数据
 * @Author yangj
 * @Date 2022/1/11 10:01
 * @Vresion 1.0
 **/
//@Component
public class SQLRelationConditionImpl implements SQLRelationCondition {

    private static final Logger log = LoggerFactory.getLogger(SQLRelationConditionImpl.class);

    /**
     * @param data 过滤参数
     * @param rule 过滤规则
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>  过滤后返回的结果集
     * @MonthedName resultHandler 数据过滤方法
     * @Description 递归拼接sql的Where条件
     * @Date 2022/1/13 15:46
     **/
    @Override
    public String resultHandler(Map<String, Object> data, String rule) throws Exception {

        if (StringUtils.isBlank(rule)) {
            return "";
        }

        if (data == null) {
            throw new NullPointerException("校验字段参数为空");
        }

        out("============校验过程执行  开始====================================");

        //规则校验 当规则校验成功 返回true或false
        String sqlWhere = ruleTree(data,rule);

        out("============校验过程执行  结束====================================");

        return sqlWhere;
    }

    /**
     * @param rule
     * @return boolean
     * @MonthedName ruleTree
     * @Description 规则树处理。将规则数据转化为树型结构，循环节点，当节点条件都满足后，自上而下的进行判断，所有一级节点判断完成后
     * 返回结果
     * @Date 2022/1/13 17:29
     **/
    private String ruleTree(Map<String, Object> data, String rule) throws Exception {

        //转化规则树
        List<DataConditionStructureEntity> list = JSON.parseArray(rule, DataConditionStructureEntity.class);

        if (list == null || list.size() == 0) {
            throw new NullPointerException("规则为空");
        }

        StringBuffer sqlWhere = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {

            DataConditionStructureEntity item = list.get(i);

            out("执行 setMapRecord 方法   " + i);

            out("运算符 Operator  = " + item.getLogic());

            String where = "(" + groupHandler(data,item) + ")";

            if ("".equals(item.getLogic()) || item.getLogic() == null) {
                sqlWhere.append(where + " AND ");
            } else {
                sqlWhere.append(where + " " + item.getLogic() + " ");
            }

        }

        String resultWhere = whereTrim(sqlWhere);
        out("最终返回拼接条件：sqlWhere.toString() =================" + sqlWhere.toString());

        return resultWhere;
    }

    //去掉头尾的 AND 或 OR
    private String whereTrim(StringBuffer sqlWhere) {

        String where = sqlWhere.toString();

        if (where == null || where.equals("")) {
            return "";
        }

        int first = where.indexOf("(");
        int last = where.lastIndexOf(")");

        return where.substring(first,last+1);

    }

    /**
     * @param vo
     * @return void
     * @MonthedName groupHandler
     * @Description 递归
     * @Date 2022/1/13 17:50
     **/
    private String groupHandler(Map<String, Object> data,DataConditionStructureEntity vo) throws Exception {

//        out("规则递归开始 ================");
//        out("操作符 = " + vo.getOperator());
//        out("类型为 = " + vo.getType());
        //字段类型为FIELD时代表规则树为最末节点
        if (Constants.FIELD_TYPE.equals(vo.getType().toUpperCase())) {

            //数据比对
            String where = dataHandler(data,vo);
//            out("FIELD 判断结果返回 = " + where);

            return where;
        }

        List<DataConditionStructureEntity> list = vo.getChildren();

        String forWhere = null;

        for (DataConditionStructureEntity item : list) {

            String where = groupHandler(data,item);

            out("where========"+where+"   vo.getOperator()============="+vo.getOperator()+"     item.getOperator()==========="+item.getOperator());
            forWhere = fieldWhere(vo, where, forWhere);

            out("forWhere==============="+forWhere);
            out("递归当前的ID为 " + item.getFieldCode() + "  结束");

        }

        return "(" + forWhere + ")";

    }

    private String fieldWhere(DataConditionStructureEntity vo, String where, String forWhere) {

        if (where == null || "".equals(where)){
            return forWhere;
        }
        //组 操作符 AND
        if (Constants.GROUP_OPERATOR_AND.equals(vo.getLogic().toUpperCase())) {

            if (StringUtils.isNotBlank(forWhere)) {

                return forWhere + " and " + where;
            }
            return where;

        }

        //组 操作符 OR
        if (Constants.GROUP_OPERATOR_OR.equals(vo.getLogic().toUpperCase())) {

            if (StringUtils.isNotBlank(forWhere)) {

                return forWhere + " or " + where;
            }
            return where;

        }

        //组 操作符 为空 默认为 and
        if (Constants.GROUP_OPERATOR_.equals(vo.getLogic())) {

            if (StringUtils.isNotBlank(forWhere)) {

                return forWhere + " and " + where;
            }
            return where;

        }

        return "";

    }

    //处理实体数据
    private String dataHandler(Map<String, Object> data,DataConditionStructureEntity vo) throws Exception {

        out("类型为字段时才进入该方法");
        String operator = vo.getOperator();

        out("operator==="+operator);

        operator = ( operator == null || "".equals(operator) ) ? Constants.OPERATOR_EQ : operator.toUpperCase();

        AbsOperatorHandler operatorHandler = Constants.operatorMap.get(operator);

        if (operatorHandler == null) {
            throw new NullPointerException("规则JSON格式错误");
        }

        return operatorHandler.operatorWhereHandler(data , vo);

    }

    private void out(String outstr) {

        log.info("=================   " + outstr);
//        //System.out.println("=================   "+outstr);
    }

}
