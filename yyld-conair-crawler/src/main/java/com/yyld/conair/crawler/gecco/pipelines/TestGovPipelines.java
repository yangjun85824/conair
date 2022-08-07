package com.yyld.conair.crawler.gecco.pipelines;

/**
 * @ClassName TestPipelines
 * @Description //TODO
 * @Author yangj
 * @Date 2022/8/2 18:35
 * @Vresion 1.0
 **/
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.DeriveSchedulerContext;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import com.yyld.conair.crawler.gecco.testhtml.GeccoGovNewsTests;
import com.yyld.conair.crawler.gecco.testhtml.GeccoGovTests;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: lianjc
 * @Date: 2018/11/22 0022 11:29
 * @Description:
 */
@PipelineName(value="testGovPipelines")
public class TestGovPipelines implements Pipeline<GeccoGovTests> {

    @Override
    public void process(GeccoGovTests test) {
        String [] str = {"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/537.75.14",
                "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Win64; x64; Trident/6.0)"
        };

        List<GeccoGovNewsTests> tests = test.getNewsTestLIST();

        System.out.println("test.getPage()========="+test.getPage());
        System.out.println("test.getNextPageUrl()========="+test.getNextPageUrl());

        //向下抓取详情页信息
        for (GeccoGovNewsTests t : tests) {

            System.out.println(t.getTitle());
            HttpRequest request = test.getRequest();
            request.setUrl(t.getLink());
            request.addHeader("User-Agent",String.join(",",str));
            request.addHeader("Host","www.gov.cn");
            request.addHeader("Referer","http://www.gov.cn/");
            request.setCharset("utf-8");

            Map<String,String> parammap = JSONObject.parseObject(JSON.toJSONString(t),Map.class);
            request.setParameters(parammap);

            DeriveSchedulerContext.into(request.subRequest(t.getLink()));

        }

        //翻页爬取
        String regEx="[^0-9]";

        Pattern p = Pattern.compile(regEx);

        Matcher m = p.matcher(test.getAllPage());

        String cPage = m.replaceAll("").trim();

        System.out.println( "m.replaceAll().trim()"+cPage);

        String pageUrl = test.getNextPageUrl();
        System.out.println("pageUrl========"+pageUrl);
        if (!cPage.equals(test.getcPage())) {
            System.out.println(pageUrl);
            HttpRequest request = test.getRequest();
            request.setUrl(pageUrl);
            request.addHeader("User-Agent", String.join(",", str));
            request.addHeader("Host", "sousuo.gov.cn");
            request.addHeader("Referer", "http://sousuo.gov.cn");
            request.setCharset("utf-8");

            DeriveSchedulerContext.into(request.subRequest(pageUrl));
        }

    }

    public static void main(String[] args) {

        String regEx="[^0-9]";

        Pattern p = Pattern.compile(regEx);

        Matcher m = p.matcher("共79页");

        String cPage = m.replaceAll("").trim();

        System.out.println( "m.replaceAll().trim()"+cPage);

    }
}


