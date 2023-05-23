package com.web_design.backend.mapper;

import com.web_design.backend.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where email=#{text}")
    Account findAccountByEmail(String text);

    @Insert("insert into user (username, password, email) values (#{username}, #{password}, #{email})")
    int createAccount(String username, String password, String email);
}
