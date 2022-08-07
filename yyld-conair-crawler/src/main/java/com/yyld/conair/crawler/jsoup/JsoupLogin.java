package com.yyld.conair.crawler.jsoup;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @ClassName JsoupLogin
 * @Description //TODO
 * @Author yangj
 * @Date 2022/8/2 20:13
 * @Vresion 1.0
 **/
public class JsoupLogin {

    public static String LOGIN_URL = "http://172.19.3.62:31200/#/login";
    public static String USER_AGENT = "User-Agent";
    public static String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";

    public static void main(String[] args) throws Exception {

        simulateLogin("P0005998", "01234"); // 模拟登陆github的用户名和密码

    }

    /**
     * @param userName 用户名
     * @param pwd 密码
     * @throws Exception
     */
    public static void simulateLogin(String userName, String pwd) throws Exception {

        //构造一个webClient 模拟Chrome 浏览器
       /* WebClient webClient = new WebClient(BrowserVersion.CHROME);
//屏蔽日志信息
//        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
//                "org.apache.commons.logging.impl.NoOpLog");
        Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
//支持JavaScript
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(5000);
        webClient.
        HtmlPage rootPage = webClient.getPage(LOGIN_URL);
//设置一个运行JavaScript的时间
        webClient.waitForBackgroundJavaScript(5000);
        String html = rootPage.asXml();
        Document document = Jsoup.parse(html);

        Element app = document.getElementById("app");

        Element form = app.tagName("form");

        Elements es = form.getElementsByTag("input");*/

//        Elements text = app.tagName("input");

        // 获取cooking和表单属性
        // lets make data map containing all the parameters and its values found in the form
        Map<String, String> datas = new HashMap<>();
        /*for (Element e : eleList.get(1).getAllElements()) {

            System.out.println(e.html());
            // 设置用户名
            if (e.attr("name").equals("login")) {
                e.attr("value", userName);
            }
            // 设置用户密码
            if (e.attr("name").equals("password")) {
                e.attr("value", pwd);
            }
            // 排除空值表单属性
            if (e.attr("name").length() > 0) {
                datas.put(e.attr("name"), e.attr("value"));
            }
        }*/

        /*
         * 第二次请求，以post方式提交表单数据以及cookie信息
         */
        /*Connection con2 = Jsoup.connect("https://github.com/session");
        con2.header(USER_AGENT, USER_AGENT_VALUE);
        // 设置cookie和post上面的map数据
        Connection.Response login = con2.ignoreContentType(true).followRedirects(true).method(Connection.Method.POST)
                .data(datas).cookies(rs.cookies()).execute();
        // 打印，登陆成功后的信息
        System.out.println(login.body());

        // 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
        Map<String, String> map = login.cookies();
        for (String s : map.keySet()) {
            System.out.println(s + " : " + map.get(s));
        }*/
    }
}
