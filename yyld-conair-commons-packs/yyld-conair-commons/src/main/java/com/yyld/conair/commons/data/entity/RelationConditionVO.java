package com.yyld.conair.commons.data.entity;

import java.util.List;

/**
 * @ClassName RelationConditionVO
 * @Description 前置业务关联关系实体类
 * @Author yangj
 * @Date 2022/1/11 10:04
 * @Vresion 1.0
 **/
public class RelationConditionVO {

    private String id; //实体ID

    private String parentId;//父级ID

    private String operator;//运算符

    private String type;//类型

    private String name;//显示名称

    private int sort;//排序号

    private String sourceFieldName;//源字段名称

    private String sourceField;//源字段

    private String sourceFieldValue;//源字段值

    private String sourceType;//源字段类型

    private String targetFieldName;//

    private String targetType;

    private String targetFieldValue;

    private String targetField;

    private List<RelationConditionVO> nextList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getSourceFieldName() {
        return sourceFieldName;
    }

    public void setSourceFieldName(String sourceFieldName) {
        this.sourceFieldName = sourceFieldName;
    }

    public String getSourceField() {
        return sourceField;
    }

    public void setSourceField(String sourceField) {
        this.sourceField = sourceField;
    }

    public String getSourceFieldValue() {
        return sourceFieldValue;
    }

    public void setSourceFieldValue(String sourceFieldValue) {
        this.sourceFieldValue = sourceFieldValue;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getTargetFieldName() {
        return targetFieldName;
    }

    public void setTargetFieldName(String targetFieldName) {
        this.targetFieldName = targetFieldName;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public List<RelationConditionVO> getNextList() {
        return nextList;
    }

    public void setNextList(List<RelationConditionVO> nextList) {
        this.nextList = nextList;
    }

    public String getTargetFieldValue() {
        return targetFieldValue;
    }

    public void setTargetFieldValue(String targetFieldValue) {
        this.targetFieldValue = targetFieldValue;
    }

    public String getTargetField() {
        return targetField;
    }

    public void setTargetField(String targetField) {
        this.targetField = targetField;
    }
}
