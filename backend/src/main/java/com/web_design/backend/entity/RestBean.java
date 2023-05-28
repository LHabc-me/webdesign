package com.web_design.backend.entity;

import lombok.Data;

@Data
public class RestBean<T> {
    private int status;         // 状态码
    private boolean success;    // 是否成功
    private T token;            // 返回的数据
    private int err_code;       // 错误码

    private RestBean(int status, boolean success, T token) {
        this.status = status;
        this.success = success;
        this.token = token;
    }

    private RestBean(int status, boolean success, T token, int err_code) {
        this.status = status;
        this.success = success;
        this.token = token;
        this.err_code = err_code;
    }

    // 用了错误码枚举类的构造函数
    private RestBean(int status, boolean success, T token, ErrCode err_code) {
        this.status = status;
        this.success = success;
        this.token = token;
        this.err_code = err_code.getCode();
    }

    public static <T> RestBean<T> success() {
        return new RestBean<>(200, true, null);
    }

    public static <T> RestBean<T> success(T data) {
        return new RestBean<>(200, true, data);
    }

    public static <T> RestBean<T> failure(int status) {
        return new RestBean<>(status, false, null);
    }

    public static <T> RestBean<T> failure(int status, int err_code) {
        return new RestBean<>(status, false, null, err_code);
    }

    public static <T> RestBean<T> failure(int status, ErrCode err_code) {
        return new RestBean<>(status, false, null, err_code);
    }

    public static <T> RestBean<T> failure(int status, T data) {
        return new RestBean<>(status, false, data);
    }

    public static <T> RestBean<T> failure(int status, T data, int err_code) {
        return new RestBean<>(status, false, data, err_code);
    }

    public static <T> RestBean<T> failure(int status, T data, ErrCode err_code) {
        return new RestBean<>(status, false, data, err_code);
    }
}
