package com.yyld.conair.eip.ws.abs;

import com.predic8.schema.*;
import com.predic8.wsdl.*;
import groovy.xml.QName;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbsWsdlPredic8Analysis {

    private Definitions defs;

    private String targetNamespaceUri;

    public AbsWsdlPredic8Analysis() {}

    public AbsWsdlPredic8Analysis(String wsdl, TYPE e) throws Exception {

        switch (e) {
            case HTTP:
                this.defs = getDefinitions(wsdl);
                break;
            case FILEURL:
                this.defs = getDefinitionsByWsdl(wsdl);
                break;
        }
    }

    public abstract List<Map> getAllParams();

    public List<Map> hook(){
        return getAllParams();
    }

    //wsdl全文定义
    public final Definitions getDefinitions(String wsdl) {

        WSDLParser parser = new WSDLParser();

        defs = parser.parse(wsdl);

        return defs;

    }

    //wsdl通过文件路径 全文定义
    public final Definitions getDefinitionsByWsdl(String wsdlFileUrl) throws FileNotFoundException {

        WSDLParser parser = new WSDLParser();

        defs = parser.parse(new FileInputStream(new File(wsdlFileUrl)));

        return defs;

    }

    //获取wsdl所有方法
    public List getMotheds() {

        List<Binding> bindings = this.defs.getBindings();

        List<Map> mapList = new ArrayList<>();
        for (Binding binding : bindings) {

            QName qName = binding.getType();

            List<BindingOperation> bindingOperations = binding.getOperations();
            for (BindingOperation operation : bindingOperations) {

                BindingOutput output = operation.getOutput();

                String mothedName = operation.getName();

                Map map = new HashMap();

                map.put("namespaceuri", getTargetNamespaceUri());
                map.put("localpart", qName.getLocalPart());
                map.put("prefix", qName.getPrefix());

                map.put("mothed", mothedName);
                map.put("type", binding.getTypePN());

                map.put("mothedResponse", output.getName());

                mapList.add(map);

            }

        }

        return mapList;
    }

    //通过指定的命名空间获取schema
    public Schema getSchema(String ns) {

        Schema schema = this.defs.getSchema(ns);

        return schema;

    }

    //获取PortType
    public PortType getPortType(String localPort) {

        if (defs == null) {
            throw new NullPointerException("Definitions对象为空");
        }

        PortType portType = this.defs.getPortType(localPort);

        return portType;

    }

    //获取Message
    public Map<String, Message> getMessage(PortType portType, String mothedname) {

        Operation operation = portType.getOperation(mothedname);
        Input input = operation.getInput();
        Output output = operation.getOutput();

        Message iMessage = input.getMessage();
        Message oMessage = output.getMessage();

        Map<String, Message> map = new HashMap();

        map.put("iMessage", iMessage);
        map.put("oMessage", oMessage);

        return map;

    }

    /*
        对ComplexType对象进行处理 如果普通结构，可通过schema.getComplexType(mothedName)直接获得对象（
            <xs:complexType name="hiWebService">
                <xs:sequence>
                    <xs:element minOccurs="0" name="hi" type="xs:string"/>
                    <xs:element minOccurs="0" name="b" type="xs:int"/>
                </xs:sequence>
            </xs:complexType>
        ）,
        如果对element有定义的情况下，上面的方式会异常，此处则通过第二种方式进行获取对象（
            <xsd:element name="queryAssBalance">
                <xsd:complexType>
                    <xsd:sequence>
                        <xsd:element name="ASSBALANCEINVO" type="AssBalanceInVO" minOccurs="0"/>
                    </xsd:sequence>
                </xsd:complexType>
            </xsd:element>
            <xsd:complexType name="AssBalanceInVO">
                <xsd:sequence>
                    <xsd:element name="CUSTTYPE" type="xsd:string" minOccurs="0"/>
                    <xsd:element name="SUBJCODES" type="SubjcodeVO" minOccurs="0" maxOccurs="unbounded"/>
                    <xsd:element name="COMPANY" type="xsd:string" minOccurs="0"/>
                    <xsd:element name="PSNCODE" type="xsd:string" minOccurs="0"/>
                    <xsd:element name="CUSTCODE" type="xsd:string" minOccurs="0"/>
                    <xsd:element name="DEF1" type="xsd:string" minOccurs="0"/>
                    <xsd:element name="DEF2" type="xsd:string" minOccurs="0"/>
                    <xsd:element name="DEF3" type="xsd:string" minOccurs="0"/>
                    <xsd:element name="DEF4" type="xsd:string" minOccurs="0"/>
                    <xsd:element name="DEF5" type="xsd:string" minOccurs="0"/>
                </xsd:sequence>
            </xsd:complexType>
        ）
     */
    public ComplexType complexTypeHandler(Schema schema, String mothedName) {

        ComplexType complexType = null;
        try {

            complexType = schema.getComplexType(mothedName);

        } catch (Exception e) {

            Element element = schema.getElement(mothedName);
            ComplexType cType = (ComplexType) element.getEmbeddedType();
            Sequence sequence = (Sequence) cType.getModel();

            List<SchemaComponent> schemaComponents = sequence.getParticles();

            Element el = (Element) schemaComponents.get(0);     //此处只取第一个complexType
            String localPart = el.getType().getLocalPart();

            if (!"".equals(localPart)) {
                complexType = schema.getComplexType(localPart);
            }
        }

        return complexType;

    }

    //获取 方法参数名及类型
    public List<Map> getParamesByComplexType(ComplexType complexType) {

        if (complexType == null) {
            throw new NullPointerException("ComplexType对象为空 ,对应WSDL文档中的ComplexType节点 ");
        }

        List<Map> returnResultList = new ArrayList<>();

        if (complexType.getModel() instanceof ModelGroup) {

            for (Element element : ((ModelGroup) complexType.getModel()).getElements()) {

                Map temp = new HashMap();

                String paramtype = element.getType().getLocalPart();
                String paramname = element.getName();

                temp.put("paramname", paramname);
                temp.put("paramtype", paramtype);
                temp.put("value", "");

                Schema schema = complexType.getSchema();
                ComplexType ctype = null;
                try {
                    ctype = schema.getComplexType(paramtype);
                } catch (Exception e) {
                    ctype = null;
                    System.out.println("没有子节点");
                }
                if (ctype != null) {
                    System.out.println(ctype.getPrefix());
                    temp.put(paramtype + "_childs", getParamesByComplexType(ctype));

                }

                returnResultList.add(temp);
            }

        }
        return returnResultList;
    }

    public Definitions getDefs() {
        return defs;
    }

    //获取命名空间
    public String getTargetNamespaceUri() {

        if (defs == null) {
            throw new NullPointerException("Definitions对象为空");
        }

        this.targetNamespaceUri = defs.getTargetNamespace();

        return this.targetNamespaceUri;

    }

    public List<Element> getElementsByMessage(Message message) {

        List<Part> partList = message.getParts();

        List<Element> elements = new ArrayList<>();
        for (Part part : partList) {

            Element element = part.getElement();

            elements.add(element);

        }

        return elements;
    }

    //根据 Message 下的 Element定义获取 element下的参数
    public List<Map> getParamsByElement(Element element) {

        List<Map> resultList = new ArrayList<>();

        ComplexType complexType = (ComplexType) element.getEmbeddedType();
        if (complexType == null) {
            Schema schema = getSchema(element.getType().getNamespaceURI());
            try {
                complexType = schema.getComplexType(element.getType().getLocalPart());
            } catch (Exception e) {
                return null;
            }
        }

        if (complexType == null) {
            return null;
        }
        Sequence sequence = (Sequence) complexType.getModel();

        List<SchemaComponent> schemaComponents = sequence.getParticles();

        for (SchemaComponent schemaComponent : schemaComponents) {

            Element el = (Element) schemaComponent;
            String name = el.getName();
            String type = el.getType() == null ? null : el.getType().getLocalPart();

            Map map = new HashMap();

            map.put("paramname", name);
            map.put("paramtype", type);
            map.put("value", "");

            List<Map> temp = getParamsByElement(el);    //递归 获取子集
            map.put("childer", temp);

            resultList.add(map);
        }

        return resultList;
    }
}
