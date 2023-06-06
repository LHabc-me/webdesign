package com.web_design.backend.service;

import com.web_design.backend.entity.SpendingInfo;
import com.web_design.backend.entity.UserInfo;

// 获取用户信息相关
public interface UserInfoService {
    UserInfo getUserInfoByEmail(String email);

    UserInfo getUserInfoById(int id);

    SpendingInfo[] getALlSpendingInfo();

    boolean updatePasswordById(int id, String password);

    boolean updatePasswordByEmail(String email, String password);

    boolean updateUsernameById(int id, String username);

    boolean updateUsernameByEmail(String email, String username);

    boolean updateCoinsByEmail(String email, int coins);

    boolean updateCoinsById(int id, int coins);

    boolean updateHotByEmail(String email, int hot);

    boolean updateHotById(int id, int hot);
}
