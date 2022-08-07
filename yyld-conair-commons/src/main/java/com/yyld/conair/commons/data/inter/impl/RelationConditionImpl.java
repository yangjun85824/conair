package com.yyld.conair.commons.data.inter.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yyld.conair.commons.data.abs.AbsOperatorHandler;
import com.yyld.conair.commons.data.constants.Constants;
import com.yyld.conair.commons.data.entity.DataConditionStructureEntity;
import com.yyld.conair.commons.data.inter.RelationCondition;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
public class RelationConditionImpl implements RelationCondition {

    private static final Logger log = LoggerFactory.getLogger(RelationConditionImpl.class);

    /**
     * @param data 过滤参数
     * @param list 过滤列表
     * @param rule 过滤规则
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>  过滤后返回的结果集
     * @MonthedName resultHandler 数据过滤方法
     * @Description 方法流程： 循环数据列表 -> 递归rule规则树 -> 右侧数据比对 -> 左侧数据比对 -> 返回比对结果 -> 根据结果将满足条件的数据添加到结果集列表
     * 方法描述： 关联关系用于筛选过滤数据。根据传入的list数据循环，每一条数据根据rule规则和data参数的比对进行筛选。
     * rule规则为树型结构，所以每一条数据都需要递归一次规则进行筛选，当递归执行到FIELD类型节点时，
     * 代表已为最末节点，节点进行字段比对数据比对，数据比对：
     * 先进行右侧的数据比对：取出规则中的操作符operator（operator类型见——biz包Constants）通过操
     * 作符的类型来决定走哪一个判断分支。再规则中的sourceField、sourceFieldValue、sourceType以
     * 及data中（sourceField）字段数据，(sourceType类型见——biz包Constants,类型分为3种：单值、多值、变量)匹配数据类型后，
     * sourceFieldValue与data中的字段值是否条件满足，当条件满足时，则进行左侧的数据比对，
     * 如果条件不满足则直接返回false
     * 左侧数据比对：取出规则中的操作符operator（operator类型见——biz包Constants）通过操
     * 作符的类型来决定走哪一个判断分支。再规则中的targetTield、targetTieldValue、targetType以
     * 及targetMap(每一条的循环数据)中（targetTield）字段数据，(targetType类型见——biz包Constants,类型分为3种：单值、多值、变量)匹配数据类型后，
     * targetTieldValue与data中的字段值是否条件满足，当条件满足时，则该点返回为true,否则返回false，
     * 并记录和修改父级的操作类
     * 末级节点返回数据后，根据操作类的变量值进行判断该节点数据为true还是false。为true时，则该数据满足条件，
     * 将该数据添加到返回的结果集中，否则该数据就被过滤掉
     * rule规则说明： rule规则为树型结构，所以每一条数据都需要递归一次规则进行筛选，
     * 树型结构节点分两两种类型type:GROUP、FIELD
     * 操作符为分为两种operator：AND、OR
     * 排序号sort: 主要提供给前端排序使用
     * id唯一标识： 主用用于返回返回树形筛选结果时作用
     * parentid父类ID: 主用用于返回返回树形筛选结果时作用
     * rele参考：该类的main方法中
     * @Date 2022/1/13 15:46
     **/
    @Override
    public <T> List<T> resultHandler(Map<String, Object> data, List<T> list, String rule) throws ParseException {

        if (StringUtils.isBlank(rule)) {
            throw new NullPointerException("规则为空");
        }

        if (data == null) {
            throw new NullPointerException("校验字段参数为空");
        }

        if (list == null || list.size() == 0) {
            throw new NullPointerException("源数据参数为空");
        }

        out("============校验过程执行  开始====================================");

        List<T> resultList = new ArrayList<>();

        //转化规则树
        List<DataConditionStructureEntity> jsonList = JSON.parseArray(rule, DataConditionStructureEntity.class);

        //循环数据列表
        for (T entity : list) {

            //规则校验 当规则校验成功 返回true或false
            boolean bool = ruleTree(entity,data,jsonList);

            //规则满足后将数据添加到结果集中
            if (bool) {

                resultList.add(entity);
            }

        }

        out("操作结果 resultListMap 长度 = " + resultList.size());
        out("操作结果 resultListMap = " + JSON.toJSONString(resultList == null ? "" : resultList));
        out("============校验过程执行  结束====================================");
        return resultList;

    }

    private <T> boolean ruleTree(T entity, Map<String, Object> data, List<DataConditionStructureEntity> jsonList) throws ParseException {

        boolean resultbool = false;

        int i = 0;

        for (DataConditionStructureEntity vo : jsonList) {

            boolean bool = false;
            try {

                bool = groupHandler(vo,entity,data);

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            //组处理
            if (Constants.GROUP_OPERATOR_AND.equals(vo.getLogic())) {

                if (i == 0){
                    resultbool = bool;
                }else {
                    resultbool = (resultbool && bool);
                }
                i = 1;
            }
            if (Constants.GROUP_OPERATOR_OR.equals(vo.getLogic())) {

                if (i == 0){
                    resultbool = bool;
                }else {
                    resultbool = (resultbool || bool);
                }
                i = 1;
            }

            if (!resultbool){

                out("返回 FALSE");
                return resultbool;
            }

            out("执行 groupHandler 方法   结束");
        }

        return resultbool;
    }

    /**
     * @param vo
     * @return void
     * @MonthedName groupHandler
     * @Description 递归
     * @Date 2022/1/13 17:50
     **/
    private <T> boolean groupHandler(DataConditionStructureEntity vo, T entity, Map<String, Object> data) throws Exception {

        out("规则递归开始 ================");
        out("操作符 = " + vo.getOperator());
        out("类型为 = " + vo.getType());
        //字段类型为FIELD时代表规则树为最末节点
        if (Constants.FIELD_TYPE.equals(vo.getType().toUpperCase())) {

            //数据比对
            boolean bool = dataHandler(vo , entity , data);
            out("FIELD 判断结果返回 = " + bool);

            return bool;
        }

        List<DataConditionStructureEntity> list = vo.getChildren();

        boolean resultbool = false;

        int i = 0;

        for (DataConditionStructureEntity item : list) {

            boolean bool = groupHandler(item,entity,data);

            if (Constants.GROUP_OPERATOR_AND.equals(vo.getLogic().toUpperCase())) {

                if (i == 0){
                    resultbool = bool;
                }else {
                    resultbool = (resultbool && bool);
                }
            }
            if (Constants.GROUP_OPERATOR_OR.equals(vo.getLogic().toUpperCase())) {

                if (i == 0){
                    resultbool = bool;
                }else {
                    resultbool = (resultbool || bool);
                }

            }
            i = 1;

            if (!resultbool){
                return resultbool;
            }
        }

        return resultbool;
    }

    //处理实体数据
    private <T> boolean dataHandler(DataConditionStructureEntity vo, T entity, Map<String, Object> data) throws Exception {

        out("类型为字段时才进入该方法");
        String operator = vo.getOperator();

        out("operator==="+operator);

        operator = ( operator == null || "".equals(operator) ) ? Constants.OPERATOR_EQ : operator.toUpperCase();

        AbsOperatorHandler operatorHandler = Constants.operatorMap.get(operator);

        boolean boolSource;

        if (operatorHandler == null) {
            throw new NullPointerException("规则JSON格式错误");
        }

        boolSource = operatorHandler.operatorHandler(entity,data,vo);

        return boolSource;
    }

    private void out(String outstr) {

        log.info("=================   " + outstr);
//        //System.out.println("=================   "+outstr);
    }

}
