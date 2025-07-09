package com.yyld.conair.friend.entity;

import lombok.Data;

import java.util.Map;

@Data
public class Test {

    private String name;

    private String value;

    private Abc abc;

    private Map<String,String> map;

}

@Data
class Abc{
    private String a;
    private String b;
    private String c;
}
