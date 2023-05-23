package com.web_design.backend.entity;

public enum ErrCode {
    Success(0),
    // 注册相关
    EmailAlreadyExist(1),
    UsernameAlreadyExist(2),
    EmailNotExist(3),
    UsernameNotExist(4),
    PasswordError(5),
    RegisterSqlFailed(6),        // sql执行失败
    // 验证码相关
    ValidateCodeExpired(11),     // 验证码过期
    ValidateCodeNotMatch(12),    // 验证码不匹配
    NotSendValidateCode(13);     // 尚未发送验证码
    ;

    private final int code;

    ErrCode(int i) {
        this.code = i;
    }

    public int getCode() {
        return code;
    }
}
