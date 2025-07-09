package com.webtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class ConsoleLogger {

    public static Logger logger = LoggerFactory.getLogger(ConsoleLogger.class);
    private static StringBuilder logBuffer = new StringBuilder();

    public static void main(String[] args) {
        redirectSystemOutAndErr();

        for (int i =0 ; i< 10 ; i++) {
            System.out.println("This is a test message.");

            logger.info("=="+i);
        }
        String log = getConsoleLog();
        System.out.println("Console log: \n" + log);
    }

    public static String print(){

//        System.out.println("55555555555555");

        redirectSystemOutAndErr();

//        System.out.println("This is a test message.");
        String log = getConsoleLog();
        System.out.println("Console log: \n" + log);
        return log;
    }
    private static void redirectSystemOutAndErr() {
        System.setOut(new PrintStream(new ConsoleOutputStream(System.out)));
        System.setErr(new PrintStream(new ConsoleOutputStream(System.err)));
    }

    private static String getConsoleLog() {
        return logBuffer.toString();
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
