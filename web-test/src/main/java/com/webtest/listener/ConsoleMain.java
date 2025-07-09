package com.webtest.listener;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class ConsoleMain {


    public static void main(String[] args) {
        // 创建 ConsoleListener 实例
        ConsoleListener listener = new ConsoleListener();

        // 重定向 System.out
        PrintStream console = System.out;
        OutputStream outputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                console.write(b); // 将控制台输出写回 System.out
                // 在这里处理控制台输出的监听逻辑
            }
        };
        System.setOut(new PrintStream(outputStream));

        // 启动监听线程
        Thread thread = new Thread(listener);
        thread.start();

        // 模拟控制台输出
        System.out.println("Hello, World!");
    }

}
