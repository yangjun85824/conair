package com.yyld.conair.friend.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL注入包装类
 *
 * @author CL
 *
 */
@Slf4j
public class SqlInjectHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static Logger logger = Logger.getLogger(SqlInjectHttpServletRequestWrapper.class);

    /**
     * 构造请求对象
     *
     * @param request
     */
    public SqlInjectHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
    }

    /**
     * 获取头部参数
     *
     * @param v 参数值
     */
    @Override
    public String getHeader(String v) {
        String header = super.getHeader(v);
        if (header == null || "".equals(header)) {
            return header;
        }
        return sqlFilter(header);
    }

    /**
     * 获取参数
     *
     * @param v 参数值
     */
    @Override
    public String getParameter(String v) {
        String param = super.getParameter(v);
        if (param == null || "".equals(param)) {
            return param;
        }
        return sqlFilter(param);
    }

    /**
     * 获取参数值
     *
     * @param v 参数值
     */
    @Override
    public String[] getParameterValues(String v) {
        String[] values = super.getParameterValues(v);
        if (values == null) {
            return values;
        }

        // 富文本内容不过滤
        if ("remarks".equals(v)) {
            return values;
        }

        int length = values.length;
        String[] resultValues = new String[length];
        for (int i = 0; i < length; i++) {
            // 过滤特殊字符
            resultValues[i] = sqlFilter(values[i]);
            if (!(resultValues[i]).equals(values[i])) {
                log.debug("SQL注入过滤器 => 过滤前：{} => 过滤后：{}", values[i], resultValues[i]);
            }
        }
        return resultValues;
    }

    /**
     * 预编译SQL过滤正则表达式
     */
    //private Pattern sqlPattern = Pattern.compile(
    //        "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)",
    //        Pattern.CASE_INSENSITIVE);
            //去掉or、and、char、count关键字，因为在规则传递中会有该类关键字
    private Pattern sqlPattern = Pattern.compile(
            "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|delete|insert|trancate|into|substr|ascii|declare|exec|master|into|drop|execute)\\b)",
            Pattern.CASE_INSENSITIVE);
    /**
     * SQL过滤
     *
     * @param v 参数值
     * @return
     */
    private String sqlFilter(String v) {
        
        if (StringUtils.isBlank(v)){
            return null;
        }
        
        return StringEscapeUtils.escapeSql(v);
        //if (v != null) {
        //    String resultVal = v;
        //    Matcher matcher = sqlPattern.matcher(resultVal);
        //    if (matcher.find()) {
        //        resultVal = matcher.replaceAll("");
        //    }
        //    if (!resultVal.equals(v)) {
        //        return "";
        //    }
        //    return resultVal;
        //}
        //return null;
    }

    /**
     * 重写getInputStream方法
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        logger.debug("参数去空格处理");

        //非json类型，直接返回
        if (!super.getHeader(HttpHeaders.CONTENT_TYPE).equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            return super.getInputStream();
        }
        //从输入流中取出body串, 如果为空，直接返回
        String reqBodyStr = IOUtils.toString(super.getInputStream(), "utf-8");
        if (StringUtils.isEmpty(reqBodyStr)) {
            return super.getInputStream();
        }
        logger.debug("转化前reqest body:{}"+reqBodyStr);

        //reqBodyStr转为Map对象
        Map<String, Object> paramMap = new ObjectMapper().readValue(reqBodyStr, new TypeReference<HashMap<String, Object>>() {
        });
        //去首尾空格
        Map<String,Object> trimedMap = recursiveTrim(paramMap);
        logger.debug("转化后reqest body：" + JSON.toJSONString(trimedMap));

        //重新构造一个输入流对象
        byte[] bytes = JSON.toJSONString(trimedMap).getBytes("utf-8");
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        return new MyServletInputStream(bis);
    }

    /**
     * 只处理String, List, Map
     *
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String,Object> recursiveTrim(Map<String, Object> param) {
        for (String key : param.keySet()) {
            Object vo = param.get(key);
            if (null == vo) {
                //key对应的值为空
                continue;
            }
            if (vo instanceof String) {
                //key对应的值为String类型, 去空格后重新放入map
                //param.put(key, StringUtils.trimToEmpty((String) vo));
                param.put(key, sqlFilter(vo.toString()));

            } else if (vo instanceof Map) {
                param.put(key, recursiveTrim((Map<String,Object>) vo));
            } else if (vo instanceof List) {
                //key对应的值为List类型
                List<Object> alist = (List<Object>) vo;
                for (int i = 0; i < alist.size(); i++) {
                    //遍历list
                    Object vol = alist.get(i);
                    if (vol instanceof String) {
                        //list里的元素为String, 去空格
                        //alist.set(i, StringUtils.trimToEmpty((String) vol));
                        alist.set(i,sqlFilter(vo.toString()));
                    } else if (vol instanceof Map) {
                        //list里的元素为Map, 递归处理
                        alist.set(i, recursiveTrim((Map<String,Object>) vol));
                    }
                }
                param.put(key, vo);
            }
        }
        return param;

    }

}
class MyServletInputStream extends ServletInputStream {

    private ByteArrayInputStream bis;

    public MyServletInputStream(ByteArrayInputStream bis) {
        this.bis = bis;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {

    }

    @Override
    public int read() throws IOException {
        return bis.read();
    }
}