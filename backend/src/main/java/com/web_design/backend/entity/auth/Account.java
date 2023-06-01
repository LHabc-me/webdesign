package com.web_design.backend.entity.auth;

import lombok.Data;

@Data
public class Account {
    int id;
    String email;
    String username;
    String password;
    int coins = 0;
    int hot = 0;
    boolean roles;
}
