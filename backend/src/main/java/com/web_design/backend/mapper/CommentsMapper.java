package com.web_design.backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CommentsMapper {
    @Insert("insert into comments(bookId, user_id, content) values(#{filename}, #{userId}, #{content})")
    int insertComment(String filename, int userId, String content);

    @Update("update comments set flag=false where bookId=#{filename}")
    int deleteCommentByFilename(String filename);

    @Select("select * from comments where bookId=#{filename} and flag=true")
    String[] findCommentByFilename(String filename);

    @Select("select * from comments where user_id=#{userId} and flag=true")
    String[] findCommentByUserId(int userId);

}
