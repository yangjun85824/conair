package com.yyld.conair.eip.wsconsume;

import com.yyld.conair.commons.data.result.APIResult;
import com.yyld.conair.eip.entity.RestEntity;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("testrest")
public class RestServices {

    public APIResult requestHttp(RestEntity entity){

        return APIResult.success();
    }

    public static void main(String[] args) {

        RestEntity entity = new RestEntity();

        entity.setHttpurl("http://localhost:4567/users/alist");
        entity.setMothed("post");

        Map<String,Object> map = new HashMap<>();
        map.put("dsType","mysql");
        map.put("dbName","a");
        entity.setParams(map);

        CamelContext camelContext = new DefaultCamelContext();

        ProducerTemplate p = camelContext.createProducerTemplate();
        p.sendBodyAndHeaders("direct:testrest",entity,map);
        //p.sendBody("direct:testrest",entity);

        ConsumerTemplate consumerTemplate = camelContext.createConsumerTemplate();
        String message =  consumerTemplate.receiveBody("seda:testrest",String.class);
    }

}
