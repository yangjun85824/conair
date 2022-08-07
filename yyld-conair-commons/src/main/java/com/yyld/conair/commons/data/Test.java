package com.yyld.conair.commons.data;

import com.alibaba.fastjson.JSON;
import com.yyld.conair.commons.data.inter.FaceDataHandlerInter;
import com.yyld.conair.commons.data.inter.impl.FaceDataHandlerImpl;
import com.yyld.conair.commons.data.utils.PageResultListHandlerUtil;
import org.apache.commons.lang.StringEscapeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName Test
 * @Description //TODO
 * @Author yangj
 * @Date 2022/5/26 16:04
 * @Vresion 1.0
 **/
public class Test {

    public static void main(String[] args) throws Exception {

        // 开始时间
        long stime = System.currentTimeMillis();

        String rule = "[\n" +
                "\t{\n" +
                "\t\t\"type\": \"group\",\n" +
                "\t\t\"id\": \"1\",\n" +
                "\t\t\"logic\": \"AND\",\n" +
                "\t\t\"children\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"type\": \"field\",\n" +
                "\t\t\t\t\"id\": 9903019004,\n" +
                "\t\t\t\t\"fieldCode\": \"abc\",\n" +
                "\t\t\t\t\"fieldType\": \"number\",\n" +
                "\t\t\t\t\"variable\": \"const\",\n" +
                "\t\t\t\t\"rightFiledType\": \"number\",\n" +
                "\t\t\t\t\"value\": \"10,20,30\",\n" +
                "\t\t\t\t\"operator\": \"ISIN\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"type\": \"field\",\n" +
                "\t\t\t\t\"id\": 1438778087,\n" +
                "\t\t\t\t\"fieldCode\": \"bcd\",\n" +
                "\t\t\t\t\"fieldType\": \"number\",\n" +
                "\t\t\t\t\"variable\": \"const\",\n" +
                "\t\t\t\t\"rightFiledType\": \"number\",\n" +
                "\t\t\t\t\"value\": \"\",\n" +
                "\t\t\t\t\"operator\": \"BT\",\n" +
                "\t\t\t\t\"text1\": \"10\",\n" +
                "\t\t\t\t\"text2\": \"100\",\n" +
//                "\t\t\t\t\"text1\": \"ggg\",\n" +
//                "\t\t\t\t\"text2\": \"eef\",\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"type\": \"field\",\n" +
                "\t\t\t\t\"id\": 6072759084,\n" +
                "\t\t\t\t\"fieldCode\": \"cde\",\n" +
                "\t\t\t\t\"fieldType\": \"varchar\",\n" +
                "\t\t\t\t\"variable\": \"const\",\n" +
                "\t\t\t\t\"rightFiledType\": \"number\",\n" +
                "\t\t\t\t\"value\": \"no\",\n" +
                "\t\t\t\t\"operator\": \"ne\"\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "]";
        /*String rule = "[\n" +
                "\t{\n" +
                "\t\t\"type\": \"group\",\n" +
                "\t\t\"id\": 1133274952,\n" +
                "\t\t\"logic\": \"AND\",\n" +
                "\t\t\"children\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"type\": \"field\",\n" +
                "\t\t\t\t\"id\": 5429118307,\n" +
                "\t\t\t\t\"operator\": \"EQ\",\n" +
                "\t\t\t\t\"fieldCode\": \"YEWULEIXING\",\n" +
                "\t\t\t\t\"fieldName\": \"业务类型\",\n" +
                "\t\t\t\t\"fieldType\": \"varchar\",\n" +
                "\t\t\t\t\"variable\": \"var\",\n" +
                "\t\t\t\t\"rightFieldCode\": \"\",\n" +
                "\t\t\t\t\"rightFieldName\": \"\",\n" +
                "\t\t\t\t\"rightFiledType\": \"\",\n" +
                "\t\t\t\t\"value\": \"GUANLIANZIDUAN1\",\n" +
                "\t\t\t\t\"text1\": \"\",\n" +
                "\t\t\t\t\"text2\": \"\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"type\": \"group\",\n" +
                "\t\t\t\t\"id\": 4173879768,\n" +
                "\t\t\t\t\"logic\": \"OR\",\n" +
                "\t\t\t\t\"children\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"type\": \"field\",\n" +
                "\t\t\t\t\t\t\"id\": 1410306465,\n" +
                "\t\t\t\t\t\t\"operator\": \"ISNULL\",\n" +
                "\t\t\t\t\t\t\"fieldCode\": \"CHENGSHI\",\n" +
                "\t\t\t\t\t\t\"fieldName\": \"城市\",\n" +
                "\t\t\t\t\t\t\"fieldType\": \"varchar\",\n" +
                "\t\t\t\t\t\t\"variable\": \"var\",\n" +
                "\t\t\t\t\t\t\"rightFieldCode\": \"\",\n" +
                "\t\t\t\t\t\t\"rightFieldName\": \"\",\n" +
                "\t\t\t\t\t\t\"rightFiledType\": \"\",\n" +
                "\t\t\t\t\t\t\"value\": \"\",\n" +
                "\t\t\t\t\t\t\"text1\": \"\",\n" +
                "\t\t\t\t\t\t\"text2\": \"\"\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"type\": \"field\",\n" +
                "\t\t\t\t\t\t\"id\": 7582813133,\n" +
                "\t\t\t\t\t\t\"operator\": \"NOTNULL\",\n" +
                "\t\t\t\t\t\t\"fieldCode\": \"CHENGSHI\",\n" +
                "\t\t\t\t\t\t\"fieldName\": \"城市\",\n" +
                "\t\t\t\t\t\t\"fieldType\": \"varchar\",\n" +
                "\t\t\t\t\t\t\"variable\": \"const\",\n" +
                "\t\t\t\t\t\t\"rightFieldCode\": \"\",\n" +
                "\t\t\t\t\t\t\"rightFieldName\": \"\",\n" +
                "\t\t\t\t\t\t\"rightFiledType\": \"\",\n" +
                "\t\t\t\t\t\t\"value\": \"\",\n" +
                "\t\t\t\t\t\t\"text1\": \"\",\n" +
                "\t\t\t\t\t\t\"text2\": \"\"\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"type\": \"group\",\n" +
                "\t\t\t\t\"id\": 8155548503,\n" +
                "\t\t\t\t\"logic\": \"AND\",\n" +
                "\t\t\t\t\"children\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"type\": \"group\",\n" +
                "\t\t\t\t\t\t\"id\": 8757565566,\n" +
                "\t\t\t\t\t\t\"logic\": \"AND\",\n" +
                "\t\t\t\t\t\t\"children\": [\n" +
                "\t\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\t\"type\": \"field\",\n" +
                "\t\t\t\t\t\t\t\t\"id\": 1931459660,\n" +
                "\t\t\t\t\t\t\t\t\"operator\": \"BT\",\n" +
                "\t\t\t\t\t\t\t\t\"fieldCode\": \"UPDATE_BY_\",\n" +
                "\t\t\t\t\t\t\t\t\"fieldName\": \"更新人\",\n" +
                "\t\t\t\t\t\t\t\t\"fieldType\": \"varchar\",\n" +
                "\t\t\t\t\t\t\t\t\"variable\": \"const\",\n" +
                "\t\t\t\t\t\t\t\t\"rightFieldCode\": \"\",\n" +
                "\t\t\t\t\t\t\t\t\"rightFieldName\": \"\",\n" +
                "\t\t\t\t\t\t\t\t\"rightFiledType\": \"\",\n" +
                "\t\t\t\t\t\t\t\t\"value\": \"\",\n" +
                "\t\t\t\t\t\t\t\t\"text1\": \"30\",\n" +
                "\t\t\t\t\t\t\t\t\"text2\": \"500\"\n" +
                "\t\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\t\"type\": \"field\",\n" +
                "\t\t\t\t\t\t\t\t\"id\": 7998686865,\n" +
                "\t\t\t\t\t\t\t\t\"operator\": \"BE\",\n" +
                "\t\t\t\t\t\t\t\t\"fieldCode\": \"CHENGSHI\",\n" +
                "\t\t\t\t\t\t\t\t\"fieldName\": \"城市\",\n" +
                "\t\t\t\t\t\t\t\t\"fieldType\": \"varchar\",\n" +
                "\t\t\t\t\t\t\t\t\"variable\": \"CONST\",\n" +
                "\t\t\t\t\t\t\t\t\"rightFieldCode\": \"\",\n" +
                "\t\t\t\t\t\t\t\t\"rightFieldName\": \"\",\n" +
                "\t\t\t\t\t\t\t\t\"rightFiledType\": \"\",\n" +
                "\t\t\t\t\t\t\t\t\"value\": \"\",\n" +
                "\t\t\t\t\t\t\t\t\"text1\": \"GUANLIANZIDUAN1\",\n" +
                "\t\t\t\t\t\t\t\t\"text2\": \"GUANLIANZIDUAN4\"\n" +
                "\t\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t\t]\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"type\": \"field\",\n" +
                "\t\t\t\t\t\t\"id\": 5137928467,\n" +
                "\t\t\t\t\t\t\"operator\": \"GE\",\n" +
                "\t\t\t\t\t\t\"fieldCode\": \"CHENGSHI\",\n" +
                "\t\t\t\t\t\t\"fieldName\": \"城市\",\n" +
                "\t\t\t\t\t\t\"fieldType\": \"varchar\",\n" +
                "\t\t\t\t\t\t\"variable\": \"var\",\n" +
                "\t\t\t\t\t\t\"rightFieldCode\": \"\",\n" +
                "\t\t\t\t\t\t\"rightFieldName\": \"\",\n" +
                "\t\t\t\t\t\t\"rightFiledType\": \"\",\n" +
                "\t\t\t\t\t\t\"value\": \"GUANLIANZIDUAN4\",\n" +
                "\t\t\t\t\t\t\"text1\": \"\",\n" +
                "\t\t\t\t\t\t\"text2\": \"\"\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"type\": \"field\",\n" +
                "\t\t\t\t\"id\": 8745091376,\n" +
                "\t\t\t\t\"operator\": \"ISIN\",\n" +
                "\t\t\t\t\"fieldCode\": \"UPDATE_BY_\",\n" +
                "\t\t\t\t\"fieldName\": \"更新人\",\n" +
                "\t\t\t\t\"fieldType\": \"varchar\",\n" +
                "\t\t\t\t\"variable\": \"var\",\n" +
                "\t\t\t\t\"rightFieldCode\": \"\",\n" +
                "\t\t\t\t\"rightFieldName\": \"\",\n" +
                "\t\t\t\t\"rightFiledType\": \"\",\n" +
                "\t\t\t\t\"value\": \"1,2,3,45\",\n" +
                "\t\t\t\t\"text1\": \"\",\n" +
                "\t\t\t\t\"text2\": \"\"\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "]";*/

        String json = "[{\"type\":\"group\",\"id\":9300436961,\"logic\":\"AND\",\"children\":[{\"type\":\"field\",\"id\":2738285784,\"operator\":\"EQ\",\"fieldCode\":\"CHUCHAMUDI\",\"fieldName\":\"出差目的\",\"fieldType\":\"varchar\",\"variable\":\"const\",\"rightFieldCode\":\"\",\"rightFieldName\":\"\",\"rightFiledType\":\"\",\"value\":\"没有目的\",\"text1\":\"\",\"text2\":\"\"},{\"type\":\"field\",\"id\":6707225108,\"operator\":\"EQ\",\"fieldCode\":\"CHENGSHI\",\"fieldName\":\"城市\",\"fieldType\":\"varchar\",\"variable\":\"const\",\"rightFieldCode\":\"\",\"rightFieldName\":\"\",\"rightFiledType\":\"\",\"value\":\"衡阳\",\"text1\":\"\",\"text2\":\"\"}]}]";

        json = StringEscapeUtils.unescapeJava(json);//JSON.parseObject(json,String.class);
        System.out.println(StringEscapeUtils.unescapeJava(json));
        String mapJson = "[{\"CHENGSHI\":\"西安\",\"DANJUSHIYOU\":\"测试\",\"ID_\":\"983413913687175168\",\"CREATE_TIME_\":\"2022-06-06 16:55:47\",\"IP_\":\"\",\"YEWULEIXING\":\"水费\",\"UPDATE_BY_\":\"\",\"CHUCHAMUDI\":\"没有目的\",\"CREATE_BY_\":\"4743913001P0005998\",\"TENANT_ID_\":\"\"},{\"CHENGSHI\":\"重庆\",\"DANJUSHIYOU\":\"测试\",\"ID_\":\"983414364306419712\",\"CREATE_TIME_\":\"2022-06-06 16:57:35\",\"IP_\":\"\",\"YEWULEIXING\":\"水费\",\"UPDATE_BY_\":\"\",\"CHUCHAMUDI\":\"没有目的\",\"CREATE_BY_\":\"4743913001P0005998\",\"TENANT_ID_\":\"\"},{\"CHENGSHI\":\"衡阳\",\"DANJUSHIYOU\":\"测试\",\"ID_\":\"983414579058978816\",\"CREATE_TIME_\":\"2022-06-06 16:58:26\",\"IP_\":\"\",\"YEWULEIXING\":\"水费\",\"UPDATE_BY_\":\"\",\"CHUCHAMUDI\":\"没有目的\",\"CREATE_BY_\":\"4743913001P0005998\",\"TENANT_ID_\":\"\"},{\"CHENGSHI\":\"上海\",\"DANJUSHIYOU\":\"测试\",\"ID_\":\"983415018596872192\",\"CREATE_TIME_\":\"2022-06-06 17:00:11\",\"IP_\":\"\",\"YEWULEIXING\":\"水费\",\"UPDATE_BY_\":\"\",\"CHUCHAMUDI\":\"有目的\",\"CREATE_BY_\":\"4743913001P0005998\",\"TENANT_ID_\":\"\"}]";

        mapJson = "[{\"CHENGSHI\":\"西安\",\"DANJUSHIYOU\":\"测试\",\"ID_\":\"983413913687175168\",\"CREATE_TIME_\":\"2022-06-06 16:55:47\",\"IP_\":\"\",\"YEWULEIXING\":\"水费\",\"UPDATE_BY_\":\"\",\"CHUCHAMUDI\":\"没有目的\",\"CREATE_BY_\":\"4743913001P0005998\",\"TENANT_ID_\":\"\"},{\"CHENGSHI\":\"重庆\",\"DANJUSHIYOU\":\"测试\",\"ID_\":\"983414364306419712\",\"CREATE_TIME_\":\"2022-06-06 16:57:35\",\"IP_\":\"\",\"YEWULEIXING\":\"水费\",\"UPDATE_BY_\":\"\",\"CHUCHAMUDI\":\"没有目的\",\"CREATE_BY_\":\"4743913001P0005998\",\"TENANT_ID_\":\"\"},{\"CHENGSHI\":\"衡阳\",\"DANJUSHIYOU\":\"测试\",\"ID_\":\"983414579058978816\",\"CREATE_TIME_\":\"2022-06-06 16:58:26\",\"IP_\":\"\",\"YEWULEIXING\":\"水费\",\"UPDATE_BY_\":\"\",\"CHUCHAMUDI\":\"没有目的\",\"CREATE_BY_\":\"4743913001P0005998\",\"TENANT_ID_\":\"\"},{\"CHENGSHI\":\"上海\",\"DANJUSHIYOU\":\"测试\",\"ID_\":\"983415018596872192\",\"CREATE_TIME_\":\"2022-06-06 17:00:11\",\"IP_\":\"\",\"YEWULEIXING\":\"水费\",\"UPDATE_BY_\":\"\",\"CHUCHAMUDI\":\"有目的\",\"CREATE_BY_\":\"4743913001P0005998\",\"TENANT_ID_\":\"\"}]";
        Map<String,Object> paramMapTemp = JSON.parseObject(StringEscapeUtils.unescapeJava("{\"FK001_GUANLIANZIDUAN1\":\"测试\",\"FK001_GUANLIANZIDUAN2\":\"水费\",\"DSF_DATA_CHUCHAMUDI\":\"没有目的\",\"FK001_GUANLIANZIDUAN3\":\"\",\"DSF_DATA_CHENGSHI\":\"西安\",\"DSF_DATA_IP_\":\"\",\"currentDate\":\"2022-06-06 20:03:17\",\"currentMonth\":\"06\",\"MAX$DSF_DATA_CREATE_TIME__\":\"2022-06-06 17:00:11\",\"DSF_DATA_DANJUSHIYOU\":\"测试\",\"currentYear\":2022,\"DSF_DATA_YEWULEIXING\":\"水费\",\"DSF_DATA_TENANT_ID_\":\"\",\"DSF_DATA\":[{\"CHENGSHI\":\"西安\",\"DANJUSHIYOU\":\"测试\",\"ID_\":\"983413913687175168\",\"CREATE_TIME_\":\"2022-06-06 16:55:47\",\"IP_\":\"\",\"YEWULEIXING\":\"水费\",\"UPDATE_BY_\":\"\",\"CHUCHAMUDI\":\"没有目的\",\"CREATE_BY_\":\"4743913001P0005998\",\"TENANT_ID_\":\"\"},{\"CHENGSHI\":\"重庆\",\"DANJUSHIYOU\":\"测试\",\"ID_\":\"983414364306419712\",\"CREATE_TIME_\":\"2022-06-06 16:57:35\",\"IP_\":\"\",\"YEWULEIXING\":\"水费\",\"UPDATE_BY_\":\"\",\"CHUCHAMUDI\":\"没有目的\",\"CREATE_BY_\":\"4743913001P0005998\",\"TENANT_ID_\":\"\"},{\"CHENGSHI\":\"衡阳\",\"DANJUSHIYOU\":\"测试\",\"ID_\":\"983414579058978816\",\"CREATE_TIME_\":\"2022-06-06 16:58:26\",\"IP_\":\"\",\"YEWULEIXING\":\"水费\",\"UPDATE_BY_\":\"\",\"CHUCHAMUDI\":\"没有目的\",\"CREATE_BY_\":\"4743913001P0005998\",\"TENANT_ID_\":\"\"},{\"CHENGSHI\":\"上海\",\"DANJUSHIYOU\":\"测试\",\"ID_\":\"983415018596872192\",\"CREATE_TIME_\":\"2022-06-06 17:00:11\",\"IP_\":\"\",\"YEWULEIXING\":\"水费\",\"UPDATE_BY_\":\"\",\"CHUCHAMUDI\":\"有目的\",\"CREATE_BY_\":\"4743913001P0005998\",\"TENANT_ID_\":\"\"}],\"FK001\":[{\"GUANLIANZIDUAN1\":\"测试\",\"GUANLIANZIDUAN2\":\"水费\"}],\"DSF_DATA_UPDATE_BY_\":\"\",\"currentUserId\":\"4743913001P0005998\",\"MIN$DSF_DATA_CREATE_TIME__\":\"2022-06-06 16:55:47\",\"DSF_DATA_CREATE_TIME_\":\"2022-06-06 16:55:47\",\"DSF_DATA_ID_\":\"983413913687175168\",\"FK001_BE_EVER_ROW_DATA_ID_\":\"\",\"DSF_DATA_CREATE_BY_\":\"4743913001P0005998\",\"$123\":0.00}"),Map.class);

        List<Entity> entityList = new ArrayList<>();

        entityList.add(new Entity("10","40","yes",new Date()));
        entityList.add(new Entity("20","60","yes",new Date()));
        entityList.add(new Entity("30","80","yes",new Date()));
        entityList.add(new Entity("40","90","no",new Date()));
        entityList.add(new Entity("50","110","yes",new Date()));
        entityList.add(new Entity("60","140","yes",new Date()));
        entityList.add(new Entity("70","160","yes",new Date()));
        entityList.add(new Entity("80","180","no",new Date()));
        entityList.add(new Entity("90","240","yes",new Date()));
        entityList.add(new Entity("100","340","yes",new Date()));
        entityList.add(new Entity("110","440","yes",new Date()));
        entityList.add(new Entity("120","540","yes",new Date()));

        FaceDataHandlerInter pre = new FaceDataHandlerImpl();

        Map<String , Object> paramMap = new HashMap<>();

        paramMap.put("bcd","60");
        paramMap.put("abc","20");
        paramMap.put("ggg","30");
        paramMap.put("eef","80");


        try {
            List<Entity> result = pre.handler(entityList, paramMap, rule);

            for (Entity entity : result){

                System.out.println(JSON.toJSONString(entity));

            }

            List<Map> resultMap = JSON.parseArray(mapJson,Map.class);
            resultMap = pre.handler(resultMap, paramMapTemp, json);

            System.out.println(JSON.toJSONString("------------------------------"+resultMap));

            resultMap = PageResultListHandlerUtil.pageList(resultMap,"0",0,5);

            for (Map entity : resultMap){

                System.out.println(JSON.toJSONString(entity));

            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        // 结束时间
        long etime = System.currentTimeMillis();
        // 计算执行时间
        System.out.println("执行时长：" + (etime - stime) + " 毫秒.");

        String where = pre.sqlWhereHandler(paramMap,rule);

        System.out.println(where);

//        String where = "(((abc is null) or (bcd between 30.0 and 80.0 ) or (cde = 'yes'))) OR";

        if (where == null || where.equals("")) {
            return;
        }

        int first = where.indexOf("(");
        int last = where.lastIndexOf(")");
        System.out.println(where.substring(first,last+1));*/

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2022-06-09 11:11:23");
//        Date date1 = format.parse("1654744283000");
        System.out.println(format.format(date));

        Date date1 = format.parse(format.format(date));
        System.out.println(format.format(date1));

    }

    static class Entity{

        private String abc;

        private String bcd;

        private String cde;

        private Date date;

        public Entity(){}

        public Entity(String abc,String bcd , String cde,Date date){

            this.abc = abc;
            this.bcd = bcd;
            this.cde = cde;
            this.date = date;

        }

        public String getAbc() {
            return abc;
        }

        public void setAbc(String abc) {
            this.abc = abc;
        }

        public String getBcd() {
            return bcd;
        }

        public void setBcd(String bcd) {
            this.bcd = bcd;
        }

        public String getCde() {
            return cde;
        }

        public void setCde(String cde) {
            this.cde = cde;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}
