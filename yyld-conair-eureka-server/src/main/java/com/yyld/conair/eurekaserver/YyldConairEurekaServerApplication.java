package com.yyld.conair.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class YyldConairEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(YyldConairEurekaServerApplication.class, args);
    }

}
