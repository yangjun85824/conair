package com.yyld.conair.druid.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisPlusConfig
 * @Description
 * @Author yangj
 * @Date 2022/6/15 17:57
 * @Vresion 1.0
 **/
@Configuration  //spring中常用到注解，与xml配置相对立。是两种加载bean方式
@MapperScan({"com.yyld.conair.**.**.mapper"}) // 扫描mapperdao的地址
public class MybatisPlusConfig {

    // 最新版  分页插件   没有配置这个分页插件的是不可以进行分页的
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setMaxLimit(500L);
        paginationInnerInterceptor.setOverflow(true);

        interceptor.addInnerInterceptor(paginationInnerInterceptor);//这是分页拦截器
        return interceptor;
    }

    // 配置Druid的监控
    // 1、配置一个管理后台的Servlet
    /*@Bean
    public ServletRegistrationBean<javax.servlet.Servlet> statViewServlet() {
        //登陆界面的url
        ServletRegistrationBean<javax.servlet.Servlet> bean = new ServletRegistrationBean<javax.servlet.Servlet>(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        // 初始化用户
        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "admin");
        bean.setInitParameters(initParams);
        return bean;
    }

    // 2、配置一个web监控的filter
    @Bean
    public FilterRegistrationBean statFilter() {
        //创建过滤器
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //设置过滤器过滤路径
        filterRegistrationBean.addUrlPatterns("/*");
        //忽略过滤的形式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }*/

}
