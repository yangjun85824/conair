package com.webtest.utils;

import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class DynCodeRun {

    public static void main(String[] args) {
        String code = "public void test(String a,String b){System.out.println(\"参数打印：\"+a);System.out.println(\"参数打印：\"+b);}";
        String className = "TEST";
        String params = "{\"a\":\"1\",\"b\":\"2\"}";
        String method = "test";
        try {
            run(code,className,params,method);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object run(String code,String className,String params,String methodName) throws Exception {

        //参数抽离

        Map paramsMap = JSONObject.parseObject(params, Map.class);

        String codeBuff = handlerCode(code,className);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaSourceManager javaFileManager = new JavaSourceManager(compiler.getStandardFileManager(null, null, null));

        JavaSourceFromString javaFileObject = new JavaSourceFromString(className, codeBuff);
        compiler.getTask(null, javaFileManager, null, null, null, Arrays.asList(javaFileObject)).call();

        JavaSourceFromString myJavaFileObject = javaFileManager.getMyJavaFileObject();

        ClassLoader classloader = new DynamicClassLoader(myJavaFileObject);
        Class<?> clazz  = classloader.loadClass(className);
        Method methodRun = null;//clazz.getMethod(methodName);
        List<Object> objects = new ArrayList<>();
        //获取本类的所有方法，存放入数组
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("方法名："+method.getName());
            String mName = method.getName();
            if (StringUtils.equals(mName,methodName)){
                methodRun = method;
                methodRun.getParameters();
                //获取本方法所有参数类型，存入数组
                Class<?>[] getTypeParameters = method.getParameterTypes();
                if(getTypeParameters.length==0){
                    System.out.println("此方法无参数");
                }
                objects = (List<Object>) paramsMap.values().stream().collect(Collectors.toList());
                for (Class<?> class1 : getTypeParameters) {
                    String parameterName = class1.getName();
                    System.out.println("参数类型："+parameterName);
//                    objects.addAll();
                }
                System.out.println("****************************");
                break;
            }
        }
        if (StringUtils.isBlank(params)) {
            methodRun.invoke(clazz.newInstance(), null);
        }
        String [] a = {"1","2"};
        if (StringUtils.isNotBlank(params)) {
            methodRun.invoke(clazz.newInstance(), objects.toArray());
        }
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
        return null;
    }

    //处理方法
    private static String handlerCode(String code, String className) {

        String tempCode = code.replaceAll(" ","");
        String prefix = tempCode.substring(0,16);

        if (StringUtils.equals("publicfinalclass",prefix)){
            return  code;
        }
        if (StringUtils.contains(prefix,"publicclass")){
            return  code;
        }

        return "public class "+className+"{"+code+"}";
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
