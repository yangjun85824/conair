package com.yyld.conair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YyldConairApplication {

    public static void main(String[] args) {

//        SpringApplication.run(YyldConairApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(YyldConairApplication.class, args);
        Environment environment = context.getBean(Environment.class);
        String port = environment.getProperty("server.port");
        String ctxPath = environment.getProperty("server.servlet.context-path");
        ctxPath = ctxPath == null ? "/" : ctxPath;

        System.out.println("系统 已启动！请访问 http://localhost:" + port + ctxPath);
    }

}
