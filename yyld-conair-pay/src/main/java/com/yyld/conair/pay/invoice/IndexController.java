package com.yyld.conair.pay.invoice;

import com.alibaba.fastjson.JSON;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.yyld.conair.pay.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
public class IndexController {

    @Resource
    private InvoiceService invoiceService;

    @GetMapping("/")
    public ModelAndView url(ModelAndView model) {

        model.addObject("aliurl","weixin://dl/business/?ticket=http://pay.yiyld.com/pay/wx/accessToken");
        model.setViewName("index");
        return model;

    }
    //阿里回调
    @GetMapping("/notify_url")
    public String notifyUrl(HttpServletRequest request) {

        System.out.println(request.getParameter("request"));
        System.out.println("阿里异步接口回调"+new Date());
        return "index";

    }

    //阿里授权回调
    @GetMapping("/callback")
    public String callback(ModelAndView model) {

        System.out.println("阿里授权回调"+new Date());
        model.addObject("aliurl",invoiceService.url());
//        model.setViewName("index");
        return "index";

    }

    //阿里AUTH授权回调  请求后回调：https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2016120103696853&scope=auth_user&redirect_uri=http://pay.yiyld.com/pay/auth_callback
    @GetMapping("/auth_callback")
//    @ResponseBody
    public Object authCallback(HttpServletRequest request,ModelAndView model) {

        String auth_code = request.getParameter("auth_code");
        System.out.println(auth_code);
        System.out.println("阿里授权回调"+new Date());

        //auth_code：临时授权码，一次有效，auth_code 有效期为 3 分钟到 24 小时（开放平台规则会根据具体的业务场景动态调整 auth_code 的有效期，但是不会低于 3 分钟，同时也不会超过 24 小时），超过有效期的 auth_code 即使未使用也将无法使用。 用户的每次授权动作都会生成一个新的 auth_code。
//        model.addObject("authurl","http://example.com/doc/toAuthPage.html?app_id=2021003144657033&source=alipay_wallet&scope=auth_user&auth_code="+auth_code);
//        model.addObject("code",auth_code);

        invoiceService.createClient("");

        //获取accessToken
        AlipaySystemOauthTokenResponse auth = invoiceService.getAccessTokenAndUserId(auth_code);
        //查询用户详情
        AlipayUserInfoShareResponse resultInfo = invoiceService.getAliUserInfo(auth.getAccessToken());

        Map<String,Object> result = new HashMap<>();

        result.put("auth",auth);
        result.put("resultInfo",resultInfo);
        result.put("invoiceurl","/pay/invoice/invoiceList?access_token="+auth.getAccessToken()+"&user_id="+resultInfo.getUserId());

        model.addObject("auth",auth);
        model.addObject("resultInfo",resultInfo);
        model.addObject("invoiceurl","/pay/invoice/invoiceList?access_token="+auth.getAccessToken()+"&user_id="+resultInfo.getUserId());

        model.setViewName("invoice");

        return model;

    }


    //阿里AUTH授权回调  请求后回调：https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2016120103696853&scope=auth_invoice_per&redirect_uri=http://pay.yiyld.com/pay/auth_callback
    @GetMapping("/auth_invoice_callback")
//    @ResponseBody
    public Object authInvoiceCallback(HttpServletRequest request,ModelAndView model) {

        String auth_code = request.getParameter("auth_code");
        System.out.println(auth_code);
        System.out.println("阿里授权回调"+new Date());

        //auth_code：临时授权码，一次有效，auth_code 有效期为 3 分钟到 24 小时（开放平台规则会根据具体的业务场景动态调整 auth_code 的有效期，但是不会低于 3 分钟，同时也不会超过 24 小时），超过有效期的 auth_code 即使未使用也将无法使用。 用户的每次授权动作都会生成一个新的 auth_code。
//        model.addObject("authurl","http://example.com/doc/toAuthPage.html?app_id=2021003144657033&source=alipay_wallet&scope=auth_user&auth_code="+auth_code);
//        model.addObject("code",auth_code);

        invoiceService.createClient("");

        //获取accessToken
        AlipaySystemOauthTokenResponse auth = invoiceService.getAccessTokenAndUserId(auth_code);
        //查询用户详情

        Map<String,Object> result = new HashMap<>();

        result.put("auth",auth);
        result.put("resultInfo",null);
        result.put("invoiceurl","/pay/invoice/invoiceList?access_token="+auth.getAccessToken());

        model.addObject("auth",auth);
        model.addObject("invoiceurl","/pay/invoice/invoiceList?access_token="+auth.getAccessToken());

        model.setViewName("invoice");

        return model;

    }
}
