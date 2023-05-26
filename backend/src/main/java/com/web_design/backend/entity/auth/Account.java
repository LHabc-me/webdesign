package com.web_design.backend.entity.auth;

import lombok.Data;

@Data
public class Account {
    int id;
    String email;
    String username;
    String password;
}
