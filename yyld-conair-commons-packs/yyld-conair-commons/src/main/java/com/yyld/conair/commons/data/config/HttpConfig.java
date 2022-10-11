package com.yyld.conair.commons.data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName HttpConfig
 * @Description //TODO
 * @Author yangj
 * @Date 2022/8/30 15:30
 * @Vresion 1.0
 **/
@Configuration
public class HttpConfig {

    /**
     * 两分钟超时时间
     */
    private int outtime = 2 * 60 * 1000;
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(outtime);
        requestFactory.setReadTimeout(outtime);
        return new RestTemplate();
    }

}
