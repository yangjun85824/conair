package com.yyld.conair.commons.data.abs.ext;

import com.yyld.conair.commons.data.abs.AbsOperatorHandler;

/**
 * @ClassName AbsDataHandler
 * @Description 非空 运算符操作
 * @Author yangj
 * @Date 2022/5/26 22:56
 * @Vresion 1.0
 **/
public final class NOTNULLOperatorHandler extends AbsOperatorHandler {

    @Override
    public boolean compare(Object entityValue, Object valueM, Object betweenS, Object betweenE) throws Exception {

        if (entityValue != null && !"".equals(entityValue.toString())) {

            return true;
        }
        return false;
    }

    @Override
    public String splicingWhereHandler(String fieldCode, Object valueM, Object betweenS, Object betweenE) {

        String where = "(" + fieldCode + " is not null)";

        return where;
    }
}
