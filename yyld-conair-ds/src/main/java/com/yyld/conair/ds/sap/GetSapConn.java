package com.yyld.conair.ds.sap;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class GetSapConn {

    static String ABAP_AS_POOLED = "Q24";//"SAP-RFC";"PRD"; //所属异构系统 【找sap要】
    private static void createDataFile() {
        Properties properties = new Properties();
        //测试
        properties.setProperty(DestinationDataProvider.JCO_ASHOST, "10.110.9.132");//sap服务器地址
        properties.setProperty(DestinationDataProvider.JCO_SYSNR, "24");//系统编号，找SAP核对写00就可以了
        properties.setProperty(DestinationDataProvider.JCO_CLIENT, "669");//集团号，不知道就问你们的sap basis
        properties.setProperty(DestinationDataProvider.JCO_USER, "YANGYD");//帐号
        properties.setProperty(DestinationDataProvider.JCO_PASSWD, "Ylc@123456");//密码
        properties.setProperty(DestinationDataProvider.JCO_LANG, "zh");//语言
        //生产
        //properties.setProperty(DestinationDataProvider.JCO_ASHOST, "10.113,1.26");//sap服务器地址
        //properties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");//系统编号，找SAP核对写00就可以了
        //properties.setProperty(DestinationDataProvider.JCO_CLIENT, "669");//集团号，不知道就问你们的sap basis
        //properties.setProperty(DestinationDataProvider.JCO_USER, "CHENXI.YANG5");//帐号
        //properties.setProperty(DestinationDataProvider.JCO_PASSWD, "5186421xx@DD");//密码
        //properties.setProperty(DestinationDataProvider.JCO_LANG, "zh");//语言

//.set(SapManagerBuilderOption.ASHOST, "10.110.9.132") // AS host
//                .set(SapManagerBuilderOption.MSSERV, "669") // MS port [AS, MS is MSSERV, GW is JCO_GWSERV]
//                .set(SapManagerBuilderOption.SYSNR, "24") // system number
//                .set(SapManagerBuilderOption.GROUP, "Q24") // group
//                .set(SapManagerBuilderOption.LANG, "ZH") // language code
//                .set(SapManagerBuilderOption.CLIENT, "669") // client number
//                .set(SapManagerBuilderOption.USER, "YANGYD") // user
//                .set(SapManagerBuilderOption.PASSWD, "Ylc@123456") // password
        String name = ABAP_AS_POOLED;
        String suffix = "jcoDestination";
        File cfg = new File(name + "." + suffix);
        if (!cfg.exists()) {
            try {
                FileOutputStream fos = new FileOutputStream(cfg, false);
                properties.store(fos, "for tests only !");
                fos.close();
            } catch (Exception e) {
                throw new RuntimeException("Unable to create the destination file " + cfg.getName(), e);
            }
        }

    }

    public static JCoDestination getJcoConnection() throws JCoException {
        createDataFile();
        return JCoDestinationManager.getDestination(ABAP_AS_POOLED);
    }

}