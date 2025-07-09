package com.yyld.conair.ds;

import java.sql.*;

/**
 * 数据库连接类
 */
public final class DATAConnection {

    public static void main(String[] args) {
        getConnection("com.microsoft.sqlserver.jdbc.SQLServerDriver",
                "jdbc:sqlserver://10.125.1.46;DatabaseName=lc0019999",
                "IEI_GS","2023@IEIsystem!");
        System.out.println("11111");
    }
    public static Connection getConnection(String driverclass,String url,String user,String password){

        try {
            // 1.注册驱动
            Class.forName(driverclass);
            // 2.获取数据库连接
            Connection coon = DriverManager.getConnection(url, user, password);
            return coon;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(ResultSet rs, Statement st, Connection conn) {
        //释放结果集资源
        //非空判断，防止空指针异常
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                rs = null;//手动置空
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                st = null;
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conn = null;
            }
        }
    }

}
