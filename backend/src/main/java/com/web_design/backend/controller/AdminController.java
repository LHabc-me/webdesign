package com.web_design.backend.controller;

import com.web_design.backend.entity.ErrCode;
import com.web_design.backend.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    UserInfoService service;

    @PostMapping("/set-password-by-email")
    public boolean updatePasswordByEmail(String email, String password) {
        return service.updatePasswordByEmail(email, password) == ErrCode.Success;
    }

    @PostMapping("/set-password-by-id")
    public boolean updatePasswordById(int id, String password) {
        return service.updatePasswordById(id, password) == ErrCode.Success;
    }

    @PostMapping("/set-username-by-email")
    public boolean updateUsernameByEmail(String email, String username) {
        return service.updateUsernameByEmail(email, username) == ErrCode.Success;
    }

    @PostMapping("/set-username-by-id")
    public boolean updateUsernameById(int id, String username) {
        return service.updateUsernameById(id, username) == ErrCode.Success;
    }

    @PostMapping("/set-coins-by-email")
    public boolean updateCoinsByEmail(String email, int coins) {
        return service.updateCoinsByEmail(email, coins) == ErrCode.Success;
    }

    @PostMapping("/set-coins-by-id")
    public boolean updateCoinsById(int id, int coins) {
        return service.updateCoinsById(id, coins) == ErrCode.Success;
    }

    @PostMapping("/set-hot-by-email")
    public boolean updateHotByEmail(String email, int hot) {
        return service.updateHotByEmail(email, hot) == ErrCode.Success;
    }

    @PostMapping("/set-hot-by-id")
    public boolean updateHotById(int id, int hot) {
        return service.updateHotById(id, hot) == ErrCode.Success;
    }
}
