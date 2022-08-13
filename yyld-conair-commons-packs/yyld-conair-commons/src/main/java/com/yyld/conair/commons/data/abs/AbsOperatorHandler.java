package com.yyld.conair.commons.data.abs;

import com.alibaba.fastjson2.JSON;
import com.yyld.conair.commons.data.constants.Constants;
import com.yyld.conair.commons.data.entity.DataConditionStructureEntity;
import com.yyld.conair.commons.data.inter.DataFieldTypeInter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AbsDataHandler
 * @Description 操作符逻辑处理
 * @Author yangj
 * @Date 2022/5/26 22:56
 * @Vresion 1.0
 **/
public abstract class AbsOperatorHandler {

    public <T> boolean operatorHandler(T entity, Map<String, Object> data, DataConditionStructureEntity vo) throws Exception {

        Map<String, Object> entityMap = entity instanceof Map ? (Map) entity : JSON.parseObject(JSON.toJSONString(entity), Map.class);

        String field = vo.getFieldCode();

        Object entityValue = entityMap.get(field);

        String variable = vo.getVariable();

        String value = vo.getValue();

        String betweenStart = vo.getText1();
        String betweenEnd = vo.getText2();

        //当类型为变量时 从data参数中获取数据比较
        if (Constants.TYPE_V.equals(variable.toUpperCase())) {

            value = data.get(value) == null ? "" : data.get(value).toString();

            //当类型为变量 ，运算符为介于 或 不介于 时的取值
            if (Constants.OPERATOR_BT.equals(vo.getOperator().toUpperCase()) || Constants.OPERATOR_BE.equals(vo.getOperator().toUpperCase())) {

                betweenStart = data.get(vo.getText1()) == null ? null : data.get(vo.getText1()).toString();
                betweenEnd = data.get(vo.getText2()) == null ? null : data.get(vo.getText2()).toString();

            }

        }

        DataFieldTypeInter fieldTypeInter = Constants.fieldTypeMap.get(vo.getFieldType());

        Object valueM;
        //当运算符为 属于 、 不属于时
        if (Constants.OPERATOR_ISIN.equals(vo.getOperator().toUpperCase()) || Constants.OPERATOR_NOTIN.equals(vo.getOperator().toUpperCase())) {

            String vals[] = value.split(",");

            List dateList = new ArrayList<>();

            for (String val : vals) {

                dateList.add(fieldTypeInter.fieldTypeHandler(val));

            }
            valueM = dateList;
            entityValue = fieldTypeInter.fieldTypeHandler(entityValue);

        } else if (Constants.OPERATOR_ISNULL.equals(vo.getOperator().toUpperCase()) || Constants.OPERATOR_NOTNULL.equals(vo.getOperator().toUpperCase())) {
            valueM = value;
            entityValue = entityValue;
        } else {
            valueM = fieldTypeInter.fieldTypeHandler(value);
            entityValue = fieldTypeInter.fieldTypeHandler(entityValue);
        }
        Object betweenS = fieldTypeInter.fieldTypeHandler(betweenStart);
        Object betweenE = fieldTypeInter.fieldTypeHandler(betweenEnd);

        boolean bool = compare(entityValue, valueM, betweenS, betweenE);

        return bool;
    }

    public String operatorWhereHandler(Map<String, Object> data, DataConditionStructureEntity vo) throws Exception {

        String field = vo.getFieldCode();

        String variable = vo.getVariable();

        String value = vo.getValue();

        String betweenStart = vo.getText1();
        String betweenEnd = vo.getText2();

        //当类型为变量时 从data参数中获取数据比较
        if (Constants.TYPE_V.equals(variable.toUpperCase())) {

            value = data.get(value) == null ? "" : data.get(value).toString();

            //当类型为变量 ，运算符为介于 或 不介于 时的取值
            if (Constants.OPERATOR_BT.equals(vo.getOperator().toUpperCase()) || Constants.OPERATOR_BE.equals(vo.getOperator().toUpperCase())) {

                betweenStart = data.get(vo.getText1()) == null ? null : data.get(vo.getText1()).toString();
                betweenEnd = data.get(vo.getText2()) == null ? null : data.get(vo.getText2()).toString();

            } else {

                betweenStart = vo.getText1() == null ? null : vo.getText1();
                betweenEnd = vo.getText2() == null ? null : vo.getText2();

            }

        }

        DataFieldTypeInter fieldTypeInter = Constants.fieldTypeMap.get(vo.getFieldType());

        Object valueM;
        //当运算符为 属于 、 不属于时
        if (Constants.OPERATOR_ISIN.equals(vo.getOperator().toUpperCase()) || Constants.OPERATOR_NOTIN.equals(vo.getOperator().toUpperCase())) {

            String vals[] = value.split(",");

            List dateList = new ArrayList<>();

            for (String val : vals) {

                dateList.add(fieldTypeInter.fieldTypeHandler(val));

            }
            valueM = dateList;

        } else if (Constants.OPERATOR_ISNULL.equals(vo.getOperator().toUpperCase()) || Constants.OPERATOR_NOTNULL.equals(vo.getOperator().toUpperCase())) {
            valueM = value;
        } else {
            valueM = fieldTypeInter.fieldTypeHandler(value);
        }
        Object betweenS = fieldTypeInter.fieldTypeHandler(betweenStart);
        Object betweenE = fieldTypeInter.fieldTypeHandler(betweenEnd);

        if ((valueM == null || "".equals(valueM)) && Constants.TYPE_V.equals(variable.toUpperCase())) {
            return null;
        }

        String where = splicingWhereHandler(field, valueM, betweenS, betweenE);

        return where;
    }

    protected abstract boolean compare(Object entityValue, Object valueM, Object betweenS, Object betweenE) throws Exception;

    protected abstract String splicingWhereHandler(String fieldCode, Object valueM, Object betweenS, Object betweenE);
}
