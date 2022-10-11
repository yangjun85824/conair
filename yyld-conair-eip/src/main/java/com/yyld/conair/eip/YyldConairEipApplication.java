package com.yyld.conair.eip;

import com.yyld.conair.commons.data.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Slf4j
//@EnableWebMvc
@SpringBootApplication(scanBasePackages = {"com.yyld.conair"})
public class YyldConairEipApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(YyldConairEipApplication.class, args);

        log.info("springboot-EIP 启动成功");

        SpringContextUtil.setApplicationContext(applicationContext);
    }

    /**
     * 如果需要使用 rest及servlet的相关功能就需要配置 ServletRegistrationBean
     *
     * 该实例的配置根目录 /camel/*
     *
     **/
    //@Bean
    //public ServletRegistrationBean camelServletRegistrationBean() {
    //    ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(), "/camel/*");
    //    registration.setName("CamelServlet");
    //    return registration;
    //}
}
