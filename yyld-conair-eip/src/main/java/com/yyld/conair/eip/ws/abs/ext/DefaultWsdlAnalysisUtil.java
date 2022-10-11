package com.yyld.conair.eip.ws.abs.ext;

import com.alibaba.fastjson.JSONObject;
import com.predic8.schema.Element;
import com.predic8.wsdl.Message;
import com.predic8.wsdl.PortType;
import com.yyld.conair.eip.ws.abs.AbsWsdlPredic8Analysis;
import com.yyld.conair.eip.ws.abs.TYPE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultWsdlAnalysisUtil extends AbsWsdlPredic8Analysis {

    public DefaultWsdlAnalysisUtil(String wsdl, TYPE http) throws Exception {
        super(wsdl,http);
    }

    public static void main(String[] args) throws Exception {


        String wsdl = "http://localhost:8001/ws/TestWebservices?wsdl";

        //wsdl = "C:\\Users\\yangj\\Desktop\\wsdl\\writeoffVoucher.wsdl";
//        wsdl = "C:\\Users\\yangj\\Desktop\\wsdl\\AssBalance.wsdl";
//        wsdl = "C:\\Users\\yangj\\Desktop\\wsdl\\NcVoucher.wsdl";
//        wsdl = "C:\\Users\\yangj\\Desktop\\wsdl\\sy_fssc_rptinfo_return.wsdl";
//        wsdl = "C:\\Users\\yangj\\Desktop\\wsdl\\writeoffVoucher.wsdl";
//        wsdl = "C:\\Users\\yangj\\Desktop\\wsdl\\ems.xml";

//        AbstractWsdlAnalysis abstractWsdlAnalysis = new DefaultWsdlAnalysisUtil();

//        abstractWsdlAnalysis.getDefinitions("http://localhost/ws/TestWebservices?wsdl");

//        abstractWsdlAnalysis.getDefinitionsByWsdl("C:\\Users\\yangj\\Desktop\\wsdl\\writeoffVoucher.wsdl");
//        abstractWsdlAnalysis.getDefinitionsByWsdl("C:\\Users\\yangj\\Desktop\\wsdl\\AssBalance.wsdl");
//        abstractWsdlAnalysis.getDefinitionsByWsdl("C:\\Users\\yangj\\Desktop\\wsdl\\NcVoucher.wsdl");
//        abstractWsdlAnalysis.getDefinitionsByWsdl("C:\\Users\\yangj\\Desktop\\wsdl\\sy_fssc_rptinfo_return.wsdl");
//        abstractWsdlAnalysis.getDefinitionsByWsdl("C:\\Users\\yangj\\Desktop\\wsdl\\writeoffVoucher.wsdl");

//        List<Map> mothedsList = abstractWsdlAnalysis.getAllMotheds();

//        abstractWsdlAnalysis.getAllPortTypes();

        /*for (Map map:mothedsList) {

            map.put("params",abstractWsdlAnalysis.getParamsByMothedName(map.get("mothed").toString()));

        }

        String wsdl = "C:\\Users\\yangj\\Desktop\\wsdl\\ems.xml";
        Object obj = abstractWsdlAnalysis.getAllParams(wsdl,TYPE.HTTP);*/

        System.out.println("====================================================================");

        AbsWsdlPredic8Analysis wsdlPredic8Analysis = new DefaultWsdlAnalysisUtil(wsdl,TYPE.HTTP);

        System.out.println(wsdlPredic8Analysis.getSchema(wsdlPredic8Analysis.getTargetNamespaceUri()));

        List result = wsdlPredic8Analysis.getAllParams();

        String json = JSONObject.toJSONString(result);

        System.out.println("json ======= "+json);
    }

    @Override
    public List<Map> getAllParams(){

        List<Map> list = getMotheds();

        for (Map map:list) {

            String localpart = (String) map.get("localpart");
            String mothedname = (String) map.get("mothed");

            PortType portType = getPortType(localpart);

            Map<String, Message> messageMap = getMessage(portType,mothedname);

            List result = new ArrayList();
            Message message = messageMap.get("iMessage");

            getElement(result, message);

            List oResult = new ArrayList();
            message = messageMap.get("oMessage");

            getElement(oResult, message);

            map.put("iparams",result);
            map.put("oparams",oResult);

        }

        return list;
    }

    public void getElement(List result, Message message){

        List<Element> elements = getElementsByMessage(message);

        for (Element element:elements) {

            List<Map> paramlist = getParamsByElement(element);

            result.add(paramlist);

        }
    }

}
