package com.web_design.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.web_design.backend.entity.ErrCode;
import com.web_design.backend.entity.RestBean;
import com.web_design.backend.entity.user.UserInfo;
import com.web_design.backend.service.UserInfoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Resource
    UserInfoService service;

    @GetMapping("/me")
    public UserInfo getUserInfoByToken(HttpServletRequest request) {
        String token = request.getSession().getAttribute("token").toString();
        return service.getUserInfoByEmail(token);
    }

    @GetMapping("/id")
    public UserInfo getUserInfoById(@RequestBody JSONObject jsonParam) {
        int id = jsonParam.getIntValue("id");
        return service.getUserInfoById(id);
    }

    @GetMapping("/email")
    public UserInfo getUserInfoByEmail(@RequestBody JSONObject jsonParam) {
        String email = jsonParam.getString("email");
        return service.getUserInfoByEmail(email);
    }

    @PostMapping("/recharge")
    public RestBean recharge(HttpServletRequest request, @RequestBody JSONObject jsonParam) {
        String token = request.getSession().getAttribute("token").toString();
        int coins = jsonParam.getIntValue("coins");
        return service.updateCoinsByEmail(token, coins) == ErrCode.Success
                ? RestBean.success()
                : RestBean.failure(400);
    }

    @PostMapping("/update-username")
    public RestBean updateUsername(HttpServletRequest request, @RequestBody JSONObject jsonParam) {
        String token = request.getSession().getAttribute("token").toString();
        String username = jsonParam.getString("username");
        return service.updateUsernameByEmail(token, username) == ErrCode.Success
                ? RestBean.success()
                : RestBean.failure(400);
    }

}
