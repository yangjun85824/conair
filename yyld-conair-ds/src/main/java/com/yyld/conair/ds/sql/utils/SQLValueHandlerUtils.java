package com.yyld.conair.ds.sql.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Map;

public final class SQLValueHandlerUtils {

    public static String valueHandler(String value){

        String resultValue = null;

        int len = value.length();
        if (len >= 3 && len <= 6){
            resultValue = value.replaceAll("！！！","‘");
        }else if (len >= 6){
            String before = value.substring(0,3);
            String after = value.substring(len-3);
            if (!StringUtils.equals(before,"！！！") || !StringUtils.equals(after,"！！！")){
                resultValue = value.replaceAll("！！！","‘");
            }else {
                String sub = value.substring(3, len - 3);
                resultValue = "'" + sub.replaceAll("！！！", "‘") + "'";
            }
        }else {
            resultValue = value;
        }

        return resultValue;
    }

    public static String handlerMapValue(String sql, Map<String, String> mapValue) {

        for (String key : mapValue.keySet()) {

            sql = sql.replaceAll(key, mapValue.get(key));

        }

        return sql;
    }
    public static void main(String[] args) {
        //valueHandler("！！！uuu！！！ooo！！！");
        String str = valueHandler("！！！uuu！！！ooo！！");
        System.out.println(str);
    }
}
