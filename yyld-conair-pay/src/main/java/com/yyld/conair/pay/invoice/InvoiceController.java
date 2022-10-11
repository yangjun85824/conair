package com.yyld.conair.pay.invoice;

/**
 * @ClassName InvoiceController
 * @Description //TODO
 * @Author yangj
 * @Date 2022/8/15 11:21
 * @Vresion 1.0
 **/

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayEbppInvoiceDetailOutputQueryResponse;
import com.alipay.api.response.AlipayEbppInvoiceFileOutputQueryResponse;
import com.alipay.api.response.AlipayEbppInvoiceTaxnoBatchqueryResponse;
import com.alipay.api.response.AlipayEbppInvoiceTokenBatchqueryResponse;
import com.yyld.conair.pay.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    //阿里发票调用后的回调地址 请求此链接后回调：https://render.alipay.com/p/s/i?appId=20000920&startMultApp=YES&url=%2Fwww%2FinvoiceSelect.htm%3Fscene%3DINVOICE_EXPENSE%26einvMerchantId%3D91430111MA4QWYK02B%26serverRedirectUrl%3Dhttp%253A%252F%252Fpay.yiyld.com%252Fpay%252Finvoice%252Fback
    @GetMapping("/back")
    @ResponseBody
    public AlipayEbppInvoiceTokenBatchqueryResponse callback(HttpServletRequest request) throws AlipayApiException {

        long starttime = System.currentTimeMillis();

        System.out.println("回调成功 ======= "+request.getParameter("INVOICE_EXPENSE"));

        //创建阿里客户端
        invoiceService.createClient("");

        //根据授权令牌查询发票列表 invoice_token
        String invoice_token = request.getParameter("INVOICE_EXPENSE");
        AlipayEbppInvoiceTokenBatchqueryResponse invoiceResponse = invoiceService.invoiceList(invoice_token);

        System.out.println(System.currentTimeMillis() - starttime);
        return invoiceResponse;

    }

    //根据用户id 发票编码 发票编号 查询发票详情接口
    @GetMapping("/invoiceInfo")
    @ResponseBody
    public AlipayEbppInvoiceDetailOutputQueryResponse invoiceInfo(HttpServletRequest request) throws AlipayApiException {

        long starttime = System.currentTimeMillis();

        //创建阿里客户端
        invoiceService.createClient("");

        //根据列表选取获取发票详情
        String user_id = request.getParameter("user_id");
        String invoice_code = request.getParameter("invoice_code");
        String invoice_no = request.getParameter("invoice_no");

        AlipayEbppInvoiceDetailOutputQueryResponse invoiceInfo = null;//invoiceService.invoiceInfo(user_id,invoice_code,invoice_no);

        System.out.println(System.currentTimeMillis() - starttime);
        return invoiceInfo;

    }

    //根据用户id 发票编码 发票编号 查询发票原文件接口
    @GetMapping("/invoiceInfoResourcesFile")
    @ResponseBody
    public AlipayEbppInvoiceFileOutputQueryResponse invoiceInfoResourcesFile(HttpServletRequest request) throws AlipayApiException {

        long starttime = System.currentTimeMillis();

        //创建阿里客户端
        invoiceService.createClient("");

        String user_id = request.getParameter("user_id");
        String invoice_code = request.getParameter("invoice_code");
        String invoice_no = request.getParameter("invoice_no");

        //获取发票原文件接口
        AlipayEbppInvoiceFileOutputQueryResponse invoiceInfoResourcesFile = invoiceService.invoiceInfoResourcesFile(user_id,invoice_code,invoice_no, invoice_no);

        System.out.println(System.currentTimeMillis() - starttime);
        return invoiceInfoResourcesFile;

    }

    @GetMapping("/url")
    public String url() {

        return invoiceService.url();

    }

    //拉模式 发票列表
    @GetMapping("/invoiceList")
//    @ResponseBody
    public Object invoiceList(String access_token, ModelAndView model) throws AlipayApiException {

        AlipayEbppInvoiceTaxnoBatchqueryResponse invoiceList = invoiceService.invoiceListPull(access_token);

        model.addObject("access_token",access_token);
        model.addObject("list",invoiceList);
        model.setViewName("invoiceList");

        return model;
    }

    //拉模式 发票详情
    @GetMapping("/invoiceInfoPull")
    @ResponseBody
    public Object invoiceInfoPull(String access_token,String user_id,String invoice_code , String invoice_no) throws AlipayApiException {

        AlipayEbppInvoiceDetailOutputQueryResponse invoiceInfo = invoiceService.invoiceInfo(access_token,user_id,invoice_code,invoice_no);

        return invoiceInfo;
    }

    //拉模式 发票原文件
    @GetMapping("/invoiceInfoPullResourcesFile")
    @ResponseBody
    public Object invoiceInfoPullResourcesFile(String access_token,String user_id,String invoice_code , String invoice_no) throws AlipayApiException {

        AlipayEbppInvoiceFileOutputQueryResponse invoiceInfo = invoiceService.invoiceInfoResourcesFile(access_token,user_id,invoice_code,invoice_no);

        return invoiceInfo;
    }

}
