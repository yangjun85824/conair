package com.yyld.conair.ds;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * @ClassName Tests
 * @Description 连接池测试类
 * @Author yangj
 * @Date 2022/6/23 15:44
 * @Vresion 1.0
 **/
public class Tests {

    public static void main(String[] args) {


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
}
