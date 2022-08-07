package com.yyld.conair.commons.data.inter.impl;

import com.alibaba.fastjson.JSON;
import com.yyld.conair.commons.data.abs.AbstractOperator;
import com.yyld.conair.commons.data.abs.ext.*;
import com.yyld.conair.commons.data.constants.Constants;
import com.yyld.conair.commons.data.entity.RelationConditionVO;
import com.yyld.conair.commons.data.inter.SQLManyRelationCondition;
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
@Component
public class SQLManyRelationConditionImpl implements SQLManyRelationCondition {

    private static final Logger log = LoggerFactory.getLogger(SQLManyRelationConditionImpl.class);

    /**
     * @param data 过滤参数
     * @param rule 过滤规则
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>  过滤后返回的结果集
     * @MonthedName resultHandler 数据过滤方法
     * @Description 递归拼接sql的Where条件
     * @Date 2022/1/13 15:46
     **/
    @Override
    public String resultHandler(List<Map<String, Object>> data, String rule, String relType) throws Exception {

        if (StringUtils.isBlank(rule)) {
            return null;
        }

        if (data == null) {
            throw new NullPointerException("校验字段参数为空");
        }

//        out("============校验过程执行  开始====================================");

        //规则校验 当规则校验成功 返回true或false
        StringBuffer sqlWhere = new StringBuffer();

        if (relType == null || "".equals(relType)){
            relType = "OR";
        }
        if (!("or".equals(relType.toLowerCase()) || "and".equals(relType.toLowerCase()))) {
            relType = "OR";
        }

        for (Map<String,Object> paramMap : data){

            sqlWhere.append(ruleTree(paramMap , rule)+" "+relType+" ");

        }

//        out("============校验过程执行  结束====================================");

        String where = whereTrim(sqlWhere);
        return where;
    }

    /**
     *
     * @param paramMap
     * @param rule
     * @return boolean
     * @MonthedName ruleTree
     * @Description 规则树处理。将规则数据转化为树型结构，循环节点，当节点条件都满足后，自上而下的进行判断，所有一级节点判断完成后
     * 返回结果
     * @Date 2022/1/13 17:29
     **/
    private String ruleTree(Map<String, Object> paramMap, String rule) throws Exception {

        //转化规则树
        List<RelationConditionVO> list = JSON.parseArray(rule, RelationConditionVO.class);

        if (list == null || list.size() == 0) {
            throw new NullPointerException("关联关系规则为空");
        }

        StringBuffer sqlWhere = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {

            RelationConditionVO item = list.get(i);

//            out("执行 setMapRecord 方法   " + i);
//
//            out("运算符 Operator  = " + item.getOperator());

            String where = "(" + groupHandler(item,paramMap) + ")";

            if ("".equals(item.getOperator()) || item.getOperator() == null) {
                sqlWhere.append(where + " AND ");
            } else {
                sqlWhere.append(where + " " + item.getOperator() + " ");
            }

        }

        String where = whereTrim(sqlWhere);
//        out("最终返回拼接条件：sqlWhere.toString() =================" + sqlWhere.toString());

        return where;
    }

    //去掉头尾的 AND 或 OR
    private String whereTrim(StringBuffer sqlWhere) {

        String where = sqlWhere.toString();

        if (where == null || where.equals("")) {
            return null;
        }

        int first = where.indexOf("(");
        int last = where.lastIndexOf(")");

        sqlWhere = new StringBuffer();
        sqlWhere.append(where.substring(first,last+1));

        return sqlWhere.toString();
    }

    /**
     * @param vo
     * @param paramMap
     * @return void
     * @MonthedName groupHandler
     * @Description 递归
     * @Date 2022/1/13 17:50
     **/
    private String groupHandler(RelationConditionVO vo, Map<String, Object> paramMap) throws Exception {

//        out("规则递归开始 ================");
//        out("操作符 = " + vo.getOperator());
//        out("类型为 = " + vo.getType());
        //字段类型为FIELD时代表规则树为最末节点
        if (Constants.FIELD_TYPE.equals(vo.getType())) {

            //数据比对
            String where = dataHandler(vo,paramMap);
//            out("FIELD 判断结果返回 = " + where);

            return where;
        }

        List<RelationConditionVO> list = vo.getNextList();

        String forWhere = null;

        for (RelationConditionVO item : list) {

            String where = groupHandler(item, paramMap);

//            out("where========"+where+"   vo.getOperator()============="+vo.getOperator()+"     item.getOperator()==========="+item.getOperator());
            forWhere = fieldWhere(vo, where, forWhere);
//
//            out("forWhere==============="+forWhere);
//            out("递归当前的ID为 " + item.getId() + "  结束");

        }

        return "(" + forWhere + ")";

    }

    private String fieldWhere(RelationConditionVO vo, String where, String forWhere) {

        //组 操作符 AND
        if (Constants.GROUP_OPERATOR_AND.equals(vo.getOperator().toUpperCase())) {

            if (StringUtils.isNotBlank(forWhere)) {

                return forWhere + " and " + where;
            }
            return where;

        }

        //组 操作符 OR
        if (Constants.GROUP_OPERATOR_OR.equals(vo.getOperator().toUpperCase())) {

            if (StringUtils.isNotBlank(forWhere)) {

                return forWhere + " or " + where;
            }
            return where;

        }

        //组 操作符 为空 默认为 and
        if (Constants.GROUP_OPERATOR_.equals(vo.getOperator())) {

            if (StringUtils.isNotBlank(forWhere)) {

                return forWhere + " and " + where;
            }
            return where;

        }

        return "";

    }

    //处理实体数据
    private String dataHandler(RelationConditionVO vo, Map<String, Object> paramMap) throws Exception {

//        out("类型为字段时才进入该方法");
        String operator = vo.getOperator();

        AbstractOperator absOperator = null;

        //等于
        if (Constants.OPERATOR_EQ.equals(operator.toUpperCase())) {

            absOperator = new OperatorEQHanlder();

        }
        //不等于
        if (Constants.OPERATOR_NE.equals(operator.toUpperCase())) {

            absOperator = new OperatorNEHanlder();

        }
        //大于
        if (Constants.OPERATOR_GT.equals(operator.toUpperCase())) {

            absOperator = new OperatorGTHanlder();

        }
        //小于
        if (Constants.OPERATOR_LT.equals(operator.toUpperCase())) {

            absOperator = new OperatorLTHanlder();

        }
        //大于等于
        if (Constants.OPERATOR_GE.equals(operator.toLowerCase())) {

            absOperator = new OperatorGEHanlder();

        }
        //小于等于
        if (Constants.OPERATOR_LE.equals(operator.toUpperCase())) {

            absOperator = new OperatorLEHanlder();

        }

        //包含
        if (Constants.OPERATOR_BT.equals(operator.toUpperCase())) {

            absOperator = new OperatorBTHanlder();

        }

        if (absOperator == null) {
            throw new NullPointerException("错误：未知运算符");
        }

        return absOperator.analyzeAssociationConditions(vo,paramMap);

    }

    public static void main(String[] args) {

        // 开始时间
        long stime = System.currentTimeMillis();

        //测试数据 1    测试条件成立
        /*String rule = "[\n" +
                "\t{\n" +
                "    \"type\": \"GROUP\",\n" +
                "    \"operator\": \"OR\",\n" +
                "    \"sort\": \"0\",\n" +
                "    \"name\": \"或\",\n" +
                "    \"parentId\": \"0\",\n" +
                "    \"id\": \"id_3\",\n" +
                "    \"sourceFieldName\": \"\",\n" +
                "    \"sourceField\": \"\",\n" +
                "    \"sourceFieldValue\": \"\",\n" +
                "    \"sourceType\": \"\",\n" +
                "    \"targetField\": \"\",\n" +
                "    \"targetFieldName\": \"\",\n" +
                "    \"targetType\": \"\",\n" +
                "    \"targetFieldValue\": \"\",\n" +
                "    \"nextList\": [\n" +
                "      {\n" +
                "        \"type\": \"FIELD\",\n" +
                "        \"sort\": \"0\",\n" +
                "        \"operator\": \"eq\",\n" +
                "        \"name\": \"\",\n" +
                "        \"sourceFieldName\": \"test字段\",\n" +
                "        \"sourceField\": \"test\",\n" +
                "        \"sourceFieldValue\": \"abc\",\n" +
                "        \"sourceType\": \"L\",\n" +
                "        \"targetField\": \"a\",\n" +
                "        \"targetFieldName\": \"A字段\",\n" +
                "        \"targetType\": \"M\",\n" +
                "        \"targetFieldValue\": \"100\",\n" +
                "        \"parentId\": \"id_3\",\n" +
                "        \"id\": \"id_3_1\"\n" +
                "      },{\n" +
                "        \"type\": \"FIELD\",\n" +
                "        \"sort\": \"0\",\n" +
                "        \"operator\": \"eq\",\n" +
                "        \"name\": \"\",\n" +
                "        \"sourceFieldName\": \"test字段\",\n" +
                "        \"sourceField\": \"test\",\n" +
                "        \"sourceFieldValue\": \"abc\",\n" +
                "        \"sourceType\": \"L\",\n" +
                "        \"targetField\": \"a\",\n" +
                "        \"targetFieldName\": \"A字段\",\n" +
                "        \"targetType\": \"M\",\n" +
                "        \"targetFieldValue\": \"100\",\n" +
                "        \"parentId\": \"id_3\",\n" +
                "        \"id\": \"id_3_2\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";*/
        //测试数据2    条件不符
        /*String rule = "[\n" +
                "\t{\n" +
                "    \"type\": \"GROUP\",\n" +
                "    \"operator\": \"AND\",\n" +
                "    \"sort\": \"0\",\n" +
                "    \"name\": \"或\",\n" +
                "    \"parentId\": \"0\",\n" +
                "    \"id\": \"id_3\",\n" +
                "    \"sourceFieldName\": \"\",\n" +
                "    \"sourceField\": \"\",\n" +
                "    \"sourceFieldValue\": \"\",\n" +
                "    \"sourceType\": \"\",\n" +
                "    \"targetField\": \"\",\n" +
                "    \"targetFieldName\": \"\",\n" +
                "    \"targetType\": \"\",\n" +
                "    \"targetFieldValue\": \"\",\n" +
                "    \"nextList\": [\n" +
                "      {\n" +
                "        \"type\": \"FIELD\",\n" +
                "        \"sort\": \"0\",\n" +
                "        \"operator\": \"eq\",\n" +
                "        \"name\": \"\",\n" +
                "        \"sourceFieldName\": \"test字段\",\n" +
                "        \"sourceField\": \"test\",\n" +
                "        \"sourceFieldValue\": \"abc\",\n" +
                "        \"sourceType\": \"L\",\n" +
                "        \"targetField\": \"a\",\n" +
                "        \"targetFieldName\": \"A字段\",\n" +
                "        \"targetType\": \"M\",\n" +
                "        \"targetFieldValue\": \"100\",\n" +
                "        \"parentId\": \"id_3\",\n" +
                "        \"id\": \"id_3_1\"\n" +
                "      },{\n" +
                "        \"type\": \"FIELD\",\n" +
                "        \"sort\": \"0\",\n" +
                "        \"operator\": \"eq\",\n" +
                "        \"name\": \"\",\n" +
                "        \"sourceFieldName\": \"test字段\",\n" +
                "        \"sourceField\": \"test\",\n" +
                "        \"sourceFieldValue\": \"abc1\",\n" +
                "        \"sourceType\": \"L\",\n" +
                "        \"targetField\": \"a\",\n" +
                "        \"targetFieldName\": \"A字段\",\n" +
                "        \"targetType\": \"M\",\n" +
                "        \"targetFieldValue\": \"1001\",\n" +
                "        \"parentId\": \"id_3\",\n" +
                "        \"id\": \"id_3_2\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";*/
        //测试数据3
        /*String rule = "[\n" +
                "  {\n" +
                "    \"type\": \"GROUP\",\n" +
                "    \"operator\": \"AND\",\n" +
                "    \"sort\": \"0\",\n" +
                "    \"name\": \"或\",\n" +
                "    \"parentId\": \"0\",\n" +
                "    \"id\": \"id_3\",\n" +
                "    \"sourceFieldName\": \"\",\n" +
                "    \"sourceField\": \"\",\n" +
                "    \"sourceFieldValue\": \"\",\n" +
                "    \"sourceType\": \"\",\n" +
                "    \"targetField\": \"\",\n" +
                "    \"targetFieldName\": \"\",\n" +
                "    \"targetType\": \"\",\n" +
                "    \"targetFieldValue\": \"\",\n" +
                "    \"nextList\": [\n" +
                "      {\n" +
                "        \"type\": \"FIELD\",\n" +
                "        \"sort\": \"0\",\n" +
                "        \"operator\": \"eq\",\n" +
                "        \"name\": \"\",\n" +
                "        \"sourceFieldName\": \"test字段\",\n" +
                "        \"sourceField\": \"test\",\n" +
                "        \"sourceFieldValue\": \"abc\",\n" +
                "        \"sourceType\": \"L\",\n" +
                "        \"targetField\": \"a\",\n" +
                "        \"targetFieldName\": \"A字段\",\n" +
                "        \"targetType\": \"M\",\n" +
                "        \"targetFieldValue\": \"100\",\n" +
                "        \"parentId\": \"id_3\",\n" +
                "        \"id\": \"id_3_1\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";*/
        /*String rule = "[\n" +
                "  {\n" +
                "    \"type\": \"GROUP\",\n" +
                "    \"operator\": \"OR\",\n" +
                "    \"sort\": \"0\",\n" +
                "    \"name\": \"或\",\n" +
                "    \"parentId\": \"0\",\n" +
                "    \"id\": \"id_1\",\n" +
                "    \"sourceFieldName\": \"\",\n" +
                "    \"sourceField\": \"\",\n" +
                "    \"sourceFieldValue\": \"\",\n" +
                "    \"sourceType\": \"\",\n" +
                "    \"targetField\": \"\",\n" +
                "    \"targetFieldName\": \"\",\n" +
                "    \"targetType\": \"\",\n" +
                "    \"targetFieldValue\": \"\",\n" +
                "    \"nextList\": [\n" +
                "      {\n" +
                "        \"type\": \"GROUP\",\n" +
                "        \"operator\": \"AND\",\n" +
                "        \"sort\": \"0\",\n" +
                "        \"name\": \"且\",\n" +
                "        \"parentId\": \"id_1\",\n" +
                "        \"id\": \"id_1_1\",\n" +
                "        \"sourceFieldName\": \"\",\n" +
                "        \"sourceField\": \"\",\n" +
                "        \"sourceFieldValue\": \"\",\n" +
                "        \"sourceType\": \"\",\n" +
                "        \"targetField\": \"\",\n" +
                "        \"targetFieldName\": \"\",\n" +
                "        \"targetType\": \"\",\n" +
                "        \"targetFieldValue\": \"\",\n" +
                "        \"nextList\": [\n" +
                "          {\n" +
                "            \"type\": \"GROUP\",\n" +
                "            \"operator\": \"AND\",\n" +
                "            \"sort\": \"0\",\n" +
                "            \"name\": \"且\",\n" +
                "            \"parentId\": \"id_1_1\",\n" +
                "            \"id\": \"id_1_1_1\",\n" +
                "            \"sourceFieldName\": \"\",\n" +
                "            \"sourceField\": \"\",\n" +
                "            \"sourceFieldValue\": \"\",\n" +
                "            \"sourceType\": \"\",\n" +
                "            \"targetField\": \"\",\n" +
                "            \"targetFieldName\": \"\",\n" +
                "            \"targetType\": \"\",\n" +
                "            \"targetFieldValue\": \"\",\n" +
                "            \"nextList\": [\n" +
                "              {\n" +
                "                \"type\": \"FIELD\",\n" +
                "                \"sort\": \"0\",\n" +
                "                \"operator\": \"eq\",\n" +
                "                \"name\": \"\",\n" +
                "                \"sourceFieldName\": \"总计\",\n" +
                "                \"sourceField\": \"total\",\n" +
                "                \"sourceFieldValue\": \"10000\",\n" +
                "                \"sourceType\": \"L\",\n" +
                "                \"targetField\": \"b\",\n" +
                "                \"targetFieldName\": \"B字段\",\n" +
                "                \"targetType\": \"M\",\n" +
                "                \"targetFieldValue\": \"1000\",\n" +
                "                \"parentId\": \"id_1_1_1\",\n" +
                "                \"id\": \"id_1_1_1_1\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"type\": \"FIELD\",\n" +
                "                \"sort\": \"1\",\n" +
                "                \"operator\": \"le\",\n" +
                "                \"name\": \"\",\n" +
                "                \"sourceFieldName\": \"C字段\",\n" +
                "                \"sourceField\": \"c\",\n" +
                "                \"sourceFieldValue\": \"2020-10-22\",\n" +
                "                \"sourceType\": \"M\",\n" +
                "                \"targetField\": \"b\",\n" +
                "                \"targetFieldName\": \"B字段\",\n" +
                "                \"targetType\": \"M\",\n" +
                "                \"targetFieldValue\": \"100,1000\",\n" +
                "                \"parentId\": \"id_1_1_1\",\n" +
                "                \"id\": \"id_1_1_1_2\"\n" +
                "              }\n" +
                "            ]\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"GROUP\",\n" +
                "            \"operator\": \"AND\",\n" +
                "            \"sort\": \"0\",\n" +
                "            \"name\": \"且\",\n" +
                "            \"parentId\": \"id_1_1\",\n" +
                "            \"id\": \"id_1_1_2\",\n" +
                "            \"sourceFieldName\": \"\",\n" +
                "            \"sourceField\": \"\",\n" +
                "            \"sourceFieldValue\": \"\",\n" +
                "            \"sourceType\": \"\",\n" +
                "            \"targetField\": \"\",\n" +
                "            \"targetFieldName\": \"\",\n" +
                "            \"targetType\": \"\",\n" +
                "            \"targetFieldValue\": \"\",\n" +
                "            \"nextList\": [\n" +
                "              {\n" +
                "                \"type\": \"FIELD\",\n" +
                "                \"sort\": \"0\",\n" +
                "                \"operator\": \"eq\",\n" +
                "                \"name\": \"\",\n" +
                "                \"sourceFieldName\": \"总计\",\n" +
                "                \"sourceField\": \"total\",\n" +
                "                \"sourceFieldValue\": \"10000\",\n" +
                "                \"sourceType\": \"L\",\n" +
                "                \"targetField\": \"b\",\n" +
                "                \"targetFieldName\": \"B字段\",\n" +
                "                \"targetType\": \"M\",\n" +
                "                \"targetFieldValue\": \"1000\",\n" +
                "                \"parentId\": \"id_1_1_1\",\n" +
                "                \"id\": \"id_1_1_1_1\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"type\": \"FIELD\",\n" +
                "                \"sort\": \"1\",\n" +
                "                \"operator\": \"le\",\n" +
                "                \"name\": \"\",\n" +
                "                \"sourceFieldName\": \"test字段\",\n" +
                "                \"sourceField\": \"test\",\n" +
                "                \"sourceFieldValue\": \"abc\",\n" +
                "                \"sourceType\": \"L\",\n" +
                "                \"targetField\": \"a\",\n" +
                "                \"targetFieldName\": \"A字段\",\n" +
                "                \"targetType\": \"M\",\n" +
                "                \"targetFieldValue\": \"100\",\n" +
                "                \"parentId\": \"id_1_1_1\",\n" +
                "                \"id\": \"id_1_1_1_2\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"type\": \"GROUP\",\n" +
                "    \"operator\": \"OR\",\n" +
                "    \"sort\": \"0\",\n" +
                "    \"name\": \"或\",\n" +
                "    \"parentId\": \"0\",\n" +
                "    \"id\": \"id_2\",\n" +
                "    \"sourceFieldName\": \"\",\n" +
                "    \"sourceField\": \"\",\n" +
                "    \"sourceFieldValue\": \"\",\n" +
                "    \"sourceType\": \"\",\n" +
                "    \"targetField\": \"\",\n" +
                "    \"targetFieldName\": \"\",\n" +
                "    \"targetType\": \"\",\n" +
                "    \"targetFieldValue\": \"\",\n" +
                "    \"nextList\": [\n" +
                "      {\n" +
                "        \"type\": \"GROUP\",\n" +
                "        \"operator\": \"AND\",\n" +
                "        \"sort\": \"0\",\n" +
                "        \"name\": \"且\",\n" +
                "        \"parentId\": \"0\",\n" +
                "        \"id\": \"id_2_1\",\n" +
                "        \"sourceFieldName\": \"\",\n" +
                "        \"sourceField\": \"\",\n" +
                "        \"sourceFieldValue\": \"\",\n" +
                "        \"sourceType\": \"\",\n" +
                "        \"targetField\": \"\",\n" +
                "        \"targetFieldName\": \"\",\n" +
                "        \"targetType\": \"\",\n" +
                "        \"targetFieldValue\": \"\",\n" +
                "        \"nextList\": [\n" +
                "          {\n" +
                "            \"type\": \"FIELD\",\n" +
                "            \"sort\": \"0\",\n" +
                "            \"operator\": \"eq\",\n" +
                "            \"name\": \"\",\n" +
                "            \"sourceFieldName\": \"总计\",\n" +
                "            \"sourceField\": \"total\",\n" +
                "            \"sourceFieldValue\": \"10000\",\n" +
                "            \"sourceType\": \"L\",\n" +
                "            \"targetField\": \"b\",\n" +
                "            \"targetFieldName\": \"B字段\",\n" +
                "            \"targetType\": \"M\",\n" +
                "            \"targetFieldValue\": \"1000\",\n" +
                "            \"parentId\": \"id_2_1\",\n" +
                "            \"id\": \"id_2_1_1\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"FIELD\",\n" +
                "            \"sort\": \"1\",\n" +
                "            \"operator\": \"eq\",\n" +
                "            \"name\": \"\",\n" +
                "            \"sourceFieldName\": \"test字段\",\n" +
                "            \"sourceField\": \"test\",\n" +
                "            \"sourceFieldValue\": \"abc\",\n" +
                "            \"sourceType\": \"L\",\n" +
                "            \"targetField\": \"a\",\n" +
                "            \"targetFieldName\": \"A字段\",\n" +
                "            \"targetType\": \"M\",\n" +
                "            \"targetFieldValue\": \"100\",\n" +
                "            \"parentId\": \"id_2_1\",\n" +
                "            \"id\": \"id_2_1_2\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"GROUP\",\n" +
                "        \"operator\": \"AND\",\n" +
                "        \"sort\": \"0\",\n" +
                "        \"name\": \"且\",\n" +
                "        \"parentId\": \"0\",\n" +
                "        \"id\": \"id_2_2\",\n" +
                "        \"sourceFieldName\": \"\",\n" +
                "        \"sourceField\": \"\",\n" +
                "        \"sourceFieldValue\": \"\",\n" +
                "        \"sourceType\": \"\",\n" +
                "        \"targetField\": \"\",\n" +
                "        \"targetFieldName\": \"\",\n" +
                "        \"targetType\": \"\",\n" +
                "        \"targetFieldValue\": \"\",\n" +
                "        \"nextList\": [\n" +
                "          {\n" +
                "            \"type\": \"FIELD\",\n" +
                "            \"sort\": \"0\",\n" +
                "            \"operator\": \"eq\",\n" +
                "            \"name\": \"\",\n" +
                "            \"sourceFieldName\": \"test字段\",\n" +
                "            \"sourceField\": \"test\",\n" +
                "            \"sourceFieldValue\": \"abc\",\n" +
                "            \"sourceType\": \"L\",\n" +
                "            \"targetField\": \"a\",\n" +
                "            \"targetFieldName\": \"A字段\",\n" +
                "            \"targetType\": \"M\",\n" +
                "            \"targetFieldValue\": \"100\",\n" +
                "            \"parentId\": \"id_2_2\",\n" +
                "            \"id\": \"id_2_2_1\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"FIELD\",\n" +
                "            \"sort\": \"1\",\n" +
                "            \"operator\": \"eq\",\n" +
                "            \"name\": \"\",\n" +
                "            \"sourceFieldName\": \"test字段\",\n" +
                "            \"sourceField\": \"test\",\n" +
                "            \"sourceFieldValue\": \"abc\",\n" +
                "            \"sourceType\": \"L\",\n" +
                "            \"targetField\": \"a\",\n" +
                "            \"targetFieldName\": \"A字段\",\n" +
                "            \"targetType\": \"M\",\n" +
                "            \"targetFieldValue\": \"100\",\n" +
                "            \"parentId\": \"id_2_2\",\n" +
                "            \"id\": \"id_2_2_2\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"type\": \"GROUP\",\n" +
                "    \"operator\": \"OR\",\n" +
                "    \"sort\": \"0\",\n" +
                "    \"name\": \"或\",\n" +
                "    \"parentId\": \"0\",\n" +
                "    \"id\": \"id_3\",\n" +
                "    \"sourceFieldName\": \"\",\n" +
                "    \"sourceField\": \"\",\n" +
                "    \"sourceFieldValue\": \"\",\n" +
                "    \"sourceType\": \"\",\n" +
                "    \"targetField\": \"\",\n" +
                "    \"targetFieldName\": \"\",\n" +
                "    \"targetType\": \"\",\n" +
                "    \"targetFieldValue\": \"\",\n" +
                "    \"nextList\": [\n" +
                "      {\n" +
                "        \"type\": \"FIELD\",\n" +
                "        \"sort\": \"0\",\n" +
                "        \"operator\": \"eq\",\n" +
                "        \"name\": \"\",\n" +
                "        \"sourceFieldName\": \"test字段\",\n" +
                "        \"sourceField\": \"test\",\n" +
                "        \"sourceFieldValue\": \"abc\",\n" +
                "        \"sourceType\": \"L\",\n" +
                "        \"targetField\": \"a\",\n" +
                "        \"targetFieldName\": \"A字段\",\n" +
                "        \"targetType\": \"M\",\n" +
                "        \"targetFieldValue\": \"100\",\n" +
                "        \"parentId\": \"id_3\",\n" +
                "        \"id\": \"id_3_1\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";*/

        /*String rule = "[\n" +
                "  {\n" +
                "    \"type\": \"GROUP\",\n" +
                "    \"operator\": \"AND\",\n" +
                "    \"sort\": \"0\",\n" +
                "    \"name\": \"且\",\n" +
                "    \"parentId\": \"id_1_1\",\n" +
                "    \"id\": \"id_1_1_1\",\n" +
                "    \"sourceFieldName\": \"\",\n" +
                "    \"sourceField\": \"\",\n" +
                "    \"sourceFieldValue\": \"\",\n" +
                "    \"sourceType\": \"\",\n" +
                "    \"targetField\": \"\",\n" +
                "    \"targetFieldName\": \"\",\n" +
                "    \"targetType\": \"\",\n" +
                "    \"targetFieldValue\": \"\",\n" +
                "    \"nextList\": [\n" +
                "      {\n" +
                "        \"type\": \"FIELD\",\n" +
                "        \"sort\": \"0\",\n" +
                "        \"operator\": \"eq\",\n" +
                "        \"name\": \"\",\n" +
                "        \"sourceFieldName\": \"总计\",\n" +
                "        \"sourceField\": \"total\",\n" +
                "        \"sourceFieldValue\": \"10000\",\n" +
                "        \"sourceType\": \"L\",\n" +
                "        \"targetField\": \"b\",\n" +
                "        \"targetFieldName\": \"B字段\",\n" +
                "        \"targetType\": \"M\",\n" +
                "        \"targetFieldValue\": \"100,200\",\n" +
                "        \"parentId\": \"id_1_1_1\",\n" +
                "        \"id\": \"id_1_1_1_1\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"FIELD\",\n" +
                "        \"sort\": \"1\",\n" +
                "        \"operator\": \"ge\",\n" +
                "        \"name\": \"\",\n" +
                "        \"sourceFieldName\": \"C字段\",\n" +
                "        \"sourceField\": \"c\",\n" +
                "        \"sourceFieldValue\": \"2020-10-11 20:10:22\",\n" +
                "        \"sourceType\": \"M\",\n" +
                "        \"targetField\": \"b\",\n" +
                "        \"targetFieldName\": \"B字段\",\n" +
                "        \"targetType\": \"M\",\n" +
                "        \"targetFieldValue\": \"100,200\",\n" +
                "        \"parentId\": \"id_1_1_1\",\n" +
                "        \"id\": \"id_1_1_1_2\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";*/

        /*String rule = "[\n" +
                "  {\n" +
                "    \"type\": \"GROUP\",\n" +
                "    \"operator\": \"AND\",\n" +
                "    \"sort\": \"0\",\n" +
                "    \"name\": \"且\",\n" +
                "    \"parentId\": \"id_1_1\",\n" +
                "    \"id\": \"id_1_1_1\",\n" +
                "    \"sourceFieldName\": \"\",\n" +
                "    \"sourceField\": \"\",\n" +
                "    \"sourceFieldValue\": \"\",\n" +
                "    \"sourceType\": \"\",\n" +
                "    \"targetField\": \"\",\n" +
                "    \"targetFieldName\": \"\",\n" +
                "    \"targetType\": \"\",\n" +
                "    \"targetFieldValue\": \"\",\n" +
                "    \"nextList\": [\n" +
                "      {\n" +
                "        \"type\": \"FIELD\",\n" +
                "        \"sort\": \"0\",\n" +
                "        \"operator\": \"eq\",\n" +
                "        \"name\": \"\",\n" +
                "        \"sourceFieldName\": \"总计\",\n" +
                "        \"sourceField\": \"total\",\n" +
                "        \"sourceFieldValue\": \"10000\",\n" +
                "        \"sourceType\": \"L\",\n" +
                "        \"targetField\": \"b\",\n" +
                "        \"targetFieldName\": \"B字段\",\n" +
                "        \"targetType\": \"M\",\n" +
                "        \"targetFieldValue\": \"100,200\",\n" +
                "        \"parentId\": \"id_1_1_1\",\n" +
                "        \"id\": \"id_1_1_1_1\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"FIELD\",\n" +
                "        \"sort\": \"1\",\n" +
                "        \"operator\": \"ge\",\n" +
                "        \"name\": \"\",\n" +
                "        \"sourceFieldName\": \"C字段\",\n" +
                "        \"sourceField\": \"c\",\n" +
                "        \"sourceFieldValue\": \"2020-10-11 20:10:22\",\n" +
                "        \"sourceType\": \"M\",\n" +
                "        \"targetField\": \"b\",\n" +
                "        \"targetFieldName\": \"B字段\",\n" +
                "        \"targetType\": \"M\",\n" +
                "        \"targetFieldValue\": \"100,200\",\n" +
                "        \"parentId\": \"id_1_1_1\",\n" +
                "        \"id\": \"id_1_1_1_2\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"type\": \"GROUP\",\n" +
                "    \"operator\": \"OR\",\n" +
                "    \"sort\": \"0\",\n" +
                "    \"name\": \"且\",\n" +
                "    \"parentId\": \"id_1_1\",\n" +
                "    \"id\": \"id_1_1_2\",\n" +
                "    \"sourceFieldName\": \"\",\n" +
                "    \"sourceField\": \"\",\n" +
                "    \"sourceFieldValue\": \"\",\n" +
                "    \"sourceType\": \"\",\n" +
                "    \"targetField\": \"\",\n" +
                "    \"targetFieldName\": \"\",\n" +
                "    \"targetType\": \"\",\n" +
                "    \"targetFieldValue\": \"\",\n" +
                "    \"nextList\": [\n" +
                "      {\n" +
                "        \"type\": \"FIELD\",\n" +
                "        \"sort\": \"0\",\n" +
                "        \"operator\": \"ne\",\n" +
                "        \"name\": \"\",\n" +
                "        \"sourceFieldName\": \"总计\",\n" +
                "        \"sourceField\": \"total\",\n" +
                "        \"sourceFieldValue\": \"1000000\",\n" +
                "        \"sourceType\": \"L\",\n" +
                "        \"targetField\": \"b\",\n" +
                "        \"targetFieldName\": \"B字段\",\n" +
                "        \"targetType\": \"M\",\n" +
                "        \"targetFieldValue\": \"100,200\",\n" +
                "        \"parentId\": \"id_1_1_1\",\n" +
                "        \"id\": \"id_1_1_1_1\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"FIELD\",\n" +
                "        \"sort\": \"1\",\n" +
                "        \"operator\": \"ne\",\n" +
                "        \"name\": \"\",\n" +
                "        \"sourceFieldName\": \"C字段\",\n" +
                "        \"sourceField\": \"c\",\n" +
                "        \"sourceFieldValue\": \"2222-02-11 16:11:11\",\n" +
                "        \"sourceType\": \"M\",\n" +
                "        \"targetField\": \"b\",\n" +
                "        \"targetFieldName\": \"B字段\",\n" +
                "        \"targetType\": \"M\",\n" +
                "        \"targetFieldValue\": \"100,200\",\n" +
                "        \"parentId\": \"id_1_1_1\",\n" +
                "        \"id\": \"id_1_1_1_2\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"FIELD\",\n" +
                "        \"sort\": \"1\",\n" +
                "        \"operator\": \"ne\",\n" +
                "        \"name\": \"\",\n" +
                "        \"sourceFieldName\": \"C字段\",\n" +
                "        \"sourceField\": \"c\",\n" +
                "        \"sourceFieldValue\": \"2222-02-11 16:11:11\",\n" +
                "        \"sourceType\": \"M\",\n" +
                "        \"targetField\": \"b\",\n" +
                "        \"targetFieldName\": \"B字段\",\n" +
                "        \"targetType\": \"M\",\n" +
                "        \"targetFieldValue\": \"100,200\",\n" +
                "        \"parentId\": \"id_1_1_1\",\n" +
                "        \"id\": \"id_1_1_1_2\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";*/

        /*String rule = "[\n" +
                "\t{\n" +
                "\t\t\"type\": \"GROUP\",\n" +
                "\t\t\"operator\": \"AND\",\n" +
                "\t\t\"sort\": \"0\",\n" +
                "\t\t\"name\": \"或\",\n" +
                "\t\t\"parentId\": \"0\",\n" +
                "\t\t\"id\": \"id_1\",\n" +
                "\t\t\"sourceFieldName\": \"\",\n" +
                "\t\t\"sourceField\": \"\",\n" +
                "\t\t\"sourceFieldValue\": \"\",\n" +
                "\t\t\"sourceType\": \"\",\n" +
                "\t\t\"targetField\": \"\",\n" +
                "\t\t\"targetFieldName\": \"\",\n" +
                "\t\t\"targetType\": \"\",\n" +
                "\t\t\"targetFieldValue\": \"\",\n" +
                "\t\t\"nextList\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"type\": \"GROUP\",\n" +
                "\t\t\t\t\"operator\": \"AND\",\n" +
                "\t\t\t\t\"sort\": \"0\",\n" +
                "\t\t\t\t\"name\": \"且\",\n" +
                "\t\t\t\t\"parentId\": \"id_1\",\n" +
                "\t\t\t\t\"id\": \"id_1_1\",\n" +
                "\t\t\t\t\"sourceFieldName\": \"\",\n" +
                "\t\t\t\t\"sourceField\": \"\",\n" +
                "\t\t\t\t\"sourceFieldValue\": \"\",\n" +
                "\t\t\t\t\"sourceType\": \"\",\n" +
                "\t\t\t\t\"targetField\": \"\",\n" +
                "\t\t\t\t\"targetFieldName\": \"\",\n" +
                "\t\t\t\t\"targetType\": \"\",\n" +
                "\t\t\t\t\"targetFieldValue\": \"\",\n" +
                "\t\t\t\t\"nextList\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"type\": \"GROUP\",\n" +
                "\t\t\t\t\t\t\"operator\": \"AND\",\n" +
                "\t\t\t\t\t\t\"sort\": \"0\",\n" +
                "\t\t\t\t\t\t\"name\": \"且\",\n" +
                "\t\t\t\t\t\t\"parentId\": \"id_1_1\",\n" +
                "\t\t\t\t\t\t\"id\": \"id_1_1_1\",\n" +
                "\t\t\t\t\t\t\"sourceFieldName\": \"\",\n" +
                "\t\t\t\t\t\t\"sourceField\": \"\",\n" +
                "\t\t\t\t\t\t\"sourceFieldValue\": \"\",\n" +
                "\t\t\t\t\t\t\"sourceType\": \"\",\n" +
                "\t\t\t\t\t\t\"targetField\": \"\",\n" +
                "\t\t\t\t\t\t\"targetFieldName\": \"\",\n" +
                "\t\t\t\t\t\t\"targetType\": \"\",\n" +
                "\t\t\t\t\t\t\"targetFieldValue\": \"\",\n" +
                "\t\t\t\t\t\t\"nextList\": [\n" +
                "\t\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\t\"type\": \"FIELD\",\n" +
                "\t\t\t\t\t\t\t\t\"sort\": \"0\",\n" +
                "\t\t\t\t\t\t\t\t\"operator\": \"eq\",\n" +
                "\t\t\t\t\t\t\t\t\"name\": \"\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceFieldName\": \"总计\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceField\": \"total\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceFieldValue\": \"10000\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceType\": \"L\",\n" +
                "\t\t\t\t\t\t\t\t\"targetField\": \"b\",\n" +
                "\t\t\t\t\t\t\t\t\"targetFieldName\": \"B字段\",\n" +
                "\t\t\t\t\t\t\t\t\"targetType\": \"M\",\n" +
                "\t\t\t\t\t\t\t\t\"targetFieldValue\": \"1000\",\n" +
                "\t\t\t\t\t\t\t\t\"parentId\": \"id_1_1_1\",\n" +
                "\t\t\t\t\t\t\t\t\"id\": \"id_1_1_1_1\"\n" +
                "\t\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\t\"type\": \"FIELD\",\n" +
                "\t\t\t\t\t\t\t\t\"sort\": \"1\",\n" +
                "\t\t\t\t\t\t\t\t\"operator\": \"le\",\n" +
                "\t\t\t\t\t\t\t\t\"name\": \"\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceFieldName\": \"C字段\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceField\": \"c\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceFieldValue\": \"2220-10-22 12:11:22\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceType\": \"M\",\n" +
                "\t\t\t\t\t\t\t\t\"targetField\": \"b\",\n" +
                "\t\t\t\t\t\t\t\t\"targetFieldName\": \"B字段\",\n" +
                "\t\t\t\t\t\t\t\t\"targetType\": \"M\",\n" +
                "\t\t\t\t\t\t\t\t\"targetFieldValue\": \"100,1000\",\n" +
                "\t\t\t\t\t\t\t\t\"parentId\": \"id_1_1_1\",\n" +
                "\t\t\t\t\t\t\t\t\"id\": \"id_1_1_1_2\"\n" +
                "\t\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t\t]\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"type\": \"GROUP\",\n" +
                "\t\t\t\t\t\t\"operator\": \"AND\",\n" +
                "\t\t\t\t\t\t\"sort\": \"0\",\n" +
                "\t\t\t\t\t\t\"name\": \"且\",\n" +
                "\t\t\t\t\t\t\"parentId\": \"id_1_1\",\n" +
                "\t\t\t\t\t\t\"id\": \"id_1_1_2\",\n" +
                "\t\t\t\t\t\t\"sourceFieldName\": \"\",\n" +
                "\t\t\t\t\t\t\"sourceField\": \"\",\n" +
                "\t\t\t\t\t\t\"sourceFieldValue\": \"\",\n" +
                "\t\t\t\t\t\t\"sourceType\": \"\",\n" +
                "\t\t\t\t\t\t\"targetField\": \"\",\n" +
                "\t\t\t\t\t\t\"targetFieldName\": \"\",\n" +
                "\t\t\t\t\t\t\"targetType\": \"\",\n" +
                "\t\t\t\t\t\t\"targetFieldValue\": \"\",\n" +
                "\t\t\t\t\t\t\"nextList\": [\n" +
                "\t\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\t\"type\": \"FIELD\",\n" +
                "\t\t\t\t\t\t\t\t\"sort\": \"0\",\n" +
                "\t\t\t\t\t\t\t\t\"operator\": \"eq\",\n" +
                "\t\t\t\t\t\t\t\t\"name\": \"\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceFieldName\": \"总计\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceField\": \"total\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceFieldValue\": \"10000\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceType\": \"L\",\n" +
                "\t\t\t\t\t\t\t\t\"targetField\": \"b\",\n" +
                "\t\t\t\t\t\t\t\t\"targetFieldName\": \"B字段\",\n" +
                "\t\t\t\t\t\t\t\t\"targetType\": \"M\",\n" +
                "\t\t\t\t\t\t\t\t\"targetFieldValue\": \"1000\",\n" +
                "\t\t\t\t\t\t\t\t\"parentId\": \"id_1_1_1\",\n" +
                "\t\t\t\t\t\t\t\t\"id\": \"id_1_1_1_1\"\n" +
                "\t\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\t\"type\": \"FIELD\",\n" +
                "\t\t\t\t\t\t\t\t\"sort\": \"1\",\n" +
                "\t\t\t\t\t\t\t\t\"operator\": \"le\",\n" +
                "\t\t\t\t\t\t\t\t\"name\": \"\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceFieldName\": \"test字段\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceField\": \"test\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceFieldValue\": \"abc\",\n" +
                "\t\t\t\t\t\t\t\t\"sourceType\": \"L\",\n" +
                "\t\t\t\t\t\t\t\t\"targetField\": \"a\",\n" +
                "\t\t\t\t\t\t\t\t\"targetFieldName\": \"A字段\",\n" +
                "\t\t\t\t\t\t\t\t\"targetType\": \"M\",\n" +
                "\t\t\t\t\t\t\t\t\"targetFieldValue\": \"100\",\n" +
                "\t\t\t\t\t\t\t\t\"parentId\": \"id_1_1_1\",\n" +
                "\t\t\t\t\t\t\t\t\"id\": \"id_1_1_1_2\"\n" +
                "\t\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t\t]\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t]\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"type\": \"GROUP\",\n" +
                "\t\t\"operator\": \"OR\",\n" +
                "\t\t\"sort\": \"0\",\n" +
                "\t\t\"name\": \"或\",\n" +
                "\t\t\"parentId\": \"0\",\n" +
                "\t\t\"id\": \"id_2\",\n" +
                "\t\t\"sourceFieldName\": \"\",\n" +
                "\t\t\"sourceField\": \"\",\n" +
                "\t\t\"sourceFieldValue\": \"\",\n" +
                "\t\t\"sourceType\": \"\",\n" +
                "\t\t\"targetField\": \"\",\n" +
                "\t\t\"targetFieldName\": \"\",\n" +
                "\t\t\"targetType\": \"\",\n" +
                "\t\t\"targetFieldValue\": \"\",\n" +
                "\t\t\"nextList\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"type\": \"GROUP\",\n" +
                "\t\t\t\t\"operator\": \"AND\",\n" +
                "\t\t\t\t\"sort\": \"0\",\n" +
                "\t\t\t\t\"name\": \"且\",\n" +
                "\t\t\t\t\"parentId\": \"0\",\n" +
                "\t\t\t\t\"id\": \"id_2_1\",\n" +
                "\t\t\t\t\"sourceFieldName\": \"\",\n" +
                "\t\t\t\t\"sourceField\": \"\",\n" +
                "\t\t\t\t\"sourceFieldValue\": \"\",\n" +
                "\t\t\t\t\"sourceType\": \"\",\n" +
                "\t\t\t\t\"targetField\": \"\",\n" +
                "\t\t\t\t\"targetFieldName\": \"\",\n" +
                "\t\t\t\t\"targetType\": \"\",\n" +
                "\t\t\t\t\"targetFieldValue\": \"\",\n" +
                "\t\t\t\t\"nextList\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"type\": \"FIELD\",\n" +
                "\t\t\t\t\t\t\"sort\": \"0\",\n" +
                "\t\t\t\t\t\t\"operator\": \"eq\",\n" +
                "\t\t\t\t\t\t\"name\": \"\",\n" +
                "\t\t\t\t\t\t\"sourceFieldName\": \"总计\",\n" +
                "\t\t\t\t\t\t\"sourceField\": \"total\",\n" +
                "\t\t\t\t\t\t\"sourceFieldValue\": \"10000\",\n" +
                "\t\t\t\t\t\t\"sourceType\": \"L\",\n" +
                "\t\t\t\t\t\t\"targetField\": \"b\",\n" +
                "\t\t\t\t\t\t\"targetFieldName\": \"B字段\",\n" +
                "\t\t\t\t\t\t\"targetType\": \"M\",\n" +
                "\t\t\t\t\t\t\"targetFieldValue\": \"1000\",\n" +
                "\t\t\t\t\t\t\"parentId\": \"id_2_1\",\n" +
                "\t\t\t\t\t\t\"id\": \"id_2_1_1\"\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"type\": \"FIELD\",\n" +
                "\t\t\t\t\t\t\"sort\": \"1\",\n" +
                "\t\t\t\t\t\t\"operator\": \"eq\",\n" +
                "\t\t\t\t\t\t\"name\": \"\",\n" +
                "\t\t\t\t\t\t\"sourceFieldName\": \"test字段\",\n" +
                "\t\t\t\t\t\t\"sourceField\": \"test\",\n" +
                "\t\t\t\t\t\t\"sourceFieldValue\": \"abc\",\n" +
                "\t\t\t\t\t\t\"sourceType\": \"L\",\n" +
                "\t\t\t\t\t\t\"targetField\": \"a\",\n" +
                "\t\t\t\t\t\t\"targetFieldName\": \"A字段\",\n" +
                "\t\t\t\t\t\t\"targetType\": \"M\",\n" +
                "\t\t\t\t\t\t\"targetFieldValue\": \"100\",\n" +
                "\t\t\t\t\t\t\"parentId\": \"id_2_1\",\n" +
                "\t\t\t\t\t\t\"id\": \"id_2_1_2\"\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"type\": \"GROUP\",\n" +
                "\t\t\t\t\"operator\": \"AND\",\n" +
                "\t\t\t\t\"sort\": \"0\",\n" +
                "\t\t\t\t\"name\": \"且\",\n" +
                "\t\t\t\t\"parentId\": \"0\",\n" +
                "\t\t\t\t\"id\": \"id_2_2\",\n" +
                "\t\t\t\t\"sourceFieldName\": \"\",\n" +
                "\t\t\t\t\"sourceField\": \"\",\n" +
                "\t\t\t\t\"sourceFieldValue\": \"\",\n" +
                "\t\t\t\t\"sourceType\": \"\",\n" +
                "\t\t\t\t\"targetField\": \"\",\n" +
                "\t\t\t\t\"targetFieldName\": \"\",\n" +
                "\t\t\t\t\"targetType\": \"\",\n" +
                "\t\t\t\t\"targetFieldValue\": \"\",\n" +
                "\t\t\t\t\"nextList\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"type\": \"FIELD\",\n" +
                "\t\t\t\t\t\t\"sort\": \"0\",\n" +
                "\t\t\t\t\t\t\"operator\": \"eq\",\n" +
                "\t\t\t\t\t\t\"name\": \"\",\n" +
                "\t\t\t\t\t\t\"sourceFieldName\": \"test字段\",\n" +
                "\t\t\t\t\t\t\"sourceField\": \"test\",\n" +
                "\t\t\t\t\t\t\"sourceFieldValue\": \"abc\",\n" +
                "\t\t\t\t\t\t\"sourceType\": \"L\",\n" +
                "\t\t\t\t\t\t\"targetField\": \"a\",\n" +
                "\t\t\t\t\t\t\"targetFieldName\": \"A字段\",\n" +
                "\t\t\t\t\t\t\"targetType\": \"M\",\n" +
                "\t\t\t\t\t\t\"targetFieldValue\": \"100\",\n" +
                "\t\t\t\t\t\t\"parentId\": \"id_2_2\",\n" +
                "\t\t\t\t\t\t\"id\": \"id_2_2_1\"\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"type\": \"FIELD\",\n" +
                "\t\t\t\t\t\t\"sort\": \"1\",\n" +
                "\t\t\t\t\t\t\"operator\": \"eq\",\n" +
                "\t\t\t\t\t\t\"name\": \"\",\n" +
                "\t\t\t\t\t\t\"sourceFieldName\": \"test字段\",\n" +
                "\t\t\t\t\t\t\"sourceField\": \"test\",\n" +
                "\t\t\t\t\t\t\"sourceFieldValue\": \"abc\",\n" +
                "\t\t\t\t\t\t\"sourceType\": \"L\",\n" +
                "\t\t\t\t\t\t\"targetField\": \"a\",\n" +
                "\t\t\t\t\t\t\"targetFieldName\": \"A字段\",\n" +
                "\t\t\t\t\t\t\"targetType\": \"M\",\n" +
                "\t\t\t\t\t\t\"targetFieldValue\": \"100\",\n" +
                "\t\t\t\t\t\t\"parentId\": \"id_2_2\",\n" +
                "\t\t\t\t\t\t\"id\": \"id_2_2_2\"\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t]\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"type\": \"GROUP\",\n" +
                "\t\t\"operator\": \"AND\",\n" +
                "\t\t\"sort\": \"0\",\n" +
                "\t\t\"name\": \"或\",\n" +
                "\t\t\"parentId\": \"0\",\n" +
                "\t\t\"id\": \"id_3\",\n" +
                "\t\t\"sourceFieldName\": \"\",\n" +
                "\t\t\"sourceField\": \"\",\n" +
                "\t\t\"sourceFieldValue\": \"\",\n" +
                "\t\t\"sourceType\": \"\",\n" +
                "\t\t\"targetField\": \"\",\n" +
                "\t\t\"targetFieldName\": \"\",\n" +
                "\t\t\"targetType\": \"\",\n" +
                "\t\t\"targetFieldValue\": \"\",\n" +
                "\t\t\"nextList\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"type\": \"FIELD\",\n" +
                "\t\t\t\t\"sort\": \"0\",\n" +
                "\t\t\t\t\"operator\": \"eq\",\n" +
                "\t\t\t\t\"name\": \"\",\n" +
                "\t\t\t\t\"sourceFieldName\": \"test字段\",\n" +
                "\t\t\t\t\"sourceField\": \"test\",\n" +
                "\t\t\t\t\"sourceFieldValue\": \"abc\",\n" +
                "\t\t\t\t\"sourceType\": \"L\",\n" +
                "\t\t\t\t\"targetField\": \"a\",\n" +
                "\t\t\t\t\"targetFieldName\": \"A字段\",\n" +
                "\t\t\t\t\"targetType\": \"M\",\n" +
                "\t\t\t\t\"targetFieldValue\": \"100,200\",\n" +
                "\t\t\t\t\"parentId\": \"id_3\",\n" +
                "\t\t\t\t\"id\": \"id_3_1\"\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "]";*/

        String rule = "[\n" +
                "\t{\n" +
                "\t\t\"type\": \"GROUP\",\n" +
                "\t\t\"operator\": \"AND\",\n" +
                "\t\t\"sort\": \"0\",\n" +
                "\t\t\"name\": \"且\",\n" +
                "\t\t\"parentId\": \"id_1_1\",\n" +
                "\t\t\"id\": \"id_1_1_1\",\n" +
                "\t\t\"sourceFieldName\": \"\",\n" +
                "\t\t\"sourceField\": \"\",\n" +
                "\t\t\"sourceFieldValue\": \"\",\n" +
                "\t\t\"sourceType\": \"\",\n" +
                "\t\t\"targetField\": \"\",\n" +
                "\t\t\"targetFieldName\": \"\",\n" +
                "\t\t\"targetType\": \"\",\n" +
                "\t\t\"targetFieldValue\": \"\",\n" +
                "\t\t\"nextList\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"type\": \"FIELD\",\n" +
                "\t\t\t\t\"sort\": \"0\",\n" +
                "\t\t\t\t\"operator\": \"eq\",\n" +
                "\t\t\t\t\"name\": \"\",\n" +
                "\t\t\t\t\"sourceFieldName\": \"公司代码\",\n" +
                "\t\t\t\t\"sourceField\": \"BUKRS\",\n" +
                "\t\t\t\t\"sourceFieldValue\": \"\",\n" +
                "\t\t\t\t\"sourceType\": \"V\",\n" +
                "\t\t\t\t\"targetField\": \"BUKRS\",\n" +
                "\t\t\t\t\"targetFieldName\": \"公司代码\",\n" +
                "\t\t\t\t\"targetType\": \"V\",\n" +
                "\t\t\t\t\"targetFieldValue\": \"\",\n" +
                "\t\t\t\t\"parentId\": \"id_1_1_1\",\n" +
                "\t\t\t\t\"id\": \"id_1_1_1_1\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"type\": \"FIELD\",\n" +
                "\t\t\t\t\"sort\": \"1\",\n" +
                "\t\t\t\t\"operator\": \"eq\",\n" +
                "\t\t\t\t\"name\": \"\",\n" +
                "\t\t\t\t\"sourceFieldName\": \"成本中心\",\n" +
                "\t\t\t\t\"sourceField\": \"CCC\",\n" +
                "\t\t\t\t\"sourceFieldValue\": \"\",\n" +
                "\t\t\t\t\"sourceType\": \"V\",\n" +
                "\t\t\t\t\"targetField\": \"KOSTL\",\n" +
                "\t\t\t\t\"targetFieldName\": \"成本中心\",\n" +
                "\t\t\t\t\"targetType\": \"V\",\n" +
                "\t\t\t\t\"targetFieldValue\": \"\",\n" +
                "\t\t\t\t\"parentId\": \"id_1_1_1\",\n" +
                "\t\t\t\t\"id\": \"id_1_1_1_2\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"type\": \"FIELD\",\n" +
                "\t\t\t\t\"sort\": \"1\",\n" +
                "\t\t\t\t\"operator\": \"eq\",\n" +
                "\t\t\t\t\"name\": \"\",\n" +
                "\t\t\t\t\"sourceFieldName\": \"利润中心\",\n" +
                "\t\t\t\t\"sourceField\": \"PRCTR\",\n" +
                "\t\t\t\t\"sourceFieldValue\": \"\",\n" +
                "\t\t\t\t\"sourceType\": \"V\",\n" +
                "\t\t\t\t\"targetField\": \"PRCTR\",\n" +
                "\t\t\t\t\"targetFieldName\": \"利润中心\",\n" +
                "\t\t\t\t\"targetType\": \"V\",\n" +
                "\t\t\t\t\"targetFieldValue\": \"\",\n" +
                "\t\t\t\t\"parentId\": \"id_1_1_1\",\n" +
                "\t\t\t\t\"id\": \"id_1_1_1_3\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"type\": \"FIELD\",\n" +
                "\t\t\t\t\"sort\": \"1\",\n" +
                "\t\t\t\t\"operator\": \"BT\",\n" +
                "\t\t\t\t\"name\": \"\",\n" +
                "\t\t\t\t\"sourceFieldName\": \"申请人ID\",\n" +
                "\t\t\t\t\"sourceField\": \"USERNO\",\n" +
                "\t\t\t\t\"sourceFieldValue\": \"1.1\",\n" +
                "\t\t\t\t\"sourceType\": \"L\",\n" +
                "\t\t\t\t\"targetField\": \"RE_USERID\",\n" +
                "\t\t\t\t\"targetFieldName\": \"申请人ID\",\n" +
                "\t\t\t\t\"targetType\": \"V\",\n" +
                "\t\t\t\t\"targetFieldValue\": \"1.11,122\",\n" +
                "\t\t\t\t\"parentId\": \"id_1_1_1\",\n" +
                "\t\t\t\t\"id\": \"id_1_1_1_3\"\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "]";

        Map<String, Object> sourceMap = new HashMap<>();

        /*sourceMap.put("total", "10000");
        sourceMap.put("test", "abc");
        sourceMap.put("dateT", "2320-10-11 20:10:22");
        sourceMap.put("abc", "ccc");
        sourceMap.put("a", "100");
        sourceMap.put("b", 100);
        sourceMap.put("c", "2320-10-11 20:10:22");*/

        sourceMap.put("BUKRS", "10000");
        sourceMap.put("PRCTR", "111");
        sourceMap.put("RCNTR","1000");
        sourceMap.put("USERNO","1.1");

        List<Map<String, Object>> sourceMapList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Map<String, Object> targetMap = new HashMap<>();

//            targetMap.put("total", "10000");
//            targetMap.put("test", "abc_" + i);
//            targetMap.put("dateT", "2320-10-11 20:10:22");
//            targetMap.put("abc", "ccc");
//            targetMap.put("a", "100");
//            targetMap.put("b", 100 * i);
//            targetMap.put("c", "2020-10-11 20:10:22");

            sourceMap.put("BUKRS", "10000");
            sourceMap.put("PRCTR", "111");
            sourceMap.put("RCNTR","1000");
            sourceMap.put("USERNO","1.1");

            sourceMapList.add(sourceMap);

        }

        SQLManyRelationConditionImpl pre = new SQLManyRelationConditionImpl();

        try {
            System.out.println(pre.resultHandler(sourceMapList, rule,null));
//            pre.out(pre.resultHandler(sourceMap, rule));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }



        // 结束时间
        long etime = System.currentTimeMillis();
        // 计算执行时间
//        pre.out("执行时长：" + (etime - stime) + " 毫秒.");

    }

    private void out(String outstr) {

        log.info("=================   " + outstr);
//        //System.out.println("=================   "+outstr);
    }

}
