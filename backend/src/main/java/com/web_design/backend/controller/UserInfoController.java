package com.web_design.backend.controller;

import com.alibaba.fastjson.JSON;
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
    public JSONObject getUserInfoByToken(HttpServletRequest request) {
        String token = request.getSession().getAttribute("token").toString();
        UserInfo userInfo = service.getUserInfoByEmail(token);
        return JSON.parseObject(JSON.toJSONString(userInfo));
    }

    @GetMapping("/id")
    public JSONObject getUserInfoById(@RequestBody JSONObject jsonParam) {
        int id = jsonParam.getIntValue("id");
        UserInfo userInfo = service.getUserInfoById(id);
        return JSON.parseObject(JSON.toJSONString(userInfo));
    }

    @GetMapping("/email")
    public JSONObject getUserInfoByEmail(@RequestBody JSONObject jsonParam) {
        String email = jsonParam.getString("email");
        UserInfo userInfo = service.getUserInfoByEmail(email);
        return JSON.parseObject(JSON.toJSONString(userInfo));
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
