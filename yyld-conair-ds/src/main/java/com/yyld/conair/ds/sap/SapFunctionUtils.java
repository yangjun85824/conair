package com.yyld.conair.ds.sap;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoTable;
import com.yyld.conair.commons.data.utils.SnowFlakeUtil;
import com.yyld.conair.ds.sap.jco.SapFunction;
import com.yyld.conair.ds.sap.jco.SapFunctionResult;
import com.yyld.conair.ds.sap.jco.SapManager;
import com.yyld.conair.ds.sap.jco.SapManagerBuilderOption;
import com.yyld.conair.ds.sap.util.Constants;
import com.yyld.conair.ds.sap.util.FunctionParamsVO;
import com.yyld.conair.ds.sap.util.FunctionParamsVO.Params;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public final class SapFunctionUtils {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static JCoFunction sapFunction;
    //开发环境
    //public static SapManager getSapManager() throws JCoException, IOException {
    //    return SapManager
    //            .builder()
    //            .set(SapManagerBuilderOption.ASHOST, "10.111.4.1") // AS host
    //            .set(SapManagerBuilderOption.MSSERV, "669") // MS port [AS, MS is MSSERV, GW is JCO_GWSERV]
    //            .set(SapManagerBuilderOption.SYSNR, "51") // system number
    //            .set(SapManagerBuilderOption.GROUP, "DEV") // group
    //            .set(SapManagerBuilderOption.LANG, "ZH") // language code
    //            .set(SapManagerBuilderOption.CLIENT, "669") // client number
    //            .set(SapManagerBuilderOption.USER, "caohm") // user
    //            .set(SapManagerBuilderOption.PASSWD, "Aa@123456") // password
    //            .build();
    //}
    //测试环境
    //public static SapManager getSapManager() throws JCoException, IOException {
    //    return SapManager
    //            .builder()
    //            .set(SapManagerBuilderOption.ASHOST, "10.110.9.132") // AS host
    //            .set(SapManagerBuilderOption.MSSERV, "669") // MS port [AS, MS is MSSERV, GW is JCO_GWSERV]
    //            .set(SapManagerBuilderOption.SYSNR, "24") // system number
    //            .set(SapManagerBuilderOption.GROUP, "Q24") // group
    //            .set(SapManagerBuilderOption.LANG, "ZH") // language code
    //            .set(SapManagerBuilderOption.CLIENT, "669") // client number
    //            .set(SapManagerBuilderOption.USER, "YANGYD") // user
    //            .set(SapManagerBuilderOption.PASSWD, "Ylc@123456") // password
    //            .build();
    //}

    //生产环境
    //properties.setProperty(DestinationDataProvider.JCO_ASHOST, "10.113,1.26");//sap服务器地址
    //properties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");//系统编号，找SAP核对写00就可以了
    //properties.setProperty(DestinationDataProvider.JCO_CLIENT, "669");//集团号，不知道就问你们的sap basis
    //properties.setProperty(DestinationDataProvider.JCO_USER, "CHENXI.YANG5");//帐号
    //properties.setProperty(DestinationDataProvider.JCO_PASSWD, "5186421xx@DD");//密码
    //properties.setProperty(DestinationDataProvider.JCO_LANG, "zh");//语言
    public static SapManager getSapManager_dev() throws JCoException, IOException {
        System.out.println("10.110.116.65");
        return SapManager
                .builder()
                .set(SapManagerBuilderOption.ASHOST, "10.110.116.65") // AS host
                //.set(SapManagerBuilderOption.MSSERV, "669") // MS port [AS, MS is MSSERV, GW is JCO_GWSERV]
                .set(SapManagerBuilderOption.SYSNR, "00") // system number
                .set(SapManagerBuilderOption.GROUP, "QAS") // group
                .set(SapManagerBuilderOption.LANG, "ZH") // language code
                .set(SapManagerBuilderOption.CLIENT, "669") // client number
                .set(SapManagerBuilderOption.USER, "YANGYD") // user
                .set(SapManagerBuilderOption.PASSWD, "Qas@2023") // password
                .build();
    }

    public static SapManager getSapManager_dev_64() throws JCoException, IOException {
        System.out.println("10.110.116.64");
        return SapManager
                .builder()
                .set(SapManagerBuilderOption.ASHOST, "10.110.116.64") // AS host
                //.set(SapManagerBuilderOption.MSSERV, "669") // MS port [AS, MS is MSSERV, GW is JCO_GWSERV]
                .set(SapManagerBuilderOption.SYSNR, "00") // system number
                .set(SapManagerBuilderOption.GROUP, "DEV") // group
                .set(SapManagerBuilderOption.LANG, "ZH") // language code
                .set(SapManagerBuilderOption.CLIENT, "669") // client number
                .set(SapManagerBuilderOption.USER, "YANGYD") // user
                .set(SapManagerBuilderOption.PASSWD, "Qas@1234567") // password
                .build();
    }

    public static SapManager getSapManager_new() throws JCoException, IOException {
        System.out.println("10.110.116.66");
        return SapManager
                .builder()
                .set(SapManagerBuilderOption.ASHOST, "10.110.116.66") // AS host
                //.set(SapManagerBuilderOption.MSSERV, "669") // MS port [AS, MS is MSSERV, GW is JCO_GWSERV]
                .set(SapManagerBuilderOption.SYSNR, "00") // system number
                //.set(SapManagerBuilderOption.GROUP, "PRD") // group
                //.set(SapManagerBuilderOption.SAPROUTER,"44300")
                .set(SapManagerBuilderOption.LANG, "ZH") // language code
                .set(SapManagerBuilderOption.CLIENT, "669") // client number
                .set(SapManagerBuilderOption.USER, "ZHANGHONGYAO") // user
                .set(SapManagerBuilderOption.PASSWD, "Jgdt@2023") // password
                .build();
    }

    public static List<Map<String, Object>> function(FunctionParamsVO paramsVO) {

        System.out.println("==========================PRD===================");
        List<Map<String, Object>> returnList = Lists.newArrayList();
        try {
            // connect
            SapManager sap = getSapManager_new();
            // load sap function
            SapFunction function = sap.getFunction(paramsVO.getFunctionName());

            //JCoParameterList imP = function.getImportParameterList();
            //利用setValue将入参参数传入
            List<Params> pList = paramsVO.getParamsList();
            if (CollectionUtils.isNotEmpty(pList)) {
                pList.forEach(vo -> {
                    String name = vo.getName();
                    Object value = vo.getValue();
                    String type = Constants.PARAM_FILED_.get(name);
                    if (StringUtils.isNotBlank(type) && value != null) {
                        function.getImportParameterList().setValue(name, StrToDate(value.toString()));
                    } else {
                        function.getImportParameterList().setValue(vo.getName(), vo.getValue());
                    }

                });
            }
            SapFunctionResult functionResult = function.execute();
            //获取数据 方式一
            returnList = functionResult.getTable(paramsVO.getResultTable());
            sapFunction = function.getFunction();
            System.out.println("======PRD================================size : " + (returnList == null ? 0 : returnList.size()));

        } catch (JCoException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return returnList;

    }

    public static JCoFunction getSapFunction() {
        return sapFunction;
    }

    public static Map<String, Object> resultHandler(SapFunction function, FunctionParamsVO paramsVO) {
        // 按照需求可以整理出一条一条的数据以便插入数据库
        JCoTable table = function.getImportTableParameter(paramsVO.getResultTable());

        // 获取metaData(包含表的关键信息)
        JCoRecordMetaData metaData = table.getRecordMetaData();
        Map<String, Object> sapData = new HashMap<String, Object>();
        sapData.put("FieldCount", metaData.getFieldCount());
        String[] name = new String[Integer.parseInt(sapData.get("FieldCount").toString())];
        List<Map<String, String>> sapList = new ArrayList<Map<String, String>>();
        // 获取全部名称
        for (int j = 0; j < Integer.parseInt(sapData.get("FieldCount").toString()); j++) {
            name[j] = metaData.getName(j);
        }
        sapData.put("FieldNames", name);
        // 获取全部数据
        System.out.println("###" + table);
        for (int i = 0; i < table.getNumRows(); i++) {
            table.setRow(i);
            Map<String, String> sapMap = new HashMap<String, String>();
            for (String fieldName : name) {
                sapMap.put(fieldName, table.getString(fieldName));
            }
            sapList.add(sapMap);
        }
        sapData.put("Data", sapList);

        return sapData;
    }

    //调用示例
    public static void main(String[] args) throws Exception {

        //List<Pm> pms = Lists.newArrayList();
        //pms.add(new Pm("7000","2023","1500000511",null));
        //pms.add(new Pm("7000","2023","1500000834",null));
        //pms.add(new Pm("7000","2023","1500000279",null));
        //pms.add(new Pm("7000","2023","1500000489",null));
        //pms.add(new Pm("7078","2023","0100000032",null));
        ////pms.add(new Pm("7078","2023","0100000032",null));
        //pms.add(new Pm("7000","2023","1500001304",null));
        //pms.add(new Pm("7000","2023","1500001431",null));
        //pms.add(new Pm("7002","2023","1500000561",null));
        //pms.add(new Pm("7010","2023","1500000059",null));
        //pms.add(new Pm("7010","2023","1500000078",null));
        //pms.add(new Pm("7013","2023","1500000058",null));
        //pms.add(new Pm("7013","2023","1500000080",null));
        //pms.add(new Pm("7900","2023","0100000553",null));
        //pms.add(new Pm("7900","2023","1500000357",null));
        //pms.add(new Pm("7000","2023","1500000050",null));
        //pms.add(new Pm("7000","2023","1500000083",null));
        //pms.add(new Pm("7000","2023","1500000109",null));
        //pms.add(new Pm("7000","2023","1500000212",null));
        //pms.add(new Pm("7901","2023","0100000031",null));
        //pms.add(new Pm("7000","2023","1500001010",null));
        //pms.add(new Pm("7000","2023","1500001055",null));
        //pms.add(new Pm("7000","2023","1500001236",null));
        //pms.add(new Pm("7000","2023","1500001237",null));
        //pms.add(new Pm("7000","2023","1500001238",null));
        //
        //FunctionParamsVO vo = null;
        //
        //for (Pm m : pms) {
        //
        //    vo = new FunctionParamsVO("ZCD_SAP_BE_BSEG_N", "T_BSEG");
        //
        //    List<Params> paramList = paramList = Lists.newArrayList();
        //    paramList.add(new Params("I_BUKRS", m.getBukrs()));
        //    paramList.add(new Params("I_BELNR", m.getBelnr()));
        //    paramList.add(new Params("I_GJAHR", m.getGjahr()));
        //    paramList.add(new Params("I_BLDAT", m.getMonat()));
        //    paramList.add(new Params("I_CPUDT_FROM", "2023.01.01"));
        //    paramList.add(new Params("I_CPUDT_TO", "2023.06.30"));
        //
        //    vo.setParamsList(paramList);
        //
        //    List<Map<String, Object>> resultMapList = function(vo);
        //    System.out.println(m.getBelnr() + "======" + resultMapList.size());
        //    if (CollectionUtils.isEmpty(resultMapList)) {
        //        continue;
        //    }
        //    System.out.println(JSON.toJSONString(resultMapList.get(0)));
        //    System.out.println(resultMapList.size());
        //    insertBatch(resultMapList);
        //}

        //FunctionParamsVO vo = new FunctionParamsVO("ZCD_SAP_BE_BSEG_N", "T_BSEG");
        //
        //List<Params> paramList = paramList = Lists.newArrayList();
        //paramList.add(new Params("I_BUKRS", null));
        //paramList.add(new Params("I_BELNR", null));
        //paramList.add(new Params("I_GJAHR", null));
        //paramList.add(new Params("I_BLDAT", null));
        //paramList.add(new Params("I_CPUDT_FROM", "2023.06.16"));
        //paramList.add(new Params("I_CPUDT_TO", "2023.06.16"));
        //
        //vo.setParamsList(paramList);
        //new FunctionParamsVO("ZCD_SAP_BE_BSEG_ZC", "T_BSEG");
        //7073	5000000001
        //7000	5000000170
        //7000	5000000185
        //7000	5000000241
        //7067	5000000008
        //7067	5000000009
        //7016	5000000001

        FunctionParamsVO vo = new FunctionParamsVO("ZCD_SAP_BE_BSEG_ZC", "T_BSEG");

        List<Params> paramList = paramList = Lists.newArrayList();
        paramList.add(new FunctionParamsVO.Params("I_BUKRS", "7001"));
        paramList.add(new FunctionParamsVO.Params("I_BELNR", "0100002537"));
        paramList.add(new FunctionParamsVO.Params("I_GJAHR", "2023"));
        paramList.add(new FunctionParamsVO.Params("I_MONAT", null));
        paramList.add(new FunctionParamsVO.Params("I_BUDAT", null));
        paramList.add(new FunctionParamsVO.Params("I_BLDAT", null));
        paramList.add(new FunctionParamsVO.Params("I_CPUDT_FROM", null));
        paramList.add(new FunctionParamsVO.Params("I_CPUDT_TO", null));
        paramList.add(new FunctionParamsVO.Params("I_CPUTM_FROM", null));
        paramList.add(new FunctionParamsVO.Params("I_CPUTM_TO", null));

        vo.setParamsList(paramList);

        List<Map<String, Object>> resultMapList = function(vo);
        System.out.println("======" + resultMapList.size());
        if (CollectionUtils.isEmpty(resultMapList)) {
            return;
        }
        System.out.println(JSON.toJSONString(resultMapList.get(0)));
        System.out.println(resultMapList.size());
        insertBatch(resultMapList);
    }

    private static void insertBatch(List<Map<String, Object>> resultMapList) throws Exception {

        String sql = "INSERT INTO `itds`(`ID_`, `PARENT_ID_`, `TENANT_ID_`, `IP_`, `CREATE_BY_`, `CREATE_TIME_`, `UPDATE_BY_`, `UPDATE_TIME_`";//`BUKRS`, `BUTXT`, `BELNR`, `GJAHR`, `BUZEI`, `BLART`, `BLDAT`, `BUDAT`, `MONAT`, `CPUDT`, `USNAM`, `TCODE`, `XBLNR`, `STBLG`, `BKTXT`, `XBLNR_ALT`, `SHKZG`, `MWSKZ`, `DMBTR`, `ZUONR`, `HKONT`, `TXT20`, `VBUND`, `NAME3`, `KUNNR`, `NAME1`, `LIFNR`, `NAME2`, `VBEL2`, `EBELN`, `EBELP`, `RSTGR`, `ANLN1`, `XREF1`, `XREF2`, `XREF3`, `XNEGP`, `ZYWFL`, `ZZTEST`, `ZZFIELD1`, `ZZFIELD2`, `ZZFIELD3`, `ZZFIELD4`, `ZZFIELD5`, `IT_FLAG`, `ASGN_FLAG`, `ZKMJQ`, `VRA_STATUS_`, `VRA_OBJ_ID`, `DIFF`, `TAX_DIFF`, `DMBTR_SH`, `TXT40`, `HANDLE_STAUS_01`, `HANDLE_STAUS_02`, `HANDLE_STAUS_03`, `ZYWM`, `HANDLE_STAUS_04`, `CURRENCY_`, `REPORT_CODE`, `REPORT_NAME`, `DATA_KEY_`, `CODE_NAME`, `CODE_DZ`, `CODE_DZ_NAME`, `REMARKS`, `STATUS_05`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil(1, 2);
        Map m = resultMapList.get(0);
        //过滤掉不需要的数据
        m.remove("CPUTM");

        //把map的值转换为list集合
        List<String> kyList = new ArrayList(m.keySet());
        String fileds = String.join(",", kyList);
        String w = "";
        for (String s : kyList) {
            w = w + ",?";
        }
        sql = sql + "," + fileds + ") values (?,?,?,?,?,?,?,?" + w + ")";
        System.out.println(sql);
        Connection conn = mysql();
        PreparedStatement ps = null;
        try {
            int size = kyList.size();
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);//取消自动提交
            for (Map map : resultMapList) {
                ps.setObject(1, snowFlakeUtil.getNextId());
                ps.setObject(2, null);
                ps.setObject(3, null);
                ps.setObject(4, null);
                ps.setObject(5, null);
                ps.setObject(6, null);
                ps.setObject(7, null);
                ps.setObject(8, null);

                for (int i = 0; i < size; i++) {
                    //System.out.println(i+"+++++"+kyList.get(i)+"============"+map.get(kyList.get(i)));
                    ps.setObject(i + 9, map.get(kyList.get(i)));
                }
                ps.addBatch();
                //if (i % 500 == 0) {
                //    ps.executeBatch();
                //    ps.clearBatch();
                //}
            }
            ps.executeBatch();
            ps.clearBatch();
            conn.commit();//所有语句都执行完毕后才手动提交sql语句
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        System.out.println();
    }

    private static Connection mysql() throws ClassNotFoundException, SQLException {
        final String url = "jdbc:mysql://localhost:3306/test?rewriteBatchedStatements=true";
        final String name = "com.mysql.jdbc.Driver";
        final String user = "root";   // 数据库账户名
        final String password = "root"; // 数据库密码
        Connection conn = null;

        Class.forName(name);//指定连接类型
        conn = DriverManager.getConnection(url, user, password);//获取连接
        if (conn != null) {
            System.out.println("获取连接成功");
        } else {
            System.out.println("获取连接失败");
        }
        return conn;

    }

    /**
     * 字符串转换成日期
     * `1
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        if (StringUtils.equals("", str)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private static Date setDate(Object obj) {
        if (!Objects.isNull(obj) && obj instanceof Date) {

            Date dd = (Date) obj;
            Long l = dd.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT' yyyy", Locale.ENGLISH);
            Date date = null;
            try {
                //date = sdf.parse(obj.toString());
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = sdf.parse(sdf.format(l));
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
        return null;
    }

    @Data
    static
    class Pm{

        private String bukrs;
        private String gjahr;
        private String belnr;
        private String monat;

        public Pm(String bukrs,String gjahr,String belnr,String monat){
            this.bukrs = bukrs;
            this.gjahr = gjahr;
            this.belnr = belnr;
            this.monat = monat;
        }

    }
}
