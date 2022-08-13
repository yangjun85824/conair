package com.myself.mybatisplusgeneratecode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: AT
 * @Date: 2021/3/11 9:30 上午
 */
@Configuration
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {

    /**跨域处理*/
    @Bean
    public CorsFilter corsFilter() {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //放行哪些原始域
        config.addAllowedOrigin("*");
        //放行哪些原始域(请求方式)
        config.addAllowedMethod("*");
        //放行哪些原始域(头部信息)
        config.addAllowedHeader("*");

        //2.添加映射路径
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        Map<String, CorsConfiguration> map = new HashMap<>();
        map.put("/**", config);
        configSource.setCorsConfigurations(map);

        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);
    }
}
