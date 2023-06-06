package com.web_design.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.web_design.backend.entity.SpendingInfo;
import com.web_design.backend.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    UserInfoService userInfoService;

    @PostMapping("/cost/info")
    public SpendingInfo[] getSpendingInfo() {
        return userInfoService.getALlSpendingInfo();
    }

    @PostMapping("/user/set-password-by-email")
    public boolean updatePasswordByEmail(String email, String password) {
        return userInfoService.updatePasswordByEmail(email, password);
    }

    @PostMapping("/update/password")
    public boolean updatePasswordById(@RequestBody JSONObject info) {
        int id = info.getIntValue("id");
        String password = info.getString("password");
        return userInfoService.updatePasswordById(id, password);
    }

    @PostMapping("/user/set-username-by-email")
    public boolean updateUsernameByEmail(String email, String username) {
        return userInfoService.updateUsernameByEmail(email, username);
    }

    @PostMapping("/user/username")
    public boolean updateUsernameById(@RequestBody JSONObject info) {
        int id = info.getIntValue("id");
        String username = info.getString("username");
        return userInfoService.updateUsernameById(id, username);
    }

    @PostMapping("/user/set-coins-by-email")
    public boolean updateCoinsByEmail(String email, int coins) {
        return userInfoService.updateCoinsByEmail(email, coins);
    }

    @PostMapping("/update/coins")
    public boolean updateCoinsById(@RequestBody JSONObject info) {
        int id = info.getIntValue("id");
        int coins = info.getIntValue("coins");
        return userInfoService.updateCoinsById(id, coins);
    }

    @PostMapping("/user/set-hot-by-email")
    public boolean updateHotByEmail(String email, int hot) {
        return userInfoService.updateHotByEmail(email, hot);
    }

    @PostMapping("/update/hot")
    public boolean updateHotById(@RequestBody JSONObject info) {
        int id = info.getIntValue("id");
        int hot = info.getIntValue("hot");
        return userInfoService.updateHotById(id, hot);
    }


}
