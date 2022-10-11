package com.yyld.conair.commons.data.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName HttpService
 * @Description //TODO
 * @Author yangj
 * @Date 2022/8/30 15:39
 * @Vresion 1.0
 **/
@Order(1)
@Service
public final class HttpService {

    @Resource
    private RestTemplate restTemplate;

    /**
     * get请求
     *
     * @param url       请求地址
     * @param headerMap 头部信息
     * @param resp      响应结果类型
     * @return
     */
    public Object get(String url, Map<String, String> headerMap, Class<?> resp) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, String> stringStringEntry : headerMap.entrySet()) {
            httpHeaders.add(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<?> result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, resp);
        return result.getBody();
    }
    /**
     * post 请求
     *
     * @param url        请求地址
     * @param headerMap  头信息
     * @param jsonObject 请求参数 JSON
     * @return JSONObject
     */
    public JSONObject post(String url, Map<String, String> headerMap, JSONObject jsonObject) {
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        for (Map.Entry<String, String> stringStringEntry : headerMap.entrySet()) {
            httpHeaders.add(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        HttpEntity httpEntity = new HttpEntity(jsonObject, httpHeaders);
        JSONObject result = restTemplate.postForObject(url, httpEntity, JSONObject.class);
        return result;
    }

}
