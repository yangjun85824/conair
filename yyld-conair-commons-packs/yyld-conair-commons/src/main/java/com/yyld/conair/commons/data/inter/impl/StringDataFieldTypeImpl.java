package com.yyld.conair.commons.data.inter.impl;

import com.yyld.conair.commons.data.inter.DataFieldTypeInter;

/**
 * @ClassName DataTypeInter
 * @Description 数据类型接口
 * @Author yangj
 * @Date 2022/5/26 22:42
 * @Vresion 1.0
 **/
public final class StringDataFieldTypeImpl implements DataFieldTypeInter<String> {

    @Override
    public String fieldTypeHandler(Object value) {

        if (value == null) {
            return null;
        }

        return value.toString();
    }
}
