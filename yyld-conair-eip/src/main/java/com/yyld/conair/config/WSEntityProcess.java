package com.yyld.conair.config;

import com.alibaba.fastjson.JSON;
import org.apache.camel.*;

public class WSEntityProcess implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {

        String routeId = exchange.getFromRouteId();
        Message message = exchange.getIn();
        CamelContext camelContext = exchange.getContext();
        Route route = camelContext.getRoute(routeId);

        Endpoint endpoint = route.getEndpoint();
        //endpoint.inTy
        System.out.println(JSON.toJSONString(exchange.getIn()));

    }
}
