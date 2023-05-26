package com.web_design.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.web_design.backend.entity.ErrCode;
import com.web_design.backend.entity.RestBean;
import com.web_design.backend.service.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/")
public class AuthorizeController {

    private final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";
    private final String USERNAME_REGEX = "^[a-zA-Z0-9._%+-]{6,}$";
    @Resource
    AuthorizeService service;

    @PostMapping("/verification-code")
    public RestBean<String> validateRegisterEmail(@RequestBody JSONObject emailJSON,
                                                  HttpSession session) {
        @Pattern(regexp = EMAIL_REGEX)
        String email = emailJSON.get("email").toString();
        ErrCode errCode = service.sendValidateEmail(email, session.getId());
        if (errCode == ErrCode.Success)
            return RestBean.success("verification-code send success");
        else
            return RestBean.failure(400, errCode);
    }

    @PostMapping("/register")
    public RestBean<String> registerUser(@RequestBody JSONObject userForm,
                                         HttpSession session) {
        @Pattern(regexp = USERNAME_REGEX) @Length(min = 2, max = 8)
        String username = userForm.get("username").toString();
        @Length(min = 6, max = 16)
        String password = userForm.get("password").toString();
        @Pattern(regexp = EMAIL_REGEX)
        String email = userForm.get("email").toString();
        @Length(min = 6, max = 6)
        String verificationCode = userForm.get("verification-code").toString();

        ErrCode errCode = service.validateAndRegister(email, username, password, verificationCode, session.getId());
        if (errCode == ErrCode.Success)
            return RestBean.success("success");
        else
            return RestBean.failure(400, errCode);
    }

    /**
     * 1. 发验证邮件
     * 2. 验证验证码是否正确，正确就在Session中存一个标记
     * 3. 用户发起重置密码请求，验证Session中是否有标记，如果有就重置密码
     */
    @PostMapping("/reset-password")
    public RestBean<String> resetPassword(@RequestBody JSONObject resetJSON,
                                          HttpSession session) {
//        @Pattern(regexp = USERNAME_REGEX) @Length(min = 2, max = 8)
//        String username = resetJSON.get("username").toString();
        @Length(min = 6, max = 16)
        String password = resetJSON.get("password").toString();
        @Pattern(regexp = EMAIL_REGEX)
        String email = resetJSON.get("email").toString();
        @Length(min = 6, max = 6)
        String verificationCode = resetJSON.get("verification-code").toString();

        ErrCode errCode = service.validateAndResetPassword(email, password, verificationCode, session.getId());
        if (errCode == ErrCode.Success) {

            return RestBean.success("reset-password success");
        } else
            return RestBean.failure(400, errCode);
    }
}
