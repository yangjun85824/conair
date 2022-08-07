package com.yyld.conair.commons.data.inter.impl;

import com.yyld.conair.commons.data.inter.DataFieldTypeInter;
import com.yyld.conair.commons.data.utils.TypeJudge;

/**
 * @ClassName DataTypeInter
 * @Description 数据类型接口
 * @Author yangj
 * @Date 2022/5/26 22:42
 * @Vresion 1.0
 **/
public final class IntDataFieldTypeImpl implements DataFieldTypeInter<Integer> {

    @Override
    public Integer fieldTypeHandler(Object value) throws Exception {

        if (value == null){
            return 0;
        }
        if (TypeJudge.isNumber(value.toString())){
            return Integer.parseInt(value.toString());
        }
        throw new Exception("类型转换失败");
    }
}
