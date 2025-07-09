//package com.yyld.conair.sources;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//
///**
// * Druid过滤器
// *
// * @author CL
// */
//@Configuration
//@WebFilter(urlPatterns = "/*", filterName = "DruidFilter")
//@Order(Integer.MAX_VALUE)
//public class DruidFilter implements Filter {
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    private static List<String> URLS = Arrays.asList("/druid/index.html", "/druid/datasource.html", "/druid/sql.html", "/druid/wall.html", "/druid/webapp.html"
//            , "/druid/weburi.html", "/druid/websession.html", "/druid/spring.html", "/druid/api.html");
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("init-----------filter");
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
////        logger.info("开始校验");
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String requestName = request.getRequestURI();
//
//        /*顺手解决跨域问题*/
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//
//        logger.info("requestName========================" + requestName);
//
//        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
//        logger.info("path========================" + path);
//
//        boolean allowedPath = URLS.contains(path);
//        if (!allowedPath) {
//            String paths[] = path.split("/");
//
//            String p = paths.length > 1 ? paths[1] : paths[0];
//            if (StringUtils.equals(p, "druid")) {
//                allowedPath = true;
//            }
//        }
//
//        if (!allowedPath) {
//            logger.info("请求存在，放行");
//            chain.doFilter(request, response);
//        } else {
//            logger.info("请求不存在，终止");
//            response.setCharacterEncoding("UTF-8");//设置将字符以"UTF-8"编码输出到客户端浏览器
//            response.setHeader("content-type", "text/html;charset=UTF-8");//通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
//            response.getWriter().write("非法请求");
//        }
//    }
//
//    @Override
//    public void destroy() {
//        logger.info("过滤器销毁了");
//    }
//
//}
//
//
