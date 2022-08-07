package com.yyld.conair.commons.data.abs.ext;

import com.yyld.conair.commons.data.abs.AbsOperatorHandler;
import com.yyld.conair.commons.data.utils.TypeJudge;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName AbsDataHandler
 * @Description = 运算符操作
 * @Author yangj
 * @Date 2022/5/26 22:56
 * @Vresion 1.0
 **/
public final class BEOperatorHandler extends AbsOperatorHandler {

    @Override
    public boolean compare(Object entityValue, Object valueM ,Object betweenS, Object betweenE) throws Exception {

        if (betweenS == null || betweenE == null ){

            if (entityValue == null){

                return true;
            }
            return false;

        }

        if (entityValue instanceof String) {

            String value = entityValue.toString();

            if (((String) betweenS).compareTo(value) <= 0 && ((String) betweenE).compareTo(value) >= 0){
                return true;
            }
            return false;

        }
        if (entityValue instanceof Integer) {

            int lowValue = Integer.parseInt(betweenS.toString());
            int hignValue = Integer.parseInt(betweenE.toString());
            int value = Integer.parseInt(entityValue.toString());

            if (lowValue <= value && hignValue >= value ){
                return true;
            }
            return false;
        }
        if (entityValue instanceof Date) {

            String dateValue ;
            //判断某个日期是否在两个日期范围之内
            SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (!(entityValue instanceof Date)){

                dateValue = simpleDateFormat.format(entityValue);
            }else {
                dateValue = TypeJudge.getDateToString((Date) entityValue,"yyyy-MM-dd HH:mm:ss");
            }


            Date lowdate=simpleDateFormat.parse(simpleDateFormat.format(betweenS));
            Date higndate=simpleDateFormat.parse(simpleDateFormat.format(betweenE));

            Date value=simpleDateFormat.parse(dateValue);

            if(lowdate.getTime()<=value.getTime() && higndate.getTime()>=value.getTime()){
                return true;
            }

            return false;

        }
        if (entityValue instanceof Double) {

            int lowValue = Integer.parseInt(betweenS.toString());
            int hignValue = Integer.parseInt(betweenE.toString());
            int value = Integer.parseInt(entityValue.toString());

            if (lowValue <= value && hignValue >= value ){
                return true;
            }
            return false;
        }

        throw new Exception("未知类型");
    }

    @Override
    public String splicingWhereHandler(String fieldCode, Object valueM, Object betweenS, Object betweenE) {

        String where = "";

        if (betweenS == null || betweenE == null) {

            throw new NullPointerException("参数为空异常");

        }

        if (betweenS instanceof String) {

            String value = betweenS.toString();

            where = "(" + fieldCode + " not between '" + value + "' and '" + betweenE.toString() + "' )";

            return where;

        }
        if (betweenS instanceof Integer) {

            int lowValue = Integer.parseInt(betweenS.toString());
            int hignValue = Integer.parseInt(betweenE.toString());

            where = "(" + fieldCode + " not between " + lowValue + " and " + hignValue + " )";

            return where;
        }
        if (betweenS instanceof Date) {

            //判断某个日期是否在两个日期范围之内
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String low = simpleDateFormat.format(betweenS);
            String hign = simpleDateFormat.format(betweenE);

            where = "(" + fieldCode + " not between '" + low + "' and '" + hign + "' )";

            return where;

        }
        if (betweenS instanceof Double) {

            double lowValue = Double.parseDouble(betweenS.toString());
            double hignValue = Double.parseDouble(betweenE.toString());

            where = "(" + fieldCode + " not between " + lowValue + " and " + hignValue + " )";

            return where;
        }

        return where;
    }

}
