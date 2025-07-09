package com.webtest.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "testOneServlet", urlPatterns = "/test")// 通过注解@WebServlet指定name和url
public class ServletTest extends HttpServlet {

    public static Logger logger = LoggerFactory.getLogger(ServletTest.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.getWriter().print("Hello, servlet");

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        PrintWriter writer = resp.getWriter();

        writer.println("<html><head><style>html,body{background:black;color:#FFFFFF}</style></head><body>");

        for(int i = 0 ; i < 50 ; i++) {
            // 创建一个字符串缓冲区来保存输出。   该流不需要关闭，这是因为ByteArrayOutputStream是基于内存的流，而不是指向硬盘或网络的流。当ByteArrayOutputStream对象不再被使用时，其内部的字节数组会成为垃圾，由Java的垃圾回收机制自动清理。
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            // 保存当前的System.out，然后替换为PrintStream
            PrintStream old = System.out;
            System.setOut(ps);
            // 写入一些日志

            try {
                Thread.sleep(50);
                logger.info("i=================11111=========="+i);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                System.out.println("EXCEPTION------------"+e);
                e.printStackTrace();
            }
            // 恢复原来的System.out
            System.setOut(old);

            // 获取输出的内容
            String log = new String(baos.toByteArray(), StandardCharsets.UTF_8);

            log = log.replaceAll("(\u001B\\[2m)|(\u001B\\[0;39m)|(\u001B\\[36m)|(\u001B\\[35m)|(\u001B\\[32m)"," ");

            writer.println("<i>"+log+"</i>");
            writer.println("<br/>");
            writer.println("<i>--------------"+i+"</i>");
            writer.println("<br/>");
            writer.flush();

        }
        writer.println("<body><html>");
        writer.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}
