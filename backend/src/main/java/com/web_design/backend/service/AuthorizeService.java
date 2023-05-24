package com.web_design.backend.service;

import com.web_design.backend.entity.ErrCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthorizeService extends UserDetailsService {

    ErrCode sendValidateEmail(String email, String sessionId);

    ErrCode validateAndRegister(String email, String username, String password, String validateCode, String sessionId);

    ErrCode validateAndResetPassword(String email, String username, String password, String validateCode, String sessionId);

//    ErrCode validateAndLogin(String email, String password);
}
