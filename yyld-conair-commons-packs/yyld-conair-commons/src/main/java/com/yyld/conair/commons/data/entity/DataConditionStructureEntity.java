package com.yyld.conair.commons.data.entity;

import java.util.List;

/**
 * @ClassName DataConditionStructureEntity
 * @Description 数据条件结构实体类
 * 原json结构：[
 * {  type: 'group',
 * logic: 'and',
 * children: [
 * {
 * type: 'field',
 * oprator: '',
 * fieldCode: '',
 * fieldType: 'number',
 * variable: 'var', // const
 * value: '',
 * rightFiledType: 'number',
 * text1: '',
 * text2: '',
 * <p>
 * },
 * {
 * type: 'group',
 * logic: 'and',
 * children: [
 * <p>
 * ]
 * }
 * ]
 * }
 * ]
 * @Author yangj
 * @Date 2022/5/26 17:40
 * @Vresion 1.0
 **/
public class DataConditionStructureEntity {

    //类型 ： 组 、 字段
    private String type;

    //条件 ： AND 、 OR
    private String logic;

    //运算符 ： eq等于 、 ne不等于 、 gt大于 、 ge大于等于 、 lt小于 、 le小于等于 、 bt介于 、 be不介于
    private String operator;

    //字段编码
    private String fieldCode;

    //字段类型
    private String fieldType;

    //运算类型：var 变量 、 const 常量
    private String variable;

    //数据值
    private String value;

    //变量字段类型
    private String rightFiledType;

    //扩展字段1
    private String text1;

    //扩展字段2
    private String text2;

    //子集
    private List<DataConditionStructureEntity> children;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRightFiledType() {
        return rightFiledType;
    }

    public void setRightFiledType(String rightFiledType) {
        this.rightFiledType = rightFiledType;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public List<DataConditionStructureEntity> getChildren() {
        return children;
    }

    public void setChildren(List<DataConditionStructureEntity> children) {
        this.children = children;
    }
}
