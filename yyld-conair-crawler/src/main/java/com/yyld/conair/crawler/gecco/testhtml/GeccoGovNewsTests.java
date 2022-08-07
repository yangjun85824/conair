package com.yyld.conair.crawler.gecco.testhtml;

import com.geccocrawler.gecco.annotation.*;
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
public class GeccoGovNewsTests implements HtmlBean {

    @HtmlField(cssPath = "li > h4 > a")
    private String title;

    @Href
    @HtmlField(cssPath = "li > h4 > a")
    private String link;

    @Text
    @HtmlField(cssPath = "li > h4 > span.date")
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
