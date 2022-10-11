package com.yyld.conair.druid.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName DataSourceInfoEntity
 * @Description //TODO
 * @Author yangj
 * @Date 2022/9/7 18:32
 * @Vresion 1.0
 **/
@Data
public class DataSourceInfoEntity implements Serializable {

    private String type;
    private String dsName;
    private String defaultDs;
    private String url;
    private String username;
    private String password;
    private String driver;
    private Integer initialSize;
    private Integer minIdle;
    private Integer maxActive;
    private Long maxWait;
    private Long timeBetweenEvictionRunsMillis;
    private Long minEvictableIdleTimeMillis;
    private String validationQuery;
    private Boolean testWhileIdle;
    private Boolean testOnBorrow;
    private Boolean testOnReturn;
    private Boolean poolPreparedStatements;
    private Integer maxPoolPreparedStatementPerConnectionSize;

    private String connectionProperties;
    private String filters;

}
