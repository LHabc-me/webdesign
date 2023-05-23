package com.web_design.backend.entity;

public enum ErrCode {
    Success(0),
    // 注册相关
    EmailAlreadyRegistered(1),      // 邮箱已注册
    EmailNotExist(2),               // 所输入邮箱不存在
    UsernameNotExist(3),            // 所输入用户名不存在
    PasswordError(4),               // 密码错误
    RegisterSqlFailed(5),           // sql执行失败

    // 验证码相关
    ValidateCodeExpired(11),        // 验证码过期
    ValidateCodeNotMatch(12),       // 验证码不匹配
    NotSendValidateCode(13);        // 尚未发送验证码
    ;

    private final int code;

    ErrCode(int i) {
        this.code = i;
    }

    public int getCode() {
        return code;
    }
}
