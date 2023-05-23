package com.web_design.backend.entity;

import lombok.Data;

@Data
public class RestBean<T> {
    private int status;
    private boolean success;
    private T message;
    private int err_code;

    private RestBean(int status, boolean success, T message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }

    private RestBean(int status, boolean success, T message, int err_code) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.err_code = err_code;
    }

    // 用了错误码枚举类的构造函数
    private RestBean(int status, boolean success, T message, ErrCode err_code) {
        this.status = status;
        this.success = success;
        this.message = message;
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
