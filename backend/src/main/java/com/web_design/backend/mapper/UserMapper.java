package com.web_design.backend.mapper;

import com.web_design.backend.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{text} or email=#{text}")
    Account findAccountByNameOrEmail(String text);
}
