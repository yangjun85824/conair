package com.yyld.conair.ds;

import com.yyld.conair.commons.data.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

@Slf4j
@EnableOpenApi
@EnableWebMvc
@ServletComponentScan("com.yyld.conair.ds.servlet")
@SpringBootApplication(scanBasePackages = {"com.yyld.conair,com.yyld.conair.commons.data,com.yyld.conair.ds"})
public class YyldConairDsApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(YyldConairDsApplication.class, args);

        log.info("springboot启动成功");

        SpringContextUtil.setApplicationContext(applicationContext);
    }

}
