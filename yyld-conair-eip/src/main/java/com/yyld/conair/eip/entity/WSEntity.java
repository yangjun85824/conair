package com.yyld.conair.eip.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@Data
public class WSEntity {

    private String wsdl;
    private String mothed;
    private String localpart;
    private String namespaceuri;
    private String mothedResponse;
    private String type;
    private String prefix;
    private List<Params> iparams;
    private List<Params> oparams;
    //private String iparams;
    //private String oparams;
    private Object[] params;


}