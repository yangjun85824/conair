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
public final class DoubleDataFieldTypeImpl implements DataFieldTypeInter<Double> {

    @Override
    public Double fieldTypeHandler(Object value) throws Exception {

        if (value == null || "".equals(value)) {
            return 0.0;
        }
        if (TypeJudge.isNumber(value.toString())) {

            return Double.parseDouble(value.toString());
        }
        throw new Exception("类型转换失败");
    }
}
