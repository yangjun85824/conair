package com.yyld.conair.crawler.gecco.testhtml;

import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

/**
 * @ClassName Gecco
 * @Description //TODO
 * @Author yangj
 * @Date 2022/8/2 18:32
 * @Vresion 1.0
 **/
@Gecco(matchUrl = "http://sousuo.gov.cn/column/30469/{page}.htm",pipelines = "testGovPipelines")
public class GeccoGovTests implements HtmlBean {

    @Request
    private HttpRequest request;

    @HtmlField(cssPath = "div.news_box > div.list > ul.listTxt > li")
    private List<GeccoGovNewsTests> newsTestLIST;

    //这个取值还有问题
    @Attr(value = "value")
    @HtmlField(cssPath = "#pnum")
    private String page;

    @Text
    @HtmlField(cssPath = "#toPage > li")
    private String allPage;

    @HtmlField(cssPath = "div.content > div.padd > div.news_box > div.newspage > ul > li.on > a")
    private String cPage;

    @Href
    @HtmlField(cssPath = "div.content > div.padd > div.news_box > div.newspage > ul > li > a.next")
    private String nextPageUrl;

    public String getAllPage() {
        return allPage;
    }

    public void setAllPage(String allPage) {
        this.allPage = allPage;
    }

    public String getcPage() {
        return cPage;
    }

    public void setcPage(String cPage) {
        this.cPage = cPage;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {

        this.request = request;
    }

    public List<GeccoGovNewsTests> getNewsTestLIST() {
        return newsTestLIST;
    }

    public void setNewsTestLIST(List<GeccoGovNewsTests> newsTestLIST) {
        this.newsTestLIST = newsTestLIST;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
