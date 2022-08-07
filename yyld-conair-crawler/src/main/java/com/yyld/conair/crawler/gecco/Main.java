package com.yyld.conair.crawler.gecco;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;

/**
 * @ClassName Main
 * @Description 测试运行
 * @Author yangj
 * @Date 2022/8/2 18:31
 * @Vresion 1.0
 **/
public class Main {

    public static void main(String[] args) {

        String [] str = {"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/537.75.14",
                "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Win64; x64; Trident/6.0)"
        };

        HttpRequest request = new HttpGetRequest("http://sousuo.gov.cn/column/30469/0.htm");

        request.addHeader("User-Agent",String.join(",",str));
        request.addHeader("Host","sousuo.gov.cn");
        request.addHeader("Referer","http://sousuo.gov.cn");
        request.setCharset("utf-8");
        GeccoEngine.create()
                .classpath("com.yyld.conair.crawler.gecco")
//                .start("https://blog.csdn.net/u013396133/article/details/84339780")
                .seed(request)
                .thread(4)
                .interval(5000)
                .loop(false)
                .debug(false)
                .mobile(false)
                .run();
//                .start();

    }

}
