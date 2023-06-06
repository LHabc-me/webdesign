package com.web_design.backend.mapper;

import com.web_design.backend.entity.SpendingInfo;
import com.web_design.backend.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserInfoMapper {
    @Select("select * from user where id=#{id}")
    UserInfo findUserInfoById(int id);

    @Select("select * from user where email=#{text}")
    UserInfo findUserInfoByEmail(String text);

    @Select("select * from bookPurchase")
    SpendingInfo[] getALlSpendingInfo();

    @Update("update user set password=#{password} where id=#{id}")
    int updatePasswordById(int id, String password);

    @Update("update user set password=#{password} where email=#{email}")
    int resetPassword(String email, String password);

    @Update("update user set username=#{username} where id=#{id}")
    int updateUsernameById(int id, String username);

    @Update("update user set username=#{username} where email=#{email}")
    int updateUsernameByEmail(String email, String username);

    @Update("update user set coins=#{coins} where id=#{id}")
    int updateUserCoinsById(int id, int coins);

    @Update("update user set coins=#{coins} where email=#{email}")
    int updateUserCoinsByEmail(String email, int coins);

    @Update("update user set hot=#{hot} where id=#{id}")
    int setUserHotById(int id, int hot);

    @Update("update user set hot=#{hot} where email=#{email}")
    int setUserHotByEmail(String email, int hot);
}
