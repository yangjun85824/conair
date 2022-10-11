package com.yyld.conair.eip.controller;

import com.alibaba.fastjson.JSONObject;
import com.yyld.conair.commons.data.result.APIResult;
import com.yyld.conair.eip.entity.RestEntity;
import com.yyld.conair.eip.entity.WSEntity;
import com.yyld.conair.eip.ws.abs.AbsWsdlPredic8Analysis;
import com.yyld.conair.eip.ws.abs.TYPE;
import com.yyld.conair.eip.ws.abs.ext.DefaultWsdlAnalysisUtil;
import com.yyld.conair.eip.wsconsume.TestWebservices;

import org.apache.camel.*;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.cxf.io.CachedOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.undertow.attribute.ExchangeAttributes.constant;

@RestController
@RequestMapping("/eip-rest")
public class IndexController {

    @Resource
    private SpringCamelContext camelContext;

    @Resource
    private TestWebservices testWebservices;

    @RequestMapping("")
    public APIResult<Void> index() {


        return APIResult.success();
    }

    @RequestMapping("/analysis")
    public APIResult<List<Map<String, String>>> analysis(String wsdl) throws Exception {

        AbsWsdlPredic8Analysis wsdlPredic8Analysis = new DefaultWsdlAnalysisUtil(wsdl, TYPE.HTTP);

        System.out.println(wsdlPredic8Analysis.getSchema(wsdlPredic8Analysis.getTargetNamespaceUri()));

        List result = wsdlPredic8Analysis.getAllParams();

        String json = JSONObject.toJSONString(result);

        System.out.println("json ======= " + json);
        return APIResult.success(result);
    }

    @RequestMapping("/tws")
    public APIResult<Void> tws(WSEntity entity) throws Exception {

        return APIResult.success();
    }


    @RequestMapping("/trest")
    public APIResult<Object> trest(RestEntity entity) throws Exception {

        ProducerTemplate p = camelContext.createProducerTemplate();

        Map headers = new HashMap();
        headers.put(Exchange.HTTP_QUERY, "dsType=mysql&dbName=a");
        p.sendBodyAndHeaders("direct:testrest",null,headers);
        //p.sendBody("direct:testrest",entity);

        Exchange exchange = p.send("http://localhost:4567/users/alist", new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setHeader(Exchange.HTTP_QUERY, constant("dsType=mysql&dbName=a"));
            }
        });
        Message out = exchange.getOut();

        InputStream bodyStream =  (InputStream)out.getBody();
        String inputContext = this.analysisMessage(bodyStream);
        bodyStream.close();

        // 存入到exchange的out区域
        if(exchange.getPattern() == ExchangePattern.InOut) {
            Message outMessage = exchange.getOut();
            outMessage.setBody(inputContext + " || out");
        }

        //ConsumerTemplate consumerTemplate = camelContext.createConsumerTemplate();
        //String message =  consumerTemplate.receiveBody("mock:results",String.class);


        return APIResult.success(inputContext);
    }

    @RequestMapping("/dynamictest")
    public APIResult<Object> dynamictest(@RequestBody RestEntity entity) throws Exception {

        ProducerTemplate p = camelContext.createProducerTemplate();

        Map headers = new HashMap();
        //headers.put(Exchange.HTTP_QUERY, "dsType=mysql&dbName=a");
        headers.put("mothed",entity.getMothed());

        p.sendBodyAndHeaders("direct:start",entity,headers);
        //p.sendBody("direct:start",entity);

        //ConsumerTemplate consumerTemplate = camelContext.createConsumerTemplate();
        //String message =  consumerTemplate.receiveBody("mock:results",String.class);


        return APIResult.success(null);
    }

    @RequestMapping("/dtrest")
    public APIResult<Object> dtrest(@RequestBody RestEntity entity) throws Exception {

        ProducerTemplate p = camelContext.createProducerTemplate();

        Map headers = new HashMap();
        headers.put(Exchange.HTTP_QUERY, "dsType=mysql&dbName=a");
        //headers.put(Exchange.CONTENT_TYPE,entity.getHeader().get(Exchange.CONTENT_TYPE));

        p.sendBodyAndHeaders("direct:testrest",null,headers);
        //p.sendBody("direct:testrest",entity);

        Exchange exchange = p.send("http://localhost:4567/users/alist", new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setHeader(Exchange.HTTP_QUERY, constant("dsType=mysql&dbName=a"));
            }
        });
        Message out = exchange.getOut();

        InputStream bodyStream =  (InputStream)out.getBody();
        String inputContext = this.analysisMessage(bodyStream);
        bodyStream.close();

        // 存入到exchange的out区域
        if(exchange.getPattern() == ExchangePattern.InOut) {
            Message outMessage = exchange.getOut();
            outMessage.setBody(inputContext + " || out");
        }

        //ConsumerTemplate consumerTemplate = camelContext.createConsumerTemplate();
        //String message =  consumerTemplate.receiveBody("mock:results",String.class);


        return APIResult.success(inputContext);
    }

    private String analysisMessage(InputStream bodyStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] contextBytes = new byte[4096];
        int realLen;
        while((realLen = bodyStream.read(contextBytes , 0 ,4096)) != -1) {
            outStream.write(contextBytes, 0, realLen);
        }

        // 返回从Stream中读取的字串
        try {
            return new String(outStream.toByteArray() , "UTF-8");
        } finally {
            outStream.close();
        }
    }

}
