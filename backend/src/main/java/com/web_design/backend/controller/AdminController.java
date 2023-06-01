package com.web_design.backend.controller;

import com.web_design.backend.entity.ErrCode;
import com.web_design.backend.service.FileService;
import com.web_design.backend.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    UserInfoService userInfoService;

    @PostMapping("/user/set-password-by-email")
    public boolean updatePasswordByEmail(String email, String password) {
        return userInfoService.updatePasswordByEmail(email, password) == ErrCode.Success;
    }

    @PostMapping("/user/set-password-by-id")
    public boolean updatePasswordById(int id, String password) {
        return userInfoService.updatePasswordById(id, password) == ErrCode.Success;
    }

    @PostMapping("/user/set-username-by-email")
    public boolean updateUsernameByEmail(String email, String username) {
        return userInfoService.updateUsernameByEmail(email, username) == ErrCode.Success;
    }

    @PostMapping("/user/set-username-by-id")
    public boolean updateUsernameById(int id, String username) {
        return userInfoService.updateUsernameById(id, username) == ErrCode.Success;
    }

    @PostMapping("/user/set-coins-by-email")
    public boolean updateCoinsByEmail(String email, int coins) {
        return userInfoService.updateCoinsByEmail(email, coins) == ErrCode.Success;
    }

    @PostMapping("/user/set-coins-by-id")
    public boolean updateCoinsById(int id, int coins) {
        return userInfoService.updateCoinsById(id, coins) == ErrCode.Success;
    }

    @PostMapping("/user/set-hot-by-email")
    public boolean updateHotByEmail(String email, int hot) {
        return userInfoService.updateHotByEmail(email, hot) == ErrCode.Success;
    }

    @PostMapping("/user/set-hot-by-id")
    public boolean updateHotById(int id, int hot) {
        return userInfoService.updateHotById(id, hot) == ErrCode.Success;
    }


}
