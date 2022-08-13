package com.yyld.conair.druid.config;

/**
 * @Description: 功能描述
 */
public final class DataSourceContext {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal();

    public static void setDataSource(String value) {
        contextHolder.set(value);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }
}
