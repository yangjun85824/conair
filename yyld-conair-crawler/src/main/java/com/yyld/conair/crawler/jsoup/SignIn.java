package com.yyld.conair.crawler.jsoup;

/**
 * @ClassName LoginIn
 * @Description //TODO
 * @Author yangj
 * @Date 2022/8/2 20:06
 * @Vresion 1.0
 **/

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

public class SignIn {

    private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.34 Safari/537.36 Edg/83.0.478.25";
    // 登录URL|退出RUL
    private static String URL_LOGIN = "http://172.19.3.62:31200/#/login";
    private static String URL_LOGOUT = "http://localhost:9590/thy/jsoup/logout";
    private static final String URL_Sign_in = "http://localhost:9590/thy/jsoup/pri/date";
    // 签到成功后的cookie
    private static Map<String, String> cookies;
    private static Connection connection;

    static {
        System.out.println("---------签到------------");
        connection = Jsoup.connect(URL_LOGIN).userAgent(userAgent)//
                .timeout(10 * 1000).method(Connection.Method.POST)  // // 字段参数| 文件名称| 文件流
                //.header("Connection", "keep-alive")
                .followRedirects(true);
        Connection.Response execute = null;
        try {
            execute = connection.ignoreContentType(true).execute();
            System.out.println(execute.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        cookies = execute.cookies();
        System.out.println("登录cookie如下");
        System.out.println(cookies);
    }

    /**
     * jsoup 模拟签到
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {



        signForScore(connection, cookies);
        System.out.println("--- 退出登录--------");
        Connection.Response response = connection.url(URL_LOGIN).method(Connection.Method.POST)//
                .ignoreContentType(true)//
                .followRedirects(true).execute();
        System.out.println(response.body());

    }

    /**
     * 带上cookie可以模拟登陆
     *
     * @param connection
     * @param cookies
     * @throws IOException
     */
    private static void signForScore(Connection connection, Map<String, String> cookies) throws IOException {
        Connection.Response response = connection.url(URL_Sign_in).method(Connection.Method.POST)//
                .cookies(cookies)//带上cookie可以模拟登陆
                .execute();
        System.out.println(response.body());
        Map<String, String> signCookie = response.cookies();
        System.out.println("模拟签到 得到的cookie");
        System.out.println(signCookie);
        System.out.println("------------");
    }
}

