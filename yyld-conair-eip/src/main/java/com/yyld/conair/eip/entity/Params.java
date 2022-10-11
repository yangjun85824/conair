package com.yyld.conair.eip.entity;

import lombok.Data;

import java.util.List;

@Data
public class Params{

    private String paramtype;
    private List<Params> childer;
    private String paramname;
    private Object value;
}
