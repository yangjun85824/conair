package com.yyld.conair.commons.data.abs.ext;

import com.yyld.conair.commons.data.abs.AbsOperatorHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName AbsDataHandler
 * @Description 为空 运算符操作
 * @Author yangj
 * @Date 2022/5/26 22:56
 * @Vresion 1.0
 **/
public final class NOTLIKEOperatorHandler extends AbsOperatorHandler {

    @Override
    public boolean compare(Object entityValue, Object valueM ,Object betweenS, Object betweenE) throws Exception {

        if (entityValue == null){
            if (valueM != null) {
                return true;
            }
            return false;
        }

        if (valueM == null){
            if (entityValue != null) {
                return true;
            }
            return false;
        }

        if (entityValue instanceof Date) {

            // 比较 日期
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //创建时间转换对象：时 分 秒
            try {

                Date sourceTime = df.parse(df.format(entityValue));
                Date mapValueTime = df.parse(df.format(valueM));

                if (sourceTime.compareTo(mapValueTime) != 0){
                    return true;
                }
                return false;

            } catch (ParseException e) {
                return false;
            }
        }

        if (valueM.toString().indexOf(entityValue.toString()) != -1){

            return false;
        }

        return true;

    }

    @Override
    public String splicingWhereHandler(String fieldCode, Object valueM, Object betweenS, Object betweenE) {

        String where = "";

        if (valueM == null){
            throw new NullPointerException("参数为空异常");
        }

        if (valueM instanceof String) {

            where = "("+fieldCode+" not like '%"+valueM+"%')";
            return where;
        }

        if (valueM instanceof Integer) {

            where = "("+fieldCode+" not like '%"+valueM+"%')";
            return where;

        }
        if (valueM instanceof Date) {

            // 比较 日期
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //创建时间转换对象：时 分 秒
            String date = df.format(valueM);

            where = "("+fieldCode+" not like '%"+date+"%')";
            return where;
        }
        if (valueM instanceof Double) {

            where = "("+fieldCode+" not like '%"+valueM+"%')";
            return where;
        }

        return where;
    }
}
