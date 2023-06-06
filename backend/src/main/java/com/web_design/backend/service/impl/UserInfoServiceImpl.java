package com.web_design.backend.service.impl;

import com.web_design.backend.entity.SpendingInfo;
import com.web_design.backend.entity.UserInfo;
import com.web_design.backend.mapper.UserInfoMapper;
import com.web_design.backend.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    UserInfoMapper mapper;

    @Override
    public UserInfo getUserInfoByEmail(String email) {
        return mapper.findUserInfoByEmail(email);
    }

    @Override
    public UserInfo getUserInfoById(int id) {
        return mapper.findUserInfoById(id);
    }

    @Override
    public SpendingInfo[] getALlSpendingInfo() {
        return mapper.getALlSpendingInfo();
    }

    @Override
    public boolean updatePasswordById(int id, String password) {
        return mapper.updatePasswordById(id, password) > 0;
    }

    @Override
    public boolean updatePasswordByEmail(String email, String password) {
        return mapper.resetPassword(email, password) > 0;
    }

    @Override
    public boolean updateUsernameById(int id, String username) {
        return mapper.updateUsernameById(id, username) > 0;
    }

    @Override
    public boolean updateUsernameByEmail(String email, String username) {
        return mapper.updateUsernameByEmail(email, username) > 0;
    }

    @Override
    public boolean updateCoinsByEmail(String email, int coins) {
        return mapper.updateUserCoinsByEmail(email, coins) > 0;
    }

    @Override
    public boolean updateCoinsById(int id, int coins) {
        return mapper.updateUserCoinsById(id, coins) > 0;
    }

    @Override
    public boolean updateHotByEmail(String email, int hot) {
        return mapper.setUserHotByEmail(email, hot) > 0;
    }

    @Override
    public boolean updateHotById(int id, int hot) {
        return mapper.setUserHotById(id, hot) > 0;
    }


}
