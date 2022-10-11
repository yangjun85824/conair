package com.yyld.conair.pay.invoice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yyld.conair.commons.data.config.HttpService;
import com.yyld.conair.commons.data.utils.JsapiTicketUtil;
import com.yyld.conair.pay.utils.CheckoutUtils;
import com.yyld.conair.pay.utils.WXPayConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName WXContoller
 * @Description //TODO
 * @Author yangj
 * @Date 2022/8/30 14:50
 * @Vresion 1.0
 **/
@Controller
@RequestMapping("/wx")
public class WXContoller {

    @Resource
    private HttpService http;

    //微信接入的token功能   用于基础配置校验
    @GetMapping("/token")
    @ResponseBody
    public String token(HttpServletRequest request) {

        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (signature != null && echostr != null && CheckoutUtils.checkSignature(signature, timestamp, nonce)) {
            //服务器接口提交时认证，一般就调用一次
            System.out.println("服务器认证："+echostr);
            return echostr;
            //文档内是说echostr是随机字符串，实际是一串数字，如果返回字符串校验失败，可以尝试返回数字
            //return Long.valueOf(echostr);
        } else {
            //用户进行关注、取关等事件时的处理逻辑，根据事件类型，与自己的业务逻辑进行挂钩
        }

        return "beever";

    }

    //获取accessToken
    @GetMapping("/accessToken")
    @ResponseBody
    public Object accessToken() {

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxac0260a3edc73444&secret=36e4e1f7608b3743f2a4a0989fb700d8";
        Object result = http.get(url,new HashMap<>(),Object.class);

        WXPayConstant.ACCESS_TOKEN_= JSON.toJSONString(result);

        System.out.println(JSON.toJSONString(result));
        return result;
    }

    //跳转到界面
    @GetMapping("/jssdk_rule")
    public Object jssdkRule(ModelAndView modelAndView , String url) {

        /**
         * appId: "wx00000000000000",
         *         timestamp: 1489030247,
         *         nonceStr: "(9J4YRV[#@",
         *         signature: "f027317f8910000000000000000000",
         */
        modelAndView.addObject("appId","wxac0260a3edc73444");

        url = url==null || "".equals(url) ? "http://pay.yiyld.com/pay/wx/jssdk_rule" : url;

        long timestamp = System.currentTimeMillis();
        String nonce = getRandomString(32);

        System.out.println("WXPayConstant.ACCESS_TOKEN_============"+WXPayConstant.ACCESS_TOKEN_);

        String ticket = (WXPayConstant.TICKET_ == null || "".equals(WXPayConstant.TICKET_)) ? getTicket(getAccessToken(WXPayConstant.ACCESS_TOKEN_)) : WXPayConstant.TICKET_;

        System.out.println("ticket=============="+ticket);

        //将参数排序并拼接字符串
        String str = "jsapi_ticket="+ticket+"&noncestr="+nonce+"&timestamp="+timestamp+"&url="+url;
        String signature = JsapiTicketUtil.SHA1(str);

        modelAndView.addObject("timestamp",timestamp);
        modelAndView.addObject("nonceStr",nonce);
        modelAndView.addObject("signature",signature);
        modelAndView.setViewName("wx");

        System.out.println("signature======"+signature);

        return modelAndView;
    }

    public String getTicket(String access_token) {
        String ticket = null;
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token +"&type=jsapi";//这个url链接和参数不能变
        try {

            Object result = http.get(url,new HashMap<>(),Object.class);

            System.out.println(result);

            String message = JSON.toJSONString(result);
            JSONObject demoJson = JSONObject.parseObject(message);
            System.out.println("JSON字符串："+demoJson);
            ticket = demoJson.getString("ticket");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticket;
    }

    //获取accessToken
    @PostMapping("/getinvoicebatch")
    @ResponseBody
    public Object getinvoicebatch(@RequestBody List<Map<String,String>> invoiceList) {


        String apiUrl = "https://api.weixin.qq.com/card/invoice/reimburse/getinvoicebatch?access_token="+getAccessToken(WXPayConstant.ACCESS_TOKEN_);

        Map<String,List<Map<String,String>>> paramsMap = new HashMap<>();
        paramsMap.put("item_list",invoiceList);

        Map<String , String> headerMap = new HashMap<>();

        headerMap.put("Content-Type","application/json");

        JSONObject params = JSONObject.parseObject(JSON.toJSONString(paramsMap));

        System.out.println("JSON.toJSONString(paramsMap)===="+JSON.toJSONString(paramsMap));

        JSONObject result = http.post(apiUrl,headerMap,params);

        System.out.println(JSON.toJSONString(result));

        return result;
    }

    //获取accessToken
    @PostMapping("/getinvoiceinfo")
    @ResponseBody
    public Object getinvoiceinfo(String card_id,String encrypt_code) {


        String apiUrl = "https://api.weixin.qq.com/card/invoice/reimburse/getinvoiceinfo?access_token="+getAccessToken(WXPayConstant.ACCESS_TOKEN_);

        Map<String , String > invoiceInfo = new HashMap<>();

        invoiceInfo.put("card_id",card_id);
        invoiceInfo.put("encrypt_code",encrypt_code);

        Map<String , String> headerMap = new HashMap<>();

        headerMap.put("Content-Type","application/json");

        JSONObject result = http.post(apiUrl,headerMap,JSONObject.parseObject(JSON.toJSONString(invoiceInfo)));

        System.out.println(JSON.toJSONString(result));

        return result;
    }

    public String getRandomString(int length){

        //1. 定义一个字符串（A-Z，a-z，0-9）即73个数字字母；

        String str="~!@#$%^&*()zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

        //2. 由Random生成随机数

        Random random=new Random();

        StringBuffer sb=new StringBuffer();

        //3. 长度为几就循环几次

        for(int i=0; i<length; ++i){

            //从62个的数字或字母中选择

            int number=random.nextInt(73);

            //将产生的数字通过length次承载到sb中

            sb.append(str.charAt(number));

        }

        //将承载的字符转换成字符串

        return sb.toString();

    }

    private String getAccessToken(String tokenEntity){

        Map<String , Object> entityMap = JSONObject.parseObject(tokenEntity);

        String token = entityMap.get("access_token").toString();

        System.out.println("token========"+token);

        return token;

    }
}
