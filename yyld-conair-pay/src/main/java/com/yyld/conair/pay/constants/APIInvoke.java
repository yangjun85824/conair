package com.yyld.conair.pay.constants;

import com.yyld.conair.commons.data.utils.SpringContextUtil;
import com.yyld.conair.pay.invoice.IndexController;
import com.yyld.conair.pay.invoice.service.InvoiceService;
import com.yyld.conair.pay.invoice.service.impl.InvoiceServiceImpl;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName APIContants
 * @Description //TODO
 * @Author yangj
 * @Date 2022/8/26 16:34
 * @Vresion 1.0
 **/
@Order(10)
@Component
public final class APIInvoke {

    public APIInvoke() {}

    public static Map<String, Object> API_MAP_ = null;

    public static Map<String, String> API_METHOD_MAP_ = null;

    public Map<String, Object> init() {

        API_MAP_ = new HashMap<>();

        API_MAP_.put("alipay", SpringContextUtil.getBean(IndexController.class));

        API_METHOD_MAP_.put("url", "url");

        return API_MAP_;
    }

    public Object invokeMethod(String className, String method , Object...objs) throws Exception{

        API_MAP_ = API_MAP_ == null ? init() : API_MAP_;

        Object cla = API_MAP_.get(className);

        String methodName = API_METHOD_MAP_.get(method);

        Class claClass = cla.getClass();//动态加载类，获取当前类的Class对象
        //忽略方法声明关键字
        Method methods = claClass.getDeclaredMethod(methodName,objs.getClass());
//        Method methods = claClass.getMethod(method,objs.getClass());
        //暴力反射
        methods.setAccessible(true);
//        Method[] methods = claClass.getMethods();
        return methods.invoke(claClass.newInstance(),objs);

       /* int len = objs.length;

        Method mh = null;

        mm: for (Method md : methods){

            String mName = md.getName();
            int parameterCount = md.getParameterCount();

            if (mName.equals(method) && parameterCount-len == 0){
                mh = md;
                break mm;
            }

        }

        Class<?>[] getTypeParameters = mh.getParameterTypes();
        if(getTypeParameters.length==0){
            return mh.invoke(claClass.newInstance());//通过对象，调用有参数的方法
        }
        return mh.invoke(claClass.newInstance(), objs);//通过对象，调用有参数的方法*/

    }
}
