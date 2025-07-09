package com.yyld.conair.druid;

import org.ofdrw.converter.ConvertHelper;
import org.ofdrw.converter.FontLoader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName Tests
 * @Description 连接池测试类
 * @Author yangj
 * @Date 2022/6/23 15:44
 * @Vresion 1.0
 **/
public class Tests {

    public static void main1(String[] args) {


        Date date = new Date();

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        int month = cal.get(Calendar.MONTH);

        System.out.println(month);

        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        month = localDate.getMonthValue();
        System.out.println(month);
        //获取当前时间年后两位    月   日    其他方法见下面格式
        System.err.println(String.format("%ty", new Date()));  //获取年的后两位
        System.err.println(String.format("%tm", new Date()));    //获取月份单个就自动0补充
        System.err.println(String.format("%td", new Date()));  //获取日单个就自动0补充

        String str = "SUM(464646MAX(FKFKFK)46464)ks".replaceAll("(SUM)\\((.*?)\\)", "$1[$2]")
                .replaceAll("(MAX)\\((.*?)\\)", "$1[$2]")
                .replaceAll("(MIN)\\((.*?)\\)", "$1[$2]")
                .replaceAll("\\[", "\\$")
                .replaceAll("\\]", "_");

        System.out.println(str);
        int i = 0;
        ii:
        if (i == 0) {
            int f = 0;
            if (f == 0) {
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

    public static void main2(String[] args) {
        // 开始时间
        long stime = System.currentTimeMillis();

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


    public static void main3(String[] args) {

//        String str = "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd";
        String str = "###@@@中文123-中文中文中文中文";

//        Pattern pattern = Pattern.compile("([\\u4e00-\\u9fa5]+).*(?=-|/|_|一.*)");
        Pattern pattern = Pattern.compile("([\\u4e00-\\u9fa5]+)|(([\\u4e00-\\u9fa5]+).*(?=-|/|_|一.*))");

        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {

            System.out.println(matcher.group());

        }

    }

    public static void main(String[] args) {
        // 1. 文件输入路径
        Path src = Paths.get("C:\\Users\\杨建俊\\Desktop\\新建文件夹\\s05负一2885.99浪潮信息.ofd");
        // 2. 转换后文件输出位置
        Path dst = Paths.get("C:\\Users\\杨建俊\\Desktop\\新建文件夹\\s05负一2885.99浪潮信息.pdf");
        // 3. OFD转换PDF
        System.out.println();
        try {
//            URL url = new URL("http://10.125.238.205:8300/photo/group1/M00/00/65/Cn3ux2bqPtmAdvs_AACP_aOPCfs472.ofd");
//            FontLoader preload = FontLoader.Preload();
//            preload.addSimilarFontReplaceRegexMapping(".*FZXBSK.*","方正小标宋_GBK");
//            preload.addSimilarFontReplaceRegexMapping(".*FZHTK.*","方正黑体_GBK");
//            preload.addSimilarFontReplaceRegexMapping(".*FZFSK.*","方正仿宋_GBK");
//            preload.addSimilarFontReplaceRegexMapping(".*FZKTK.*","方正楷体_GBK");
//            preload.addSimilarFontReplaceRegexMapping(".*FZXiaoBiaoSong.*","方正小标宋_GBK");
//            Path ofdPath = Paths.get("src/test/resources/999.ofd");
//            Path pdfPath = Paths.get("target/999.pdf");
//            try (OFDExporter exporter = new PDFExporterPDFBox(src, dst)) {
//                exporter.export();
//            }

//            public void testOfd2Pdf() {
//                Path ofdPath = Paths.get("/home/qiyulai/temp/1.ofd");
//                Path pdfPath = Paths.get("/home/qiyulai/temp/999pdf.pdf");
//
//                // 为不规范的字体名创建映射
//                FontLoader.getInstance()
//                        .addAliasMapping("新宋体", "新宋体")
//                        .addSimilarFontReplaceRegexMapping(".*SimSun.*", "SimSun");
//                ConvertHelper.toPdf(ofdPath, pdfPath);
//                System.out.println("success...");
//            }
            com.itextpdf.io.font.FontProgram fontProgram;
            org.apache.commons.io.IOUtils utils;
            org.apache.fontbox.ttf.CmapLookup cmapLookup;
            org.ofdrw.reader.OFDReader reader;
            org.dom4j.DocumentException exception;
            org.ofdrw.pkg.container.VirtualContainer virtualContainer;
            org.apache.commons.compress.utils.IOUtils utils1;
            com.itextpdf.kernel.pdf.PdfWriter pdfWriter;
            org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace pdColorSpace;
            org.apache.pdfbox.jbig2.JBIG2ImageReaderSpi jbig2ImageReaderSpi;
            org.ofdrw.gm.ses.parse.VersionParser versionParser;
            org.ujmp.core.Matrix matrix;

            org.apache.fontbox.cff.CFFParser cffParser;
            org.apache.commons.compress.archivers.zip.ZipArchiveInputStream zipArchiveInputStream;
            org.apache.commons.compress.archivers.zip.ZipArchiveEntry zipArchiveEntry;
//            FontLoader.getInstance()
//                    .addAliasMapping("新宋体", "新宋体")
//                    .addSimilarFontReplaceRegexMapping(".*SimSun.*", "SimSun");
//            FontLoader preload = FontLoader.Preload();
//            preload.addSimilarFontReplaceRegexMapping(".*FZXBSK.*","方正小标宋_GBK");
//            preload.addSimilarFontReplaceRegexMapping(".*FZHTK.*","方正黑体_GBK");
//            preload.addSimilarFontReplaceRegexMapping(".*FZFSK.*","方正仿宋_GBK");
//            preload.addSimilarFontReplaceRegexMapping(".*FZKTK.*","方正楷体_GBK");
//            preload.addSimilarFontReplaceRegexMapping(".*FZXiaoBiaoSong.*","方正小标宋_GBK");
//            ConvertHelper.toPdf(src, dst);
            org.dom4j.DocumentException documentException;
            org.bouncycastle.jcajce.provider.digest.SM3 sm3;
            org.ofdrw.core.pageDescription.color.color.CT_Color color;
            org.dom4j.io.SAXReader saxReader;

            // 为不规范的字体名创建映射
            FontLoader.getInstance()
                    .addAliasMapping("新宋体", "新宋体")
                    .addSimilarFontReplaceRegexMapping(".*SimSun.*", "SimSun");
            FontLoader.loadAsDefaultFont("/usr/share/fonts/chainese");
            ConvertHelper.toPdf(src, dst);
            System.out.println("success...");
//            testContentExtractor();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void mysql() throws Exception {

        //final String url = "jdbc:mysql://10.58.240.46:3306/be_system?allowMultiQueries=true&amp;useUnicode=true&amp;characterEncoding=utf-8";
        final String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
        final String name = "com.mysql.cj.jdbc.Driver";
        final String user = "root";   // 数据库账户名
        final String password = "root"; // 数据库密码
        Connection conn = null;

        Class.forName(name);//指定连接类型
        conn = DriverManager.getConnection(url, user, password);//获取连接
        if (conn != null) {
            System.out.println("获取连接成功");
            insert(conn);
        } else {
            System.out.println("获取连接失败");
        }
    }

    public static void insert(Connection conn) throws SQLException {

        long start = System.currentTimeMillis();
        String sql = "insert into y_int_agg_import_data_temp_copy(ID_, BATCH_NO_, CREATE_TIME_,CONTENT_) VALUES (?,null,null,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);//取消自动提交
            for (int i = 1; i <= 1000000; i++) {
                ps.setObject(1, i + 100);
                ps.setObject(2, i);
                ps.addBatch();
                if (i % 1000 == 0) {
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }
            ps.executeBatch();
            ps.clearBatch();
            conn.commit();//所有语句都执行完毕后才手动提交sql语句
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        System.out.println("百万条数据插入用时：" + (System.currentTimeMillis() - start) + "【单位：毫秒】");
    }
}
