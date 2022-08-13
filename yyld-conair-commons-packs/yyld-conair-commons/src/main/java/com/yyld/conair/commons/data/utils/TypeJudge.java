package com.yyld.conair.commons.data.utils;

import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName TypeJudge
 * @Description 数据类型判断  当前仅处理3种类型： 字符串、数值、日期类型
 * @Author yangj
 * @Date 2022/1/12 14:22
 * @Vresion 1.0
 **/
public final class TypeJudge {

    /**
     * @param param
     * @return java.lang.Object
     * @MonthedName judge
     * @Description 判断数据类型并类型转换后返回
     * @Date 2022/1/13 18:04
     **/
    public static Object judge(Object param) throws ParseException {

        //空值不处理
        if (param == null) {
            return param;
        }
        //判断 数据类型
        if (isNumber(param.toString())) {
            return Double.parseDouble(param.toString());
        }
        //判断 日期类型
        if (isValidDate(param.toString())) {
            String[] parsePatterns = {"yyyy-MM-dd", "yyyy年MM月dd日",
                    "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
                    "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};
            return DateUtils.parseDate(param.toString(), parsePatterns);
        }
        //字符串类型
        if (baseTypeHandler(param)) {
            return param.toString();
        }
        String type = param.getClass().getSimpleName().toUpperCase();
        throw new TypeNotPresentException("类型错误", new Throwable("数据类型未获取：" + type));

    }

    /**
     * 判断 是否为数值类型
     */
    public static boolean isNumber(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    /**
     * 判断 是否日期格式
     */
    public static boolean isValidDate(String str) {

        String[] parsePatterns = {"yyyy-MM-dd", "yyyy年MM月dd日",
                "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
                "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};
        if (str == null) {
            return false;
        }
        try {
            DateUtils.parseDate(str, parsePatterns);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * @param param
     * @return boolean
     * @MonthedName baseTypeHandler
     * @Description 基础数据类型判断
     * @Date 2022/1/12 15:35
     **/
    public static boolean baseTypeHandler(Object param) {

        if (isJavaClass(param.getClass())) {

            String type = param.getClass().getSimpleName().toUpperCase();

            switch (type) {
                case "LIST":
                    return false;
                case "ARRAYLIST":
                    return false;
                case "MAP":
                    return false;
                case "HASHMAP":
                    return false;
                case "STRING":

                    break;
                case "INT":

                    break;
                case "INTEGER":

                    break;
                case "LONG":

                    break;
                case "SHORT":

                    break;
                case "FLOAT":

                    break;
                case "DOUBLE":

                    break;
                case "CHAR":

                    break;
                case "BOOLEAN":
                    return false;
                case "BYTE":
                    return false;
                case "DATE":

                    break;
                case "DATETIME":

                    break;
                default:
                    throwException(type);

            }
        }
        return true;
    }

    /**
     * 判断一个类是JAVA类型还是用户定义类型
     *
     * @param clz
     * @return
     */
    public static boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }

    /**
     * 异常抛出方法
     */
    public static void throwException(String type) {
        throw new TypeNotPresentException("类型错误", new Throwable("数据类型未获取：" + type));
    }

    //按传入的格式获取当前时间  返回Date类型
    public static String getDateToString(Date time, String format) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        return simpleDateFormat.format(time);
    }

    //比较两个日期的大小
    public static long compareToDate(String beginTime, String endTime, String formatStr) throws Exception {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);

            Date date1 = format.parse(beginTime);
            Date date2 = format.parse(endTime);

            long beginMillisecond = date1.getTime();
            long endMillisecond = date2.getTime();

//			System.out.println(beginMillisecond +"  ----   "+ endMillisecond+"="+ (beginMillisecond > endMillisecond));

            return beginMillisecond - endMillisecond;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new Exception("错误");
    }

}
