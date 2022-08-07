package com.yyld.conair.commons.data.abs.ext;

import com.yyld.conair.commons.data.abs.AbsOperatorHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName AbsDataHandler
 * @Description = 运算符操作
 * @Author yangj
 * @Date 2022/5/26 22:56
 * @Vresion 1.0
 **/
public final class EQOperatorHandler extends AbsOperatorHandler {

    @Override
    public boolean compare(Object entityValue, Object valueM ,Object betweenS, Object betweenE) throws Exception {

        System.out.println("=================EQOperatorHandler===================");

        if (entityValue == null){
            if (valueM == null) {
                return true;
            }
            return false;
        }

        if (valueM == null){
            if (entityValue == null) {
                return true;
            }
            return false;
        }

        if (entityValue instanceof String) {

            if (entityValue.toString().equals(valueM.toString())){
                return true;
            }
            return false;
        }

        if (entityValue instanceof Integer) {

            if ((int)entityValue - (int)valueM == 0){

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

                if (sourceTime.compareTo(mapValueTime) == 0){
                    return true;
                }
                return false;

            } catch (ParseException e) {
                return false;
            }
        }
        if (entityValue instanceof Double) {

            if ((double)entityValue - (double)valueM == 0){

                return true;
            }
            return false;
        }
        if (entityValue instanceof Boolean) {

            if ((boolean)entityValue && (boolean)valueM){

                return true;
            }
            return false;
        }

        throw new Exception("未知类型");
    }

    @Override
    public String splicingWhereHandler(String fieldCode, Object valueM, Object betweenS, Object betweenE) {

        String where = "";

        if (valueM == null){

            where = "("+fieldCode+" is null or "+fieldCode+"='')";
            return where;
        }

        if (valueM instanceof String) {

            where = "("+fieldCode+" = '"+valueM+"')";
            return where;
        }

        if (valueM instanceof Integer) {

            where = "("+fieldCode+" = "+valueM+")";
            return where;

        }
        if (valueM instanceof Date) {

            // 比较 日期
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //创建时间转换对象：时 分 秒
            String date = df.format(valueM);

            where = "("+fieldCode+" = '"+date+"')";
            return where;
        }
        if (valueM instanceof Double) {

            where = "("+fieldCode+" = "+valueM+")";
            return where;
        }

        return where;
    }
}
