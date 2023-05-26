package com.web_design.backend.controller;

import com.web_design.backend.entity.RestBean;
import com.web_design.backend.entity.user.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/me")
    public RestBean<UserInfo> getUserInfo() {
        return RestBean.success();
    }
}
