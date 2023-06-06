package com.web_design.backend.mapper;

import com.web_design.backend.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CommentsMapper {
    @Insert("insert into comments(bookId, userId, content) values(#{filename}, #{userId}, #{content})")
    int insertComment(String filename, int userId, String content);

    @Update("update comments set flag=false where bookId=#{filename}")
    int deleteCommentByFilename(String filename);

    @Select("select * from comments where bookId=#{bookId} and flag=true")
    Comment[] findCommentByFilename(String bookId);

    @Select("select * from comments where userId=#{userId} and flag=true")
    Comment[] findCommentByUserId(int userId);

}
