package com.yyld.conair.commons.data.abs;

import com.yyld.conair.commons.data.constants.Constants;
import com.yyld.conair.commons.data.entity.RelationConditionVO;

import java.text.ParseException;
import java.util.Map;

/**
 * @ClassName AbstractOperator
 * @Description 抽象运算符类
 * @Author yangj
 * @Date 2022/1/12 10:06
 * @Vresion 1.0
 **/
public abstract class AbstractOperator {

    /**
     * @param fieldType     字段类型
     * @param fieldValue    字段值
     * @param mapFieldValue 比对值
     * @return boolean
     * @MonthedName operatorExist
     * @Description 字段类型进行规则比对
     * @Date 2022/1/13 18:05
     **/
    public boolean operatorExist(String fieldType, String fieldValue, Object mapFieldValue) throws ParseException {

        //单值
        if (Constants.FIELD_TYPE_L.equals(fieldType)) {

            return fieldTypeL(fieldValue, mapFieldValue);

        }
        //多值
        if (Constants.FIELD_TYPE_M.equals(fieldType)) {

            return fieldTypeM(fieldValue, mapFieldValue);

        }
        //变量
        if (Constants.FIELD_TYPE_V.equals(fieldType)) {

            return fieldTypeV(fieldValue, mapFieldValue);

        }

        return false;

    }

    /**
     * @param vo
     * @param sourceMap
     * @return boolean
     * @MonthedName operatorExist
     * @Description 字段类型进行规则比对
     * @Date 2022/1/13 18:05
     **/
    public String analyzeAssociationConditions(RelationConditionVO vo, Map<String, Object> sourceMap) throws Exception {

        String sourceField = vo.getSourceField();
        String sourceFieldType = vo.getSourceType();
        String sourceFieldValue = vo.getSourceFieldValue();

        String targetField = vo.getTargetField();
        String targetFieldType = vo.getTargetType();
        String targetFieldValue = vo.getTargetFieldValue();
        Object sourceMapFieldValue = sourceMap.get(sourceField);

        //单值
        if (Constants.FIELD_TYPE_L.equals(sourceFieldType)) {

            return fieldTypeLSql(sourceFieldValue, sourceMapFieldValue, targetField, targetFieldType, targetFieldValue);

        }
        //多值
        if (Constants.FIELD_TYPE_M.equals(sourceFieldType)) {

            return fieldTypeMSql(sourceFieldValue, sourceMapFieldValue, targetField, targetFieldType, targetFieldValue);

        }
        //变量
        if (Constants.FIELD_TYPE_V.equals(sourceFieldType)) {

            return fieldTypeVSql(sourceFieldValue, sourceMapFieldValue, targetField, targetFieldType, targetFieldValue);

        }

        throw new Exception("字段类型错误");

    }

    //抽象方法子类实现 单值
    public abstract boolean fieldTypeL(String sourceFieldValue, Object sourceMapFieldValue) throws ParseException;

    //抽象方法子类实现 多值
    public abstract boolean fieldTypeM(String sourceFieldValue, Object sourceMapFieldValue) throws ParseException;

    //抽象方法子类实现 变量
    public abstract boolean fieldTypeV(String sourceFieldValue, Object sourceMapFieldValue) throws ParseException;

    //抽象方法子类实现 单值
    public abstract String fieldTypeLSql(String sourceFieldValue, Object sourceMapFieldValue, String targetField, String targetFieldType, String targetFieldValue) throws Exception;

    //抽象方法子类实现 多值
    public abstract String fieldTypeMSql(String sourceFieldValue, Object sourceMapFieldValue, String targetField, String targetFieldType, String targetFieldValue) throws Exception;

    //抽象方法子类实现 变量
    public abstract String fieldTypeVSql(String sourceFieldValue, Object sourceMapFieldValue, String targetField, String targetFieldType, String targetFieldValue) throws Exception;
}
