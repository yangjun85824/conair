package com.yyld.conair.ds.sql;

import com.google.common.collect.Maps;
import me.saro.kit.Lists;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Tests
 * @Description 连接池测试类
 * @Author yangj
 * @Date 2022/6/23 15:44
 * @Vresion 1.0
 **/
public class TestMains {

    public static String readFileToString(String filePath) {
        String jsonString = null;
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(filePath);
            jsonString = IOUtils.toString(fileInputStream, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return jsonString;
    }

    public static void mysql(int no, String filename, String ch) throws Exception {

        String filePath = "C:\\Users\\杨建俊\\Desktop\\d3js-geojson-master/" + filename + ".json";
        String jsonString = readFileToString(filePath);

        System.out.println(jsonString);

        //final String url = "jdbc:mysql://10.58.240.46:3306/be_system?allowMultiQueries=true&amp;useUnicode=true&amp;characterEncoding=utf-8";
        final String url = "jdbc:mysql://10.125.238.203:3306/be_system?rewriteBatchedStatements=true";
        final String name = "com.mysql.cj.jdbc.Driver";
        final String user = "dbadmin";   // 数据库账户名
        final String password = "ServiceAdmin_1"; // 数据库密码
        Connection conn = null;

        Class.forName(name);//指定连接类型
        conn = DriverManager.getConnection(url, user, password);//获取连接
        if (conn != null) {
            System.out.println("获取连接成功");
        } else {
            System.out.println("获取连接失败");
        }
//id
//parent_id
//code
//parent_code
//ancestors
//name
//level
//data
//is_deleted
        // SQL语句模板
        String sql = "insert into blade_visual_map(id,parent_id,code,parent_code,ancestors,name,level,data,is_deleted) " +
                "values (?,3217,'11','00','00',?,1,?,0)";//"update blade_visual_map set data=? where id = '3217'";

        // 创建预处理语句
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, no);
        pstmt.setString(2, ch);
        // 设置参数
        pstmt.setString(3, jsonString);

        // 执行更新
        int affectedRows = pstmt.executeUpdate();
        System.out.println("Affected rows: " + affectedRows);

    }

    public static void main(String[] args) {

        List<String> files = Lists.asList("France", "German", "Iceland", "India", "Japan", "Netheland", "NewZealand", "Norway",
                "Portugal", "Russia", "Singapore", "SouthAfrica", "SouthKorea", "Spain", "Sweden", "Swiss", "USA");
        Map<String, String> chMap = Maps.newHashMap();
        //“法国”、“德国”、“冰岛”、“印度”、“日本”、“荷兰”、“新西兰”、“挪威”，
        //“葡萄牙”、“俄罗斯”、“新加坡”、“南非”、“韩国”、“西班牙”、“瑞典”、“瑞士”、“美国”
        chMap.put("France", "法国");
        chMap.put("German", "德国");
        chMap.put("Iceland", "冰岛");
        chMap.put("India", "印度");
        chMap.put("Japan", "日本");
        chMap.put("Netheland", "荷兰");
        chMap.put("NewZealand", "新西兰");
        chMap.put("Norway", "挪威");
        chMap.put("Portugal", "葡萄牙");
        chMap.put("Russia", "俄罗斯");
        chMap.put("Singapore", "新加坡");
        chMap.put("SouthAfrica", "南非");
        chMap.put("SouthKorea", "韩国");
        chMap.put("Spain", "西班牙");
        chMap.put("Sweden", "瑞典");
        chMap.put("Swiss", "瑞士");
        chMap.put("USA", "美国");
        int no = 3223;
        try {
            for (int i = 0; i < files.size(); i++) {
                String fileName = files.get(i);
                mysql(no + i, fileName, chMap.get(fileName));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
