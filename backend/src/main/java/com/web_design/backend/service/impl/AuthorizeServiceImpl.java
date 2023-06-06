package com.web_design.backend.service.impl;

import com.web_design.backend.entity.Account;
import com.web_design.backend.entity.ErrCode;
import com.web_design.backend.mapper.AccountMapper;
import com.web_design.backend.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Value("${spring.mail.username}")
    String from;

    @Resource
    AccountMapper mapper;
    @Resource
    MailSender mailSender;

    @Resource
    StringRedisTemplate template;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email == null)
            throw new UsernameNotFoundException("邮箱不能为空");

        Account account = mapper.findAccountByEmail(email);
        if (account == null) {
            System.out.println("此用户不存在");
            throw new UsernameNotFoundException("此用户不存在");
        }

        return User
                .withUsername(account.getEmail())
                .password(account.getPassword())
                .authorities("user")
                .build();
    }

    /**
     * 1. 先生成对应的验证码
     * 2. 把邮箱和对应的验证码直接放到Redis中（过期时间3分钟，如果此时重新要求发邮件，
     * 那么，只要剩余时间低于2分钟，就可以重新发送一次，重复此流程）
     * 3. 发送验证码到指定邮箱
     * 4. 如果发送失败，把Redis里面的验证码删除
     * 5. 用户在注册时，再从Redis里面取出对应键值对，然后看验证码是否一致
     */
    @Override
    public ErrCode sendValidateEmail(String email, String sessionId) {
        String key = "email:" + sessionId + ":" + email;
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            long expire = Optional.ofNullable(template.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if (expire > 120) return ErrCode.ValidateCodeExpired;
        }

        Random random = new Random();
        int code = random.nextInt(899999) + 100000;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("BookWebTest");
        message.setText("您的验证码为：" + code);
        try {
            mailSender.send(message);
            template.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);
            return ErrCode.Success;
        } catch (MailException e) {
            e.printStackTrace();
            // 邮箱格式错误
            return ErrCode.EmailFormatError;
        }
    }

    @Override
    public ErrCode validateAndRegister(String email, String username, String password, String validateCode, String sessionId) {
        String key = "email:" + sessionId + ":" + email;
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            String code = template.opsForValue().get(key);
            // 验证码过期
            if (code == null) return ErrCode.ValidateCodeExpired;
            if (code.equals(validateCode)) {
                // 从Redis中删除验证码
                template.delete(key);
                // 验证邮箱是否已经注册
                if (mapper.findAccountByEmail(email) != null)
                    return ErrCode.EmailAlreadyRegistered;

                // 注册成功
                if (mapper.createAccount(username, encoder.encode(password), email) > 0)
                    return ErrCode.Success;
                else {
                    // 注册失败, SQL错误
                    return ErrCode.RegisterSqlFailed;
                }
            } else {
                // 验证码不匹配
                return ErrCode.ValidateCodeNotMatch;
            }
        } else {
            // 尚未发送验证码
            return ErrCode.NotSendValidateCode;
        }
    }

    @Override
    public ErrCode validateAndResetPassword(String email, String password, String validateCode, String sessionId) {
        String key = "email:" + sessionId + ":" + email;
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            String code = template.opsForValue().get(key);
            // 验证码过期
            if (code == null) return ErrCode.ValidateCodeExpired;
            if (code.equals(validateCode)) {
                // 从Redis中删除验证码
                template.delete(key);
                // 验证账号是否存在
                if (mapper.findAccountByEmail(email) == null)
                    return ErrCode.AccountNotExist;

                // 重置密码成功
                if (mapper.resetPassword(email, encoder.encode(password)) > 0)
                    return ErrCode.Success;
                else
                    // 重置密码失败, SQL错误
                    return ErrCode.ResetPasswordSqlFailed;
            } else {
                // 验证码不匹配
                return ErrCode.ValidateCodeNotMatch;
            }
        } else {
            // 尚未发送验证码
            return ErrCode.NotSendValidateCode;
        }
    }

}
