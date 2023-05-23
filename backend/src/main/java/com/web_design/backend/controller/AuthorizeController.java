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

    @PostMapping("/register/verification-code")
    public RestBean<String> validateEmail(@RequestBody JSONObject emailJSON,
                                          HttpSession session) {
        @Pattern(regexp = EMAIL_REGEX)
        String email = emailJSON.get("email").toString();
//        System.out.println(email);
        ErrCode errCode = service.sendValidateEmail(email, session.getId());
        if (errCode == ErrCode.Success)
            return RestBean.success();
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
        String verificationCode = userForm.get("verificationCode").toString();

        ErrCode errCode = service.validateAndRegister(email, username, password, verificationCode, session.getId());
        if (errCode == ErrCode.Success)
            return RestBean.success();
        else
            return RestBean.failure(400, errCode);
    }
}
