package com.web_design.backend.entity.user;

import lombok.Data;

// 用于返回给前端的用户信息
@Data
public class UserInfo {
    int id;
    String email;
    String username;
}
