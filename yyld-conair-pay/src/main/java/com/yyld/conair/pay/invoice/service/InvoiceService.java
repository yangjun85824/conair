package com.yyld.conair.pay.invoice.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.*;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
public interface InvoiceService{// extends IService<Ls> {

    String url();
    //根据auth_code 换取access_token
    AlipaySystemOauthTokenResponse getAccessTokenAndUserId(String auth_code);
    //根据access_token查询用户详情
    AlipayUserInfoShareResponse getAliUserInfo(String access_token);

    //创建阿里客户端
    void createClient(String type);

    //查询发票列表
    AlipayEbppInvoiceTokenBatchqueryResponse invoiceList(String invoice_token) throws AlipayApiException;
    //查询发票详情
    AlipayEbppInvoiceDetailOutputQueryResponse invoiceInfo(String access_token,String user_id, String invoice_code, String invoice_no) throws AlipayApiException;
    //查询发票原文件
    AlipayEbppInvoiceFileOutputQueryResponse invoiceInfoResourcesFile(String access_token,String user_id, String invoice_code, String invoice_no) throws AlipayApiException;

    //拉模式  发票列表
    AlipayEbppInvoiceTaxnoBatchqueryResponse invoiceListPull(String access_token) throws AlipayApiException;
    /*//拉模式  发票详情
    AlipayEbppInvoiceDetailOutputQueryResponse invoiceInfoPull(String access_token) throws AlipayApiException;
    //拉模式  发票原文件
    AlipayEbppInvoiceFileOutputQueryResponse invoiceInfoPullResourcesFile(String access_token) throws AlipayApiException;*/
}
