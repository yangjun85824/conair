package com.yyld.conair.commons.data.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName APIResult
 * @Description //TODO
 * @Author yangj
 * @Date 2022/8/24 17:36
 * @Vresion 1.0
 **/
@Data
public class APIResult<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;

    /**
     * 成功
     */
    public static APIResult<Void> success() {
        APIResult<Void> result = new APIResult<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        return result;
    }

    /**
     * 成功，有返回数据
     */
    public static <V> APIResult<V> success(V data) {
        APIResult<V> result = new APIResult<>();
        result.code = ResultCode.SUCCESS.getCode();
        result.message = ResultCode.SUCCESS.getMessage();
        result.data = data;
        return result;
    }

    /**
     * 失败
     */
    public static APIResult<Void> failure() {
        APIResult<Void> result = new APIResult<>();
        result.setCode(ResultCode.FAILURE.getCode());
        result.setMessage(ResultCode.FAILURE.getMessage());
        return result;
    }

    /**
     * 失败，自定义失败信息
     */
    public static APIResult<Void> failure(String message) {
        APIResult<Void> result = new APIResult<>();
        result.setCode(ResultCode.FAILURE.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 失败，使用已定义枚举
     */
    public static APIResult<Void> failure(ResultCode resultCode) {
        APIResult<Void> result = new APIResult<>();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        return result;
    }
}

