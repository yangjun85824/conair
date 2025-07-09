package com.yyld.conair.ds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
/**
 * @ClassName Tests
 * @Description 连接池测试类
 * @Author yangj
 * @Date 2022/6/23 15:44
 * @Vresion 1.0
 **/
public class Tests {

    public static void main1(String[] args) {


        Date date= new Date();

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        int month = cal.get(Calendar.MONTH);

        System.out.println(month);

        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        month = localDate.getMonthValue();
        System.out.println(month);
        //获取当前时间年后两位    月   日    其他方法见下面格式
        System.err.println(String .format("%ty", new Date()));  //获取年的后两位
        System.err.println(String .format("%tm", new Date()));	//获取月份单个就自动0补充
        System.err.println(String .format("%td", new Date()));  //获取日单个就自动0补充

        String str = "SUM(464646MAX(FKFKFK)46464)ks".replaceAll("(SUM)\\((.*?)\\)", "$1[$2]")
                .replaceAll("(MAX)\\((.*?)\\)", "$1[$2]")
                .replaceAll("(MIN)\\((.*?)\\)", "$1[$2]")
                .replaceAll("\\[", "\\$")
                .replaceAll("\\]", "_");

        System.out.println(str);
        int i = 0 ;
        ii: if (i == 0){
            int f = 0;
            if (f == 0){
                System.out.println("1111111");
                break ii;
            }
            System.out.println("1111111122222222222");
        }
        System.out.println("2222222222222222");

        

        /* 自定义 数据连接池
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

        //开启一个线程查看连接池的状态
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleWithFixedDelay(connectionPool::status, 0, 5, TimeUnit.SECONDS);

        //开启20个线程，不断获取连接，比较哈希值看同一个线程取出的连接是不是同一个
        for(int i = 0; i < 20; i++) {
            Random random = new Random();
            int count = random.nextInt(30) + 3;
            Thread t = new Thread(() ->{
                try {
                    for (int j = 0; j < count; j++) {
                        Connection connection = connectionPool.getConnection();

                        System.out.println(Thread.currentThread().getName() + "共" + count + "次循环， 目前第" + (j + 1) + "次" + " hashcode :" + connection.hashCode());
                        TimeUnit.SECONDS.sleep(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                connectionPool.freeLocalConnection();
            });
            t.setName("test" + i);
            t.start();
        }*/

        /*AbsDruidDataSources druidDataSources = new DefaultDruidDataSources();

        Map<String, DruidDataSource> dsMap = druidDataSources.dataSourceMap();

        for (String key : dsMap.keySet()){

            System.out.println(key);
            DruidDataSource ds = dsMap.get(key);

            try {
                for (int i = 0 ; i<8;i++) {
                    System.out.println(i+"    i================="+ds.getConnection().hashCode());
                    System.out.println(ds.getValidationQuery());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }*/

    }

    public static void main(String[] args) {
        // 开始时间
        long stime = System.currentTimeMillis();

        //List<String> list = Lists.newArrayList();
        //for (int i = 1 ; i <= 10000004 ; i++){
        //
        //    Map<String,Object> objectMap = Maps.newHashMap();
        //    objectMap.put("x",i);
        //    objectMap.put("test","x_"+i);
        //    list.add(JSON.toJSONString(objectMap));
        //}
        //System.out.println("============================="+JSON.toJSONString(list.get(list.size()-1)));
        // 结束时间
        long etime = System.currentTimeMillis();
        // 计算执行时间
        System.out.printf("执行时长：%d 纳秒.", (etime - stime));

        //test(list);

        etime = System.currentTimeMillis();
        // 计算执行时间
        System.out.printf("总时长=======执行时长：%d 纳秒.", (etime - stime));

        stime = System.currentTimeMillis();

        try {
            mysql();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        etime = System.currentTimeMillis();
        // 计算执行时间
        System.out.printf("总时长=======执行时长：%d 纳秒.", (etime - stime));
    }

    public static void mysql() throws Exception {

        //final String url = "jdbc:mysql://10.58.240.46:3306/be_system?allowMultiQueries=true&amp;useUnicode=true&amp;characterEncoding=utf-8";
        final String url = "jdbc:mysql://localhost:3306/test?rewriteBatchedStatements=true";
        final String name = "com.mysql.cj.jdbc.Driver";
        final String user = "root";   // 数据库账户名
        final String password = "root"; // 数据库密码
        Connection conn = null;

        Class.forName(name);//指定连接类型
        conn = DriverManager.getConnection(url, user, password);//获取连接
        if (conn!=null) {
            System.out.println("获取连接成功");
            insert(conn);
        }else {
            System.out.println("获取连接失败");
        }
    }

    public static void insert_2(Connection conn) throws SQLException {

        long start = System.currentTimeMillis();
        String sql = "insert delayed into y_int_agg_import_data_temp_copy(ID_, BATCH_NO_, CREATE_TIME_,CONTENT_) VALUES (?,'1',null,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);//取消自动提交
            for (int i = 1; i <= 1000000; i++) {
                ps.setObject(1, i);
                ps.setObject(2, i);
                ps.addBatch();
            }
            System.out.println("生成数据 : " + (System.currentTimeMillis() - start) / 1000 + "seconds");
            ps.executeBatch();
            ps.clearBatch();
            conn.commit();//所有语句都执行完毕后才手动提交sql语句
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        System.out.println("百万条数据插入用时：" + (System.currentTimeMillis() - start)+"【单位：毫秒】");
    }
    public static void insert(Connection conn) throws SQLException {

        Long start = System.currentTimeMillis();
        Long begin = System.currentTimeMillis();
        System.out.println("Start Inserting Data...");

        String prefix = "INSERT INTO y_int_agg_import_data_temp_copy (ID_, BATCH_NO_, CREATE_TIME_,CONTENT_) VALUES ";
        int insertNum = 1000000;

        try {
            StringBuffer suffix = new StringBuffer();
            conn.setAutoCommit(false);

            for (int i = 1; i <= insertNum; i++) {
                //suffix.append("(" + i +",'1', SYSDATE(), '{\"BUKRS\":\"7000\",\"BELNR\":\"0700000478\",\"GJAHR\":\"2021\",\"BLART\":\"ZG\",\"BLDAT\":1630339200000,\"BUDAT\":1630339200000,\"MONAT\":\"08\",\"CPUDT\":1658937600000,\"USNAM\":\"FANYUFENG\",\"TCODE\":\"FBVB\",\"BVORG\":\"\",\"XBLNR\":\"\",\"STBLG\":\"\",\"STJAH\":\"0000\",\"BKTXT\":\"QT21082300145\",\"WAERS\":\"CNY\",\"KURSF\":0.00000,\"AWKEY\":\"070000047870002021\",\"HWAER\":\"CNY\",\"XBLNR_ALT\":\"\",\"CASH_ALLOC\":\"\",\"BUZEI\":\"001\",\"BSCHL\":\"01\",\"KOART\":\"D\",\"SHKZG\":\"H\",\"MWSKZ\":\"\",\"DMBTR\":-21540.00,\"WRBTR\":-21540.00,\"PSWBT\":-21540.00,\"PSWSL\":\"CNY\",\"ZUONR\":\"Y00046860\",\"SGTXT\":\"QT21082300145\",\"VBUND\":\"\",\"VORGN\":\"RFBU\",\"KOSTL\":\"\",\"SAKNR\":\"1221010000\",\"HKONT\":\"1221010000\",\"KUNNR\":\"Y00046860\",\"LIFNR\":\"\",\"XBILK\":\"X\",\"HZUON\":\"20210831\",\"ZFBDT\":1630339200000,\"SKFBT\":21540.00,\"REBZG\":\"\",\"EBELN\":\"\",\"EBELP\":\"00000\",\"RSTGR\":\"\",\"ANLN1\":\"\",\"XREF1\":\"\",\"XREF2\":\"\",\"XREF3\":\"\",\"XNEGP\":\"X\",\"FKBER\":\"\",\"PRCTR\":\"\",\"ZYWFL\":\"\",\"BUTXT\":\"浪潮云信息技术股份公司\",\"NAME1\":\"闫飞\",\"NAME2\":\"\",\"NAME3\":\"\",\"ZZFIELD1\":\"\",\"ZZFIELD2\":\"\",\"ZZFIELD3\":\"\",\"ZZFIELD4\":\"\",\"ZZFIELD5\":\"\",\"VBEL2\":\"\",\"TXT20\":\"其他应收款-个人往来-备用金\"}'),");
                suffix.append("(" + i +",'1', SYSDATE(), '"+i+"'),");
            }
            System.out.println("生成数据 : " + (System.currentTimeMillis() - start) + "毫秒");
            PreparedStatement pst = conn.prepareStatement(prefix + suffix.substring(0, suffix.length() - 1));
            pst.addBatch();
            pst.executeBatch();
            conn.commit();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        System.out.println("百万条数据插入用时：" + (System.currentTimeMillis() - start)+"【单位：毫秒】");
    }
    public static void insert_1(Connection conn) throws SQLException {

        long start = System.currentTimeMillis();
        String sql = "insert into y_int_agg_import_data_temp_copy(ID_, BATCH_NO_, CREATE_TIME_,CONTENT_) VALUES (?,null,null,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);//取消自动提交
            for (int i = 1; i <= 1000000; i++) {
                ps.setObject(1, i+"");
                ps.setObject(2, i);
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
        System.out.println("百万条数据插入用时：" + (System.currentTimeMillis() - start)+"【单位：毫秒】");
    }
}
