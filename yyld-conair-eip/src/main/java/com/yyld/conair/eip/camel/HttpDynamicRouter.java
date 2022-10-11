package com.yyld.conair.eip.camel;

import com.yyld.conair.eip.entity.RestEntity;
import org.apache.camel.ExchangeProperties;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class HttpDynamicRouter {

    public String http_1(String body, @ExchangeProperties Map<String, Object> properties) {
        //bodies.add(body);

        // get the state from the exchange properties and keep track how many times
        // we have been invoked
        int invoked = 0;
        Object current = properties.get("invoked");
        if (current != null) {
            invoked = Integer.valueOf(current.toString());
        }
        invoked++;
        // and store the state back on the properties
        properties.put("invoked", invoked);

        if (invoked == 1) {
            return "mock:a";
        } else if (invoked == 2) {
            return "mock:b,mock:c";
        } else if (invoked == 3) {
            return "direct:foo";
        } else if (invoked == 4) {
            return "mock:result";
        }

        // no more so return null
        return null;
    }

    public String http(RestEntity entity, @ExchangeProperties Map<String, Object> properties) {

        //start 官网提供 用于存储状态 保证线程案例
        int invoked = 0;
        Object current = properties.get("invoked");
        if (current != null) {
            invoked = Integer.valueOf(current.toString());
        }
        invoked++;
        properties.put("invoked", invoked);
        //end

        //路由类型
        String type = entity.getType();
        //路由参数
        Object params = entity.getParams();
        //请求类型
        String mothed = entity.getMothed();
        mothed = StringUtils.isNotBlank(mothed) ? mothed : "GET";
        //请求头
        Map<String , Object> headerMap = entity.getHeader();

        if ("GET".equals(mothed)){

            return entity.getHttpurl();
        }

        if ("POST".equals(mothed)){

            return entity.getHttpurl();
        }

        //官网提供   必须返回null 否则路由会无限循环
        return null;
    }

}
