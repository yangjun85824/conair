package com.yyld.conair.eip.entity;

import lombok.Data;
import org.apache.camel.Consume;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
public class RestEntity {

    private String type;
    private String httpurl;
    private Map<String,Object> header;
    private String mothed;
    private Object params;

}