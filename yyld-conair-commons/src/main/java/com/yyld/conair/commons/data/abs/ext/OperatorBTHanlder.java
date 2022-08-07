package com.yyld.conair.commons.data.abs.ext;

import com.yyld.conair.commons.data.abs.AbstractOperator;
import com.yyld.conair.commons.data.constants.Constants;
import com.yyld.conair.commons.data.utils.TypeJudge;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;

/**
 * @ClassName OperatorCPHanlder
 * @Description 运算符 包含 CP
 * @Author yangj
 * @Date 2022/2/17 10:23
 * @Vresion 1.0
 **/
public class OperatorBTHanlder extends AbstractOperator {

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
    public boolean fieldTypeV(String fieldValue, Object mapFieldValue) throws ParseException {

        Object temp = TypeJudge.judge(mapFieldValue);

        return compareTo(fieldValue, temp);
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

                return "(" + targetField + " like '%%')";

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

                return setWhereValue(sourceMapFieldValue,targetField,targetFieldType,targetFieldValue);

            }

            throw new Exception("条件不匹配");
        }
        if (sourceFieldValue == null || "".equals(sourceFieldValue)) {

            if (sourceMapFieldValue == null || "".equals(sourceMapFieldValue)) {

                return "(" + targetField + " like '%%')";

            }

        }
        throw new Exception("条件不匹配");
    }

    @Override
    public String fieldTypeVSql(String sourceFieldValue, Object sourceMapFieldValue, String targetField, String targetFieldType, String targetFieldValue) throws ParseException {

        if (sourceMapFieldValue == null || "".equals(sourceMapFieldValue)) {

            return "(" + targetField + " like '%%')";

        }
        return setWhereValue(sourceMapFieldValue,targetField,targetFieldType,targetFieldValue);
    }

    private String setWhereValue(Object sourceMapFieldValue, String targetField, String targetFieldType, String targetFieldValue){

        String where = null;
        //单值
        if (Constants.FIELD_TYPE_L.equals(targetFieldType)) {

            return "(" + targetField + " like '%" + targetFieldValue + "%')";

        }
        //多值
        if (Constants.FIELD_TYPE_M.equals(targetFieldType)) {

            if (StringUtils.isBlank(targetFieldValue)) {
                return "(" + targetField + " like '%%')";
            }
            String[] targetValues = targetFieldValue.split(",");

            String val = "(";
            for (int i = 0; i < targetValues.length; i++) {

                if (i == 0){
                    val += targetField+" like '%"+targetValues[i]+"%'";
                }else {
                    val += " and "+targetField+" like '%"+targetValues[i]+"%'";
                }

            }

            return val + ")";

        }
        //变量
        if (Constants.FIELD_TYPE_V.equals(targetFieldType)) {

            return  "(" + targetField + " like '%" + sourceMapFieldValue + "%')";

        }

        return where;
    }

    private boolean compareTo(String fieldValue,Object mapFieldValue){

        if (mapFieldValue == null){
            return false;
        }

        if (mapFieldValue instanceof Double){

            String mvalue = mapFieldValue.toString();

            if (mvalue.indexOf(".") != -1){

                String[] temp = mvalue.split("\\.");

                if (temp.length > 1){

                    double d = Double.parseDouble(temp[1]);
                    if (d == 0 && fieldValue.indexOf(temp[0]) != -1){
                        return true;
                    }

                    if (fieldValue.indexOf(mvalue) != -1){
                        return true;
                    }

                }

            }

            return false;

        }

         if (fieldValue.indexOf(mapFieldValue.toString()) != -1){
             return true;
         }

        return false;

    }

}
