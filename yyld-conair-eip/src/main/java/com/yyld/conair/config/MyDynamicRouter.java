package com.yyld.conair.config;

import org.apache.camel.Consume;
import org.apache.camel.Header;
import org.apache.camel.RecipientList;
import org.apache.camel.language.xpath.XPath;
import org.w3c.dom.Document;

import java.util.List;

public class MyDynamicRouter {

    @Consume(uri = "direct:start")
    @RecipientList
    public List<String> route(@XPath("/customer/id") String customerId, @Header("Location") String location, Document body) {
        //可以返回多个端点，用 逗号 隔开即可。最终必须返回null值结束
        //TODO

        return null;
    }

}
