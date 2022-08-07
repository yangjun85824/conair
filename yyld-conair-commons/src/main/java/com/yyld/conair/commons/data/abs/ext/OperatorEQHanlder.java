package com.yyld.conair.commons.data.abs.ext;

import com.yyld.conair.commons.data.abs.AbstractOperator;
import com.yyld.conair.commons.data.constants.Constants;
import com.yyld.conair.commons.data.utils.TypeJudge;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName OperatorEQHanlder
 * @Description 运算符 = EQ
 * @Author yangj
 * @Date 2022/1/12 10:23
 * @Vresion 1.0
 **/
public class OperatorEQHanlder extends AbstractOperator {

    @Override
    public boolean fieldTypeL(String fieldValue, Object mapFieldValue) throws ParseException {

        if (StringUtils.isNotBlank(fieldValue)) {

            if (mapFieldValue == null) {
                return false;
            }
            Object temp = TypeJudge.judge(mapFieldValue);

            return compareTo(fieldValue, temp);
        }
        if (fieldValue == null || "".equals(fieldValue)) {

            if (mapFieldValue == null || "".equals(mapFieldValue.toString())) {

                return true;

            }

        }

        return false;

    }

    @Override
    public boolean fieldTypeM(String fieldValue, Object mapFieldValue) throws ParseException {

        if (StringUtils.isNotBlank(fieldValue)) {

            if (mapFieldValue == null) {
                return false;
            }

            String[] temps = mapFieldValue.toString().split(",");

            String[] values = fieldValue.split(",");

            for (String str : values) {

                for (String item : temps) {

                    Object temp = TypeJudge.judge(item);

                    if (compareTo(str, temp)) {
                        return true;
                    }

                }

            }

            return false;
        }
        if (fieldValue == null || "".equals(fieldValue)) {

            if (mapFieldValue == null || "".equals(mapFieldValue)) {

                return true;

            }

        }
        return false;
    }

    @Override
    public boolean fieldTypeV(String sourceValue, Object sourceMapValue) throws ParseException {

        Object temp = TypeJudge.judge(sourceMapValue);

        boolean bool = compareTo(sourceValue, temp);

        return bool;
    }

    @Override
    public String fieldTypeLSql(String sourceFieldValue, Object sourceMapFieldValue, String targetField, String targetFieldType, String targetFieldValue) throws Exception {

        if (StringUtils.isNotBlank(sourceFieldValue)) {

            if (sourceMapFieldValue == null) {

                throw new NullPointerException("参数为空异常");
            }
            Object temp = TypeJudge.judge(sourceMapFieldValue);

            if (compareTo(sourceFieldValue, temp)) {
                return setWhereValue(sourceMapFieldValue,targetField,targetFieldType,targetFieldValue);
            }
            throw new Exception("条件不匹配");
        }
        if (sourceFieldValue == null || "".equals(sourceFieldValue)) {

            if (sourceMapFieldValue == null || "".equals(sourceMapFieldValue.toString())) {

                return "(" + targetField + " is null or " + targetField + "=null)";

            }

        }

        throw new Exception("条件不匹配");
    }

    @Override
    public String fieldTypeMSql(String sourceFieldValue, Object sourceMapFieldValue, String targetField, String targetFieldType, String targetFieldValue) throws Exception {
        if (StringUtils.isNotBlank(sourceFieldValue)) {

            if (sourceMapFieldValue == null) {
                throw new NullPointerException("参数为空异常");
            }

            String[] temps = sourceMapFieldValue.toString().split(",");

            String[] values = sourceFieldValue.split(",");

            boolean bool = false;
            first:
            for (String str : values) {

                for (String item : temps) {

                    Object temp = TypeJudge.judge(item);

                    if (compareTo(str, temp)) {

                        bool = true;

                        break first;
                    }

                }

            }
            if (bool) {
                if (StringUtils.isBlank(targetFieldValue)) {
                    return "(" + targetField + " = '" + targetFieldValue + "' or " + targetField + "=null)";
                }

                return setWhereValue(sourceMapFieldValue,targetField,targetFieldType,targetFieldValue);
            }

            throw new Exception("条件不匹配");
        }
        if (sourceFieldValue == null || "".equals(sourceFieldValue)) {

            if (sourceMapFieldValue == null || "".equals(sourceMapFieldValue)) {

                return "(" + targetField + "='' or " + targetField + " is null)";

            }

        }
        throw new Exception("条件不匹配");
    }

    @Override
    public String fieldTypeVSql(String sourceFieldValue, Object sourceMapFieldValue, String targetField, String targetFieldType, String targetFieldValue) throws ParseException {


        if (sourceMapFieldValue == null || "".equals(sourceMapFieldValue)) {

            return "(" + targetField + "='' or " + targetField + " is null)";

        }
        return setWhereValue(sourceMapFieldValue,targetField,targetFieldType,targetFieldValue);

    }

    private String setWhereValue(Object sourceMapFieldValue, String targetField, String targetFieldType, String targetFieldValue){

        String where = null;
        //单值
        if (Constants.FIELD_TYPE_L.equals(targetFieldType)) {

            return "(" + targetField + "='" + targetFieldValue + "')";

        }
        //多值
        if (Constants.FIELD_TYPE_M.equals(targetFieldType)) {


            String[] targetValues = targetFieldValue.split(",");

            String val = "";
            for (int i = 0; i < targetValues.length; i++) {

                if ("".equals(val)) {
                    val += "'" + targetValues[i] + "'";
                } else {
                    val += ",'" + targetValues[i] + "'";
                }

            }
            return "(" + targetField + " in (" + val + "))";

        }
        //变量
        if (Constants.FIELD_TYPE_V.equals(targetFieldType)) {

            return  "(" + targetField + "='" + sourceMapFieldValue + "')";

        }

        return where;
    }

    private boolean compareTo(String fieldValue, Object mapFieldValue) {

        if (mapFieldValue instanceof Double) {

            if (Double.parseDouble(fieldValue) - (Double) mapFieldValue == 0) {
                return true;
            }
            return false;
        }
        if (mapFieldValue instanceof String) {

            return fieldValue.equals(mapFieldValue.toString());
        }
        if (mapFieldValue instanceof Date) {

            // 比较 日期
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //创建时间转换对象：时 分 秒
            try {

                Date sourceTime = df.parse(fieldValue);
                Date mapValueTime = (Date) mapFieldValue;

                return sourceTime.compareTo(mapValueTime) == 0;//sourceTime.getTime() == mapValueTime.getTime();

            } catch (ParseException e) {
                return false;
            }
        }

        return false;

    }

}
