package com.yyld.conair.config;

import com.yyld.conair.commons.data.result.APIResult;
import com.yyld.conair.eip.camel.HttpDynamicRouter;
import com.yyld.conair.eip.entity.WSEntity;
import com.yyld.conair.eip.wsconsume.TestWebservices;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.apache.camel.model.rest.RestParamType.*;

/**
 * @ClassName CamelRestApi
 * @Description //TODO
 * @Author yangj
 * @Date 2022/9/16 11:32
 * @Vresion 1.0
 **/
@Component
public class CamelRestApi extends RouteBuilder {

    @Autowired
    private Environment env;

    @Value("${camel.servlet.mapping.context-path}")
    private String contextPath;


    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json) //json格式
                //.bindingMode(RestBindingMode.auto) //自动
                .dataFormatProperty("prettyPrint", "true")
                .enableCORS(true)
                .port(env.getProperty("server.port", "8003"))
                .contextPath(contextPath.substring(0, contextPath.length() - 2))
                // turn on openapi api-doc
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "企业集成API")
                .apiProperty("api.version", "1.0.0");

        rest("/ws-analysis").description("企业集成API Rest Services")
                .consumes("application/json")
                .produces("application/json")

                .get().description("查询所有方法")
                .outType(Map.class)
                .responseMessage().code(200).message("查询成功").endResponseMessage()
                .to("bean:testws?method=analysis(${header.wsdl})")

                .post().description("请求ws方法")
                .type(WSEntity.class)
                .outType(String.class)
                //.param().name("body").type(body).description("body参数").endParam()
                .responseMessage().code(200).message("调用成功").endResponseMessage()
                .to("bean:testws?method=requestWsServicesParams")

                ;

        from("direct:testrest")
                .setHeader(Exchange.HTTP_METHOD, constant(org.apache.camel.component.http.HttpMethods.GET))
                .to("http://localhost:4567/users/alist?dsType=${header.dsType}&dbName=${header.dbName}")
                //.to("sade:end");
                //.to("mock:results");
                .to("log:DEBUG?showBody=true&showHeaders=true");
        //from("sade:testrest").to("rest-openapi:http://localhost:4567/users/post");

        from("direct:start")
                // use a bean as the dynamic router
                .choice()
                .when(simple("${header.mothed} == 'GET'"))
                   .to("bean:getHeader?method=paramHandle")
                .when(simple("${header.mothed} == 'POST'"))
                    .to("bean:postHeader?method=paramHandle")
                .otherwise()
                .dynamicRouter(method(HttpDynamicRouter.class, "http"));

    }

}
