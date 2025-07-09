package com.webtest;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;

public class DynamicCodeExecution {

    public static void main( String[] args ) throws Exception {
        String code = "public class Man {\n" +
                "\tpublic void hello(){\n" +
                "\t\tSystem.out.println(\"hello world\");\n" +
                "\t}\n" +
                "}";
        String className = "Man";

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaSourceManager javaFileManager = new JavaSourceManager(compiler.getStandardFileManager(null, null, null));

        JavaSourceFromString javaFileObject = new JavaSourceFromString(className, code);
        compiler.getTask(null, javaFileManager, null, null, null, Arrays.asList(javaFileObject)).call();

        JavaSourceFromString myJavaFileObject = javaFileManager.getMyJavaFileObject();

        ClassLoader classloader = new DynamicClassLoader(myJavaFileObject);
        Class<?> clazz  = classloader.loadClass(className);
        Method method = clazz.getMethod("hello");
        method.invoke(clazz.newInstance());





        // 创建一个新的字节数组输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 创建一个新的打印流，指向字节数组输出流
        PrintStream ps = new PrintStream(baos);

        // 保存旧的打印流
        PrintStream originalOut = System.out;

        try {
            // 重定向标准输出到新的打印流
            System.setOut(ps);

            // 写入一些输出到控制台
            System.out.println("This will be captured");

            // 恢复原来的标准输出
            System.setOut(originalOut);

            // 获取之前写入的内容
            String capturedLog = baos.toString();
            // 打印捕获的日志
            System.out.println("Captured Log:");
            System.out.println(capturedLog);

        } finally {
            // 关闭打印流
            ps.close();
        }

    }

    public static class DynamicClassLoader extends ClassLoader {
        private byte[] bytes;

        public DynamicClassLoader(JavaSourceFromString myJavaFileObject) {
            this.bytes = myJavaFileObject.toBytes();
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            return defineClass(name, bytes, 0, bytes.length);
        }
    }

    public static class JavaSourceFromString extends SimpleJavaFileObject {
        private String source;
        private ByteArrayOutputStream baos;

        public JavaSourceFromString(String name, String source) {
            super(URI.create("String:///" + name + Kind.SOURCE.extension), Kind.SOURCE);
            this.source = source;
        }

        public JavaSourceFromString(String name, Kind kind){
            super(URI.create("String:///" + name + kind.extension), kind);
            source = null;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors){
            if(source == null){
                throw new IllegalArgumentException("source == null");
            }
            return source;
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            baos = new ByteArrayOutputStream();
            return baos;
        }

        public byte[] toBytes(){
            return baos.toByteArray();
        }
    }

    public static class JavaSourceManager extends ForwardingJavaFileManager<JavaFileManager> {
        private JavaSourceFromString javaSourceFromString;

        protected JavaSourceManager(JavaFileManager fileManager) {
            super(fileManager);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String qualifiedClassName, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
            JavaSourceFromString javaSource = new JavaSourceFromString(qualifiedClassName, kind);
            this.javaSourceFromString = javaSource;
            return javaSource;
        }

        public JavaSourceFromString getMyJavaFileObject() {
            return javaSourceFromString;
        }
    }
}

