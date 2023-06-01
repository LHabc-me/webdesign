package com.web_design.backend.service;

import com.web_design.backend.entity.ErrCode;
import com.web_design.backend.entity.user.UserInfo;

// 获取用户信息相关
public interface UserInfoService {
    UserInfo getUserInfoByEmail(String email);

    UserInfo getUserInfoById(int id);

    ErrCode updatePasswordById(int id, String password);

    ErrCode updatePasswordByEmail(String email, String password);

    ErrCode updateUsernameById(int id, String username);

    ErrCode updateUsernameByEmail(String email, String username);

    ErrCode updateCoinsByEmail(String email, int coins);

    ErrCode updateCoinsById(int id, int coins);

    ErrCode updateHotByEmail(String email, int hot);

    ErrCode updateHotById(int id, int hot);
}
