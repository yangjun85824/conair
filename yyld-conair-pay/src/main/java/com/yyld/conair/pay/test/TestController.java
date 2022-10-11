package com.yyld.conair.pay.test;

import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.yyld.conair.pay.constants.APIInvoke;
import com.yyld.conair.pay.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
//@RestController
@Controller
public class TestController {

    @Autowired
    private APIInvoke apiInvoke;

    @GetMapping("/test/{type}/{callback}")
    @ResponseBody
    public Object url(@PathVariable("type") String type,
                      @PathVariable("callback") String callback,
                      HttpServletRequest request) throws Exception {

        Object result = apiInvoke.invokeMethod(type,callback,request);

        System.out.println(result);

        return result;

    }

}
