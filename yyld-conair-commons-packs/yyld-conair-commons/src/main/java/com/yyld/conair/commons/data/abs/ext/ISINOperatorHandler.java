package com.yyld.conair.commons.data.abs.ext;

import com.yyld.conair.commons.data.abs.AbsOperatorHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName AbsDataHandler
 * @Description 为空 运算符操作
 * @Author yangj
 * @Date 2022/5/26 22:56
 * @Vresion 1.0
 **/
public final class ISINOperatorHandler extends AbsOperatorHandler {

    @Override
    public boolean compare(Object entityValue, Object valueM, Object betweenS, Object betweenE) throws Exception {

        System.out.println("=================ISINOperatorHandler===================");
        if (entityValue == null) {
            if (valueM == null) {
                return true;
            }
            return false;
        }

        if (valueM == null) {
            if (entityValue == null) {
                return true;
            }
            return false;
        }

        if (entityValue instanceof Date) {

            // 比较 日期
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //创建时间转换对象：时 分 秒
            List<Date> list = (List<Date>) valueM;
            Date sourceTime = df.parse(df.format(entityValue));

            for (Date date : list) {

                Date mapValueTime = df.parse(df.format(date));

                if (sourceTime.compareTo(mapValueTime) == 0) {
                    return true;
                }

            }
            return false;
        }

        if (entityValue instanceof String) {

            List<String> list = (List<String>) valueM;

            for (String str : list) {

                if (str.compareTo(entityValue.toString()) == 0) {
                    return true;
                }

            }

            return false;
        }

        if (entityValue instanceof Integer) {

            List<Integer> list = (List<Integer>) valueM;

            for (Integer val : list) {

                if ((int) entityValue - (int) val == 0) {

                    return true;
                }
            }

            return false;

        }

        if (entityValue instanceof Double) {

            List<Double> list = (List<Double>) valueM;

            for (Double val : list) {

                if ((double) entityValue - (double) val == 0) {

                    return true;
                }
            }

            return false;

        }

        throw new Exception("未知数据类型");

    }

    @Override
    public String splicingWhereHandler(String fieldCode, Object valueM, Object betweenS, Object betweenE) {

        String where = "";

        if (valueM == null) {
            throw new NullPointerException("参数为空异常");
        }
        if (valueM instanceof List) {

            List list = (List) valueM;

            where = "(" + fieldCode;

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //创建时间转换对象：时 分 秒

            String in = "";

            for (int i = 0; i < list.size(); i++) {

                Object entityValue = list.get(i);
                String temp = "";

                if (entityValue instanceof Date) {

                    temp = df.format(entityValue);

                } else {
                    temp = entityValue.toString();
                }

                if (i == list.size() - 1) {
                    in += "'" + temp + "'";
                } else {
                    in += "'" + temp + "',";
                }


            }
            where = where + " in (" + in + "))";
        }

        return where;
    }
}
