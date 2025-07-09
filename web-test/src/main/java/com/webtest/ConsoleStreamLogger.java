package com.webtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class ConsoleStreamLogger {

    public static Logger logger = LoggerFactory.getLogger(ConsoleStreamLogger.class);
    private static StringBuilder logBuffer = new StringBuilder();

    public static void main(String[] args) {
        // 获取控制台原始输出流
//        OutputStream originalOut = System.out;
//
//        try {
//            // 创建一个字符串缓冲区来接收输出
//            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//            PrintStream printStream = new PrintStream(outStream);
//
//            // 将系统输出重定向到PrintStream
//            System.setOut(printStream);
//
//            // 打印一些日志
//            System.out.println("这是一条日志信息");
//
//            // 获取缓冲区内容作为字符串
//            String consoleOutput = outStream.toString();
//
//            // 输出从控制台获取的日志信息
//            System.out.println("控制台输出:\n" + consoleOutput);
//
//        } finally {
//            // 最后确保将系统输出重定向回原来的地方
//            System.setOut(new PrintStream(originalOut));
//        }


        // 创建一个字符串缓冲区来保存输出
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        // 保存当前的System.out，然后替换为PrintStream
        PrintStream old = System.out;
        System.setOut(ps);

        // 写入一些日志
        System.out.println("这是一条控制台日志");
        logger.info("222222");
        // 恢复原来的System.out
        System.setOut(old);

        // 获取输出的内容
        String log = baos.toString();
        System.out.println("捕获的日志：" + log);
    }
    static class ConsoleOutputStream extends OutputStream {
        private OutputStream original;

        public ConsoleOutputStream(OutputStream original) {
            this.original = original;
        }

        @Override
        public void write(int b) throws IOException {
            logBuffer.append((char) b);
            original.write(b);
        }
    }
}
