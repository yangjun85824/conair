package com.yyld.conair.pay.invoice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.yyld.conair.pay.constants.PayConstants;
import com.yyld.conair.pay.invoice.service.InvoiceService;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

import static com.yyld.conair.pay.constants.PayConstants.ALIPAY_PUBLIC_KEY;
import static com.yyld.conair.pay.constants.PayConstants.XGT_ALIPAY_PUBLIC_KEY_;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private AlipayClient client;

    public String url() {
        // 1.唤起支付宝固定链接（请勿修改）
        String url = "https://render.alipay.com/p/s/i?appId=20000920&startMultApp=YES&url=";
        // 2.报销应用发票输出接收地址（与申请表中发票输出接收地址一致）
        String isvUrl = "http://pay.yiyld.com/pay/invoice/back";
        // 3.编码后的回调地址
        String encodeIsvUrl = URLEncoder.encode(isvUrl);//URLDecoder.decode(isvUrl);//UrlUtils.encode(isvUrl);
        // 4.发票输出组件固定链接
        String alipayUrl = "/www/invoiceSelect.htm?scene=INVOICE_EXPENSE&einvMerchantId=91430111MA4QWYK02B&serverRedirectUrl=" + encodeIsvUrl;
        // 5.编码之后的发票输出组件链接
        String encodeAlipayUrl = URLEncoder.encode(alipayUrl);//UrlUtils.encode(alipayUrl);
        // 6.唤起发票输出组件的完整链接
        String alipayLandingUrl = url + encodeAlipayUrl;

        return alipayLandingUrl;
    }

    @Override
    public AlipaySystemOauthTokenResponse getAccessTokenAndUserId(String auth_code) {

        /*AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                PayConstants.XGT_APP_ID_,
                PayConstants.XGT_PRIVATE_KEY_, "json", "utf-8",
                XGT_ALIPAY_PUBLIC_KEY_, "RSA2");*/

        AlipayClient alipayClient = this.getClient();
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(auth_code);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            System.out.println(oauthTokenResponse.getAccessToken());

            System.out.println("oauthTokenResponse=============" + JSON.toJSONString(oauthTokenResponse));

            return oauthTokenResponse;
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AlipayUserInfoShareResponse getAliUserInfo(String access_token) {

        AlipayClient alipayClient = this.getClient();
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();

        System.out.println("resultMap===============" + access_token);

        try {
            AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(request, access_token);
            System.out.println(userinfoShareResponse.getBody());

            System.out.println("userinfoShareResponse==============" + JSON.toJSONString(userinfoShareResponse));

            return userinfoShareResponse;
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
        }
        return null;
    }

    @Validate
    public void createClient(String type) {

        if ("xgt".equals(type)) {
            this.client = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                    PayConstants.XGT_APP_ID_,
                    PayConstants.XGT_PRIVATE_KEY_, "json", "utf-8",
                    XGT_ALIPAY_PUBLIC_KEY_, "RSA2");
            return;
        }
        this.client = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                PayConstants.ALIPAY_APPID,
                PayConstants.PRIVATE_KEY, "json", "utf-8",
                ALIPAY_PUBLIC_KEY, "RSA2");
    }

    //查询发票列表
    @Override
    public AlipayEbppInvoiceTokenBatchqueryResponse invoiceList(String invoice_token) throws AlipayApiException {

        AlipayClient alipayClient = this.getClient();//new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
        AlipayEbppInvoiceTokenBatchqueryRequest request = new AlipayEbppInvoiceTokenBatchqueryRequest();
        request.setBizContent("{" +
                "\"invoice_token\": \"" + invoice_token + "\"," +
                "\"scene\":\"INVOICE_EXPENSE\"" +
                "  }");
        AlipayEbppInvoiceTokenBatchqueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return response;
    }

    //查询发票详情
    @Override
    public AlipayEbppInvoiceDetailOutputQueryResponse invoiceInfo(String access_token, String user_id, String invoice_code, String invoice_no) throws AlipayApiException {

        AlipayClient alipayClient = this.getClient();//new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
        AlipayEbppInvoiceDetailOutputQueryRequest request = new AlipayEbppInvoiceDetailOutputQueryRequest();
        request.setBizContent("{" +
                "\"user_id\":\"" + user_id + "\"," +
                "\"invoice_code\":\"" + invoice_code + "\"," +
                "\"invoice_no\":\"" + invoice_no + "\"," +
                "\"scene\":\"INVOICE_EXPENSE\"" +
                "}");
        AlipayEbppInvoiceDetailOutputQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return response;
    }

    //查询发票原文件
    @Override
    public AlipayEbppInvoiceFileOutputQueryResponse invoiceInfoResourcesFile(String user_id, String invoice_code, String invoice_no, String invoiceNo) throws AlipayApiException {

        AlipayClient alipayClient = this.getClient();//new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
        AlipayEbppInvoiceFileOutputQueryRequest request = new AlipayEbppInvoiceFileOutputQueryRequest();
        request.setBizContent("{" +
                "\"user_id\":\"" + user_id + "\"," +
                "\"invoice_code\":\"" + invoice_code + "\"," +
                "\"invoice_no\":\"" + invoice_no + "\"," +
                "\"scene\":\"INVOICE_EXPENSE\"" +
                "}");
        AlipayEbppInvoiceFileOutputQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return response;
    }

    //拉模式  发票列表
    @Override
    public AlipayEbppInvoiceTaxnoBatchqueryResponse invoiceListPull(String access_token) throws AlipayApiException {

        System.out.println("access_token=================================" + access_token);

        AlipayClient alipayClient = this.getClient();//new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
        AlipayEbppInvoiceTaxnoBatchqueryRequest request = new AlipayEbppInvoiceTaxnoBatchqueryRequest();
        request.setBizContent("{" +
                "\"tax_no\":\"91430111MA4QWYK02B\"," +
                "\"invoice_kind_list\":[" +
                    "\"PLAIN\"" +
                "]," +
                "\"scene\":\"INVOICE_EXPENSE\"," +
//                "\"start_invoice_date\":\"2017-01-11\"," +
//                "\"end_invoice_date\":\"2017-07-11\"," +
                "\"limit_size\":20," +
                "\"enable_trade_out\":\"true\"," +
                "\"page_num\":1" +
                "  }");
        AlipayEbppInvoiceTaxnoBatchqueryResponse response = alipayClient.execute(request, access_token);

        System.out.println(" =============================== 发票调用结果 ： " + JSON.toJSONString(response));

        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return null;
    }
    //拉模式  发票详情
    /*@Override
    public AlipayEbppInvoiceDetailOutputQueryResponse invoiceInfoPull(String access_token) throws AlipayApiException {

        AlipayClient alipayClient = this.getClient();
        AlipayEbppInvoiceDetailOutputQueryRequest request = new AlipayEbppInvoiceDetailOutputQueryRequest();
        request.setBizContent("{" +
                "\"user_id\":\"20880000000000000\"," +
                "\"invoice_code\":\"1234567890\"," +
                "\"invoice_no\":\"12345678\"," +
                "\"scene\":\"INVOICE_EXPENSE\"," +
                "\"skip_expense_progress_sync\":false" +
                "  }");
        AlipayEbppInvoiceDetailOutputQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }

        return null;
    }
    //拉模式  发票原文件
    @Override
    public AlipayEbppInvoiceFileOutputQueryResponse invoiceInfoPullResourcesFile(String access_token) throws AlipayApiException {

        AlipayClient alipayClient = this.getClient();
        AlipayEbppInvoiceFileOutputQueryRequest request = new AlipayEbppInvoiceFileOutputQueryRequest();
        request.setBizContent("{" +
                "\"user_id\":\"2088550000000\"," +
                "\"invoice_code\":\"123\"," +
                "\"invoice_no\":\"111\"," +
                "\"scene\":\"INVOICE_EXPENSE\"," +
                "\"skip_expense_progress_sync\":false" +
                "  }");
        AlipayEbppInvoiceFileOutputQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return null;
    }*/

    private AlipayClient getClient() {
        return this.client;
    }

    public static void main(String[] args) {

        InvoiceServiceImpl impl = new InvoiceServiceImpl();

        String url = impl.url();

        System.out.println(url);

        try {

            impl.createClient("");
            AlipayEbppInvoiceTaxnoBatchqueryResponse response = impl.invoiceListPull("authusrB5ba5d2bd8f3745f3be1cf562467dcD88");

            System.out.println(JSON.toJSONString(response));

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

    }

}
