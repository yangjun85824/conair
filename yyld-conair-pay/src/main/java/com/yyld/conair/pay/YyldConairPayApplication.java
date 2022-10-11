package com.yyld.conair.pay;

import com.yyld.conair.commons.data.utils.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.yyld.conair"})
public class YyldConairPayApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(YyldConairPayApplication.class, args);
        SpringContextUtil.setApplicationContext(applicationContext);
    }

}
