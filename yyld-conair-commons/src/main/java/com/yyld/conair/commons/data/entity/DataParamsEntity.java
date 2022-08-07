package com.yyld.conair.commons.data.entity;

import java.util.List;
import java.util.Map;

/**
 * @ClassName DataParamsEntity
 * @Description 参数实体类
 * @Author yangj
 * @Date 2022/5/27 17:19
 * @Vresion 1.0
 **/
public class DataParamsEntity<T> {

    private List<T> data;

    private Map<String , Object> paramMap;

    private String interType;

}
