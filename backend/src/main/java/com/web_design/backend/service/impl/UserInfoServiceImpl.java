package com.web_design.backend.service.impl;

import com.web_design.backend.entity.ErrCode;
import com.web_design.backend.entity.user.UserInfo;
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
    public ErrCode updatePasswordById(int id, String password) {
        return mapper.updatePasswordById(id, password) > 0 ? ErrCode.Success : ErrCode.SqlFail;
    }

    @Override
    public ErrCode updatePasswordByEmail(String email, String password) {
        return mapper.resetPassword(email, password) > 0 ? ErrCode.Success : ErrCode.SqlFail;
    }

    @Override
    public ErrCode updateUsernameById(int id, String username) {
        return mapper.updateUsernameById(id, username) > 0 ? ErrCode.Success : ErrCode.SqlFail;
    }

    @Override
    public ErrCode updateUsernameByEmail(String email, String username) {
        return mapper.updateUsernameByEmail(email, username) > 0 ? ErrCode.Success : ErrCode.SqlFail;
    }

    @Override
    public ErrCode updateCoinsByEmail(String email, int coins) {
        return mapper.updateUserCoinsByEmail(email, coins) > 0 ? ErrCode.Success : ErrCode.SqlFail;
    }

    @Override
    public ErrCode updateCoinsById(int id, int coins) {
        return mapper.updateUserCoinsById(id, coins) > 0 ? ErrCode.Success : ErrCode.SqlFail;
    }

    @Override
    public ErrCode updateHotByEmail(String email, int hot) {
        return mapper.setUserHotByEmail(email, hot) > 0 ? ErrCode.Success : ErrCode.SqlFail;
    }

    @Override
    public ErrCode updateHotById(int id, int hot) {
        return mapper.setUserHotById(id, hot) > 0 ? ErrCode.Success : ErrCode.SqlFail;
    }


}
