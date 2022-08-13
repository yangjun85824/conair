package com.myself.mybatisplusgeneratecode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description:
 * @author: AT
 * @Date: 2021/1/23 4:18 下午
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("xx公司_xx项目_接口文档")
                        .description("描述内容")
                        .version("2.0.0")
                        .build())
                .select()
//                在此放入项目的controller包
                .apis(RequestHandlerSelectors.basePackage(""))
                .paths(PathSelectors.any())
                .build();
    }
}
