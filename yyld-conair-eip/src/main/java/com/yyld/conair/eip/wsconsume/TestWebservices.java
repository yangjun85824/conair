package com.yyld.conair.eip.wsconsume;

import com.alibaba.fastjson.JSON;
import com.yyld.conair.commons.data.result.APIResult;
import com.yyld.conair.eip.entity.Params;
import com.yyld.conair.eip.entity.WSEntity;
import com.yyld.conair.eip.ws.abs.AbsWsdlPredic8Analysis;
import com.yyld.conair.eip.ws.abs.TYPE;
import com.yyld.conair.eip.ws.abs.ext.DefaultWsdlAnalysisUtil;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("testws")
public class TestWebservices {

    public static void main(String[] args) {

        String wsdlUrl = "http://localhost:8001/ws/TestWebservices?wsdl";
        String namespaceUrl = "http://www.endpoint.webservice.example.com";
        String localPart = "hiWebService";

        Object[] param = {"1111",10};

        Map map = new HashMap();
        map.put("wsdlUrl",wsdlUrl);
        map.put("namespaceUrl",namespaceUrl);
        map.put("localPart",localPart);
        map.put("parames", param);

        TestWebservices testWebservices = new TestWebservices();
//        testWebservices(wsdlUrl, namespaceUrl, localPart, param);
        testWebservices.testParamMap(map);

    }

    public APIResult<List<Map<String, String>>>  analysis(String wsdl) throws Exception {

        AbsWsdlPredic8Analysis wsdlPredic8Analysis = new DefaultWsdlAnalysisUtil(wsdl, TYPE.HTTP);

        System.out.println(wsdlPredic8Analysis.getSchema(wsdlPredic8Analysis.getTargetNamespaceUri()));

        List resultList = wsdlPredic8Analysis.getAllParams();

        return APIResult.success(resultList);
    }

    public String testss(String hi,String hih) {

        System.out.println(hi+"====="+hih);

        return "slakdjfalksdjf===="+hi+"====="+hih;
    }


    public String testParamMap(Map map) {

        String wsdlUrl = map.get("wsdlUrl").toString();
        String namespaceUrl = map.get("namespaceUrl").toString();
        String localPart = map.get("localPart").toString();
        Object[] param = (Object[]) map.get("parames");

        return testWebservices(wsdlUrl, namespaceUrl, localPart, param);
    }

    public String requestWsServicesParams(WSEntity entity) {

        String wsdlUrl = entity.getWsdl();// map.get("wsdlUrl").toString();
        String namespaceUrl = entity.getNamespaceuri();//.get("namespaceUrl").toString();
        String method = entity.getMothed();// map.get("localPart").toString();

        Object[] objs = entity.getParams();
        List<Params> iParams = entity.getIparams();

        if (objs == null || objs.length ==0){



        }

        Object[] param = {"1111",10};


        return testWebservices(wsdlUrl, namespaceUrl, method, param);
    }

    public String testWebservices(String wsdlUrl, String namespaceUrl, String localPart, Object... params) {

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdlUrl);
        // url为调用webService的wsdl地址
        QName name = new QName(namespaceUrl, localPart);
        // namespace是命名空间，methodName是方法名

        Object[] objects;
        try {
            objects = client.invoke(name, params);
            System.out.println(objects[0].toString());
            return objects[0].toString()+"   ==============    从这里返回是就对了";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "结束 返回";
    }
}
