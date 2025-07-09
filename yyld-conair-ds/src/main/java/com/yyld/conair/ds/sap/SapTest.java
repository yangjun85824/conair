package com.yyld.conair.ds.sap;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sap.conn.jco.*;
import com.yyld.conair.ds.sap.util.Constants;
import com.yyld.conair.ds.sap.util.FunctionParamsVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SapTest{

    public static void test(FunctionParamsVO paramsVO) {
        String message = null;
        try {
            JCoDestination jCoDestination = null;// 连接对象
            JCoFunction function = null;// 调用rfc函数对象
            try {
                long startTime=System.currentTimeMillis();   //获取开始时间
                jCoDestination=GetSapConn.getJcoConnection();
                function = jCoDestination.getRepository().getFunctionTemplate(paramsVO.getFunctionName()).getFunction();
                if (null == function) {
                    throw new RuntimeException("get function not found in sap");
                } else {
                    JCoParameterList paramList = function.getImportParameterList();
                    //【sap系统传入参数,有多个的话就写多行 LV_BANKA sap定义的函数参数名 有多个的话就写多行】
                    paramsVO.getParamsList().forEach(vo -> {
                        String name = vo.getName();
                        Object value = vo.getValue();
                        String type = Constants.PARAM_FILED_.get(name);
                        if (StringUtils.isNotBlank(type) && value != null){
                            paramList.setValue(name, StrToDate(value.toString()));
                        }else {
                            paramList.setValue(vo.getName(), vo.getValue());
                        }

                    });
                    //如果传如参数是内表的形式的话就以如下代码传入sap系统
                    //  JCoTable table1 = function.getTableParameterList().getTable("I_EKPO");
                    //  for (int i = 0; i < purchaseNo.size(); i++) {
                    // table1.setRow(i);
                    // table1.appendRow();
                    // table1.setValue("EBELN", purchaseNo.get(i)[0]);
                    // table1.setValue("EBELP", purchaseNo.get(i)[1]);
                    //  }
                    function.execute(jCoDestination);//执行调用函数
                    JCoTable table = function.getTableParameterList().getTable(paramsVO.getResultTable());//[ES_DATA 是SAP返回 数据的“表名字”]得到sap返回的参数，你就把他当作c语言的结构体理解就可以了
                    long endTime=System.currentTimeMillis(); //获取结束时间
                    System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
                    //有时候sap那边只是返回一个输出参数，sap比方说你这边输入一个物料号，想得到sap那边的物料描述，这是sap方是不会返回一个内表给你的
                    //而是只是返回一个输出参数给你这时你就要用到下面的方法来得到输出参数
                    //paramList = function.getExportParameterList();
                    //paramList.getString("rfc返回字段字段名称");
                    List<Map<String,Object>> list = Lists.newArrayList();
                    //for (int i = 0; i < table.getNumRows(); i++) {
                    //    table.setRow(i);
                    //    //这里获取sap函数传出内表结构的字段 【传入的字符串为调用函数需要传入的参数名，必须为大写】
                    //    //String BANKL = table.getString("BANKL");//【表结果里面的字段】记住这里BANKL一定是大写的，不然得不到值
                    //    //String BANKA = table.getString("BANKA");//【表结果里面的字段】记住这里BANKA一定是大写的，不然得不到值
                    //    //System.out.println("BANKL:"+BANKL  +"------"+ "BANKA"+BANKA);
                    //    //得到了sap数据，然后下面就是你java擅长的部分了，想封装成什么类型的都由你
                    //    Map<String,Object> map = Maps.newHashMap();
                    //
                    //    Streams.toStream(table, false).forEach(field -> map.put(field.getName(), field.getValue()));
                    //    list.add(map);
                    //    System.out.println(i);
                    //}

                    //获取表体
                    //JCoTable每一行都是一个JCoStructure，可以通过setRow()设置指针的位置，然后再遍历各个field：
                    int tableRows = table.getNumRows();
                    startTime=System.currentTimeMillis();   //获取开始时间

                    for(int i = 0; i < tableRows; i++){
                        // Sets the row pointer to the specified position(beginning from zero)
                        table.setRow(i);//每次一张表

                        // Each line is of type JCoStructure
                        Map<String,Object> map = Maps.newHashMap();
                        for(JCoField field : table){
                            map.put(field.getName(), field.getValue());
                        }
                        list.add(map);
                        //System.out.println(JSON.toJSONString(map));
                    }
                    //threadTable(table,20);

                    endTime=System.currentTimeMillis(); //获取结束时间

                    System.out.println("程序运行时间： "+(endTime-startTime)+"ms"+list.size());

                    System.out.println();
                }
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } finally {
                jCoDestination = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "失败";
        }
    }

    public static void main(String[] args) {

        //ZCD_SAP_BE_KNA1	SAP-BEever客户主数据接口
        FunctionParamsVO vo = new FunctionParamsVO("ZCD_SAP_BE_KNA1", "T_KNA1");

        List<FunctionParamsVO.Params> paramList = Lists.newArrayList();
        paramList.add(new FunctionParamsVO.Params("I_KUNNR", null));
        paramList.add(new FunctionParamsVO.Params("I_FROMDATE", "2022.12.01"));
        paramList.add(new FunctionParamsVO.Params("I_TODATE", "2022.12.31"));

        vo.setParamsList(paramList);

        System.out.println(JSON.toJSONString(vo));
        //function(vo);

        //ZCD_SAP_BE_LFA1	SAP-BEever供应商主数据接口
        vo = new FunctionParamsVO("ZCD_SAP_BE_LFA1", "T_LFA1");

        paramList = Lists.newArrayList();
        paramList.add(new FunctionParamsVO.Params("I_LIFNR", null));
        paramList.add(new FunctionParamsVO.Params("I_FROMDATE", "2022.12.01"));
        paramList.add(new FunctionParamsVO.Params("I_TODATE", "2022.12.31"));

        vo.setParamsList(paramList);

        System.out.println(JSON.toJSONString(vo));
        //function(vo);

        //ZCD_SAP_BE_BSEG	SAP与Beever对账平台财务凭证接口
        vo = new FunctionParamsVO("ZCD_SAP_BE_BSEG_N", "T_BSEG");

        paramList = Lists.newArrayList();
        paramList.add(new FunctionParamsVO.Params("I_BUKRS", null));
        paramList.add(new FunctionParamsVO.Params("I_BELNR", null));
        paramList.add(new FunctionParamsVO.Params("I_GJAHR", null));
        paramList.add(new FunctionParamsVO.Params("I_BLDAT", null));
        paramList.add(new FunctionParamsVO.Params("I_CPUDT_FROM", "2022.12.31"));
        paramList.add(new FunctionParamsVO.Params("I_CPUDT_TO", "2022.12.31"));

        vo.setParamsList(paramList);

        System.out.println(JSON.toJSONString(vo));
        //function(vo);
        //test(vo);

        vo = new FunctionParamsVO("ZCD_SAP_BE_BSEG_ZC", "T_BSEG");

        paramList = Lists.newArrayList();
        paramList.add(new FunctionParamsVO.Params("I_BUKRS", null));
        paramList.add(new FunctionParamsVO.Params("I_BELNR", null));
        paramList.add(new FunctionParamsVO.Params("I_GJAHR", "2023"));
        paramList.add(new FunctionParamsVO.Params("I_MONAT", null));
        paramList.add(new FunctionParamsVO.Params("I_BUDAT", null));
        paramList.add(new FunctionParamsVO.Params("I_BLDAT", null));
        paramList.add(new FunctionParamsVO.Params("I_CPUDT_FROM", null));
        paramList.add(new FunctionParamsVO.Params("I_CPUDT_TO", null));
        paramList.add(new FunctionParamsVO.Params("I_CPUTM_FROM", null));
        paramList.add(new FunctionParamsVO.Params("I_CPUTM_TO", null));

        vo.setParamsList(paramList);
        test(vo);
        //long start = System.currentTimeMillis();
        //List<Integer> list = new ArrayList();
        //for (int i = 0; i < 1000; i++) {
        //    list.add(i);
        //}
        ////定义线程数量为20，可根据服务器配置适当调整大小
        ////Thread(list, 20);
        //long end = System.currentTimeMillis();
        //System.out.println("总时间 = " + (end - start));

    }

    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        if (StringUtils.isNotBlank(str)){
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

    //此处有加锁，不需要的同学可以自行改造
    public synchronized static <T> void threadTable(JCoTable table, int nThread) {
        if (Objects.isNull(table) || nThread <= 0) {
            return;
        }
        Semaphore semaphore = new Semaphore(nThread);//定义几个许可
        ExecutorService executorService = Executors.newFixedThreadPool(nThread);//创建一个固定的线程池
        int tableRows = table.getNumRows();
        List<Map<String,Object>> list = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            try {
                semaphore.acquire();
                executorService.execute(() -> {
                    //此处可以放入待处理的业务
                    Map<String,Object> map = Maps.newHashMap();
                    for(JCoField field : table){
                                map.put(field.getName(), field.getValue());
                                list.add(map);
                            }
                    semaphore.release();
                });
            } catch (InterruptedException e) {

            }
        }
        System.out.println(list.size());
        executorService.shutdown();
    }

    //此处有加锁，不需要的同学可以自行改造
    public synchronized static <T> void Thread(List<T> list, int nThread) {
        if (CollectionUtils.isEmpty(list) || nThread <= 0 || CollectionUtils.isEmpty(list)) {
            return;
        }
        Semaphore semaphore = new Semaphore(nThread);//定义几个许可
        ExecutorService executorService = Executors.newFixedThreadPool(nThread);//创建一个固定的线程池
        for (T number : list) {
            try {
                semaphore.acquire();
                executorService.execute(() -> {
                    //此处可以放入待处理的业务
                    System.out.println("number:" + number);
                    semaphore.release();
                });
            } catch (InterruptedException e) {

            }
        }
        executorService.shutdown();
    }

}
