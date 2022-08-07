package com.yyld.conair.crawler.gecco.pipelines;

/**
 * @ClassName TestPipelines
 * @Description //TODO
 * @Author yangj
 * @Date 2022/8/2 18:35
 * @Vresion 1.0
 **/
import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.DeriveSchedulerContext;
import com.yyld.conair.crawler.gecco.testhtml.GeccoGovDetailTests;
import com.yyld.conair.crawler.gecco.testhtml.GeccoGovNewsTests;
import com.yyld.conair.crawler.gecco.testhtml.GeccoGovTests;

import java.util.List;

/**
 * @Auther: lianjc
 * @Date: 2018/11/22 0022 11:29
 * @Description:
 */
@PipelineName(value="testGovDetailPipelines")
public class TestGovDetailPipelines implements Pipeline<GeccoGovDetailTests> {
    @Override
    public void process(GeccoGovDetailTests test) {

        HttpRequest req = test.getRequest();
        System.out.println("请求参数："+JSON.toJSONString(req.getParameters()));

        System.out.println("获取到内容表getDivContentWrap:"+ JSON.toJSONString(test.getDivContentWrap()));
        System.out.println("获取到内容表getDivContentArticle:"+JSON.toJSONString(test.getDivContentArticle()));
    }
}


