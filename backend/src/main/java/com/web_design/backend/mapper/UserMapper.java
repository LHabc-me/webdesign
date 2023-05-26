package com.web_design.backend.mapper;

import com.web_design.backend.entity.auth.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from user where email=#{text}")
    Account findAccountByEmail(String text);

    @Insert("insert into user (username, password, email) values (#{username}, #{password}, #{email})")
    int createAccount(String username, String password, String email);

    @Update("update user set password=#{password} where email=#{email}")
    int resetPassword(String email, String password);
}
