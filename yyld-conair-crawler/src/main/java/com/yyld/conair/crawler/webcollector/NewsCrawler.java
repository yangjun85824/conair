package com.yyld.conair.crawler.webcollector;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import com.alibaba.fastjson.JSON;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @ClassName NewsCrawler
 * @Description //TODO
 * @Author yangj
 * @Date 2022/8/4 14:08
 * @Vresion 1.0
 **/
public class NewsCrawler extends BreadthCrawler {

    /** 从这个页面用正则表达式去匹配链接
     * 这是一个构造函数
 */
    public NewsCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        /*页面开始*/
        this.addSeed("http://sousuo.gov.cn/column/30469/0.htm");

        /*获取像这样的http://news.hfut.edu.cn/show-xxxxxxhtmlurl*/
        this.addRegex("http://sousuo.gov.cn/column/30469/[0-9]*.htm");
        this.addRegex("http://www.gov.cn/zhengce/[0-9]*-[0-9]*/[0-9]*/content_[0-9]*.htm");
        this.addRegex("http://www.gov.cn/zhengce/content/[0-9]*-[0-9]*/[0-9]*/content_[0-9]*.htm");
        /*不获取这样的格式 jpg|png|gif*/
        this.addRegex("-.*\\.(jpg|png|gif).*");
        /*也不要这样的 #*/
        this.addRegex("-.*#.*");
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        String url = page.url();


        /*如果是一个新的页面*/
        if (page.matchUrl("http://sousuo.gov.cn/column/30469/[0-9]*.htm")) {
            //用JSOUP去解析这个页面
//            Document doc = page.doc();
            System.out.println("url================"+url);
            //的提取新闻和标题的css选择器

            Elements elements = page.select("div.news_box > div.list > ul.listTxt > li > h4 > a ");
            for (Element el : elements) {


                String title = el.text();
                String content = el.attr("href");

                System.out.println("URL:\n" + url);
                System.out.println("title:\n" + title);
                System.out.println("content:\n" + content);
            }
        }
        /*详情页*/
        if (page.matchUrl("http://www.gov.cn/zhengce/[0-9]*-[0-9]*/[0-9]*/content_[0-9]*.htm")
                || page.matchUrl("http://www.gov.cn/zhengce/content/[0-9]*-[0-9]*/[0-9]*/content_[0-9]*.htm")) {
            /*用JSOUP去解析这个页面*/
//            Document doc = page.doc();

            /*提取新闻和标题的css选择器*/

            Elements elements = page.select("div.w1100 > div.wrap");
            elements = elements == null ? page.select("div.content > div.padd > div.article") : elements;
            for (Element el : elements) {

                String title = el.html();

                System.out.println("测试:\n" + url);
                System.out.println("测试详情页:\n" + JSON.toJSONString(title));
            }

            /*如果你想要抓取新的url,添加他们到 nextLink*/
            /*WebCollector 可以自动去重*/
            /*如果autoparse 为真则添加链接去nextlinks 如果与正则不匹配则不会添加*/
            //next.add("http://xxxxxx.com");
        }
    }

    public static void main(String[] args) throws Exception {
        NewsCrawler crawler = new NewsCrawler("crawl", true);
        crawler.setThreads(50);
//        crawler.setTopN(100);
        //crawler.setResumable(true);
        /*网页爬取深度为4*/
        crawler.start(80);
    }


}
