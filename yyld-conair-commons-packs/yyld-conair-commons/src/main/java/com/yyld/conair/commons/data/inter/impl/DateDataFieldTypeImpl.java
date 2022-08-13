package com.yyld.conair.commons.data.inter.impl;

import com.yyld.conair.commons.data.inter.DataFieldTypeInter;
import com.yyld.conair.commons.data.utils.TypeJudge;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * @ClassName DataTypeInter
 * @Description 数据类型接口
 * @Author yangj
 * @Date 2022/5/26 22:42
 * @Vresion 1.0
 **/
public final class DateDataFieldTypeImpl implements DataFieldTypeInter<Date> {

    @Override
    public Date fieldTypeHandler(Object value) throws Exception {

        if (value == null) {
            return null;
        }

        if (TypeJudge.isValidDate(value.toString())) {
            String[] parsePatterns = {"yyyy-MM-dd", "yyyy年MM月dd日",
                    "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
                    "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};
            return DateUtils.parseDate(value.toString(), parsePatterns);
        }

        throw new Exception("类型转换失败");
    }
}
