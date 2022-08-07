package com.yyld.conair.crawler.gecco.testhtml;

import com.baomidou.mybatisplus.annotation.TableField;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Html;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
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
@Gecco(pipelines = "testGovDetailPipelines")
public class GeccoGovDetailTests implements HtmlBean {

    @Html
    @HtmlField(cssPath = "div.w1100 > div.wrap")
    private String divContentWrap;

    @Html
    @HtmlField(cssPath = "div.content > div.padd > div.article")
    private String divContentArticle;

    @Request
    private HttpRequest request;

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public String getDivContentWrap() {
        return divContentWrap;
    }

    public void setDivContentWrap(String divContentWrap) {
        this.divContentWrap = divContentWrap;
    }

    public String getDivContentArticle() {
        return divContentArticle;
    }

    public void setDivContentArticle(String divContentArticle) {
        this.divContentArticle = divContentArticle;
    }

}
