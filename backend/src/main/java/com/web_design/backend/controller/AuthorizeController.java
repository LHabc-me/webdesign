package com.web_design.backend.controller;

import com.web_design.backend.entity.RestBean;
import com.web_design.backend.service.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    private final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";

    @Resource
    AuthorizeService service;

    @PostMapping("/valid-email")
    public RestBean<String> validateEmail(@Pattern(regexp = EMAIL_PATTERN)
                                          @RequestParam("email") String email,
                                          HttpSession session) {
        if (service.sendValidateEmail(email, session.getId()))
            return RestBean.success("邮件已发送，请注意查收");
        else
            return RestBean.failure(400, "邮件发送失败，请联系管理员");
    }
}