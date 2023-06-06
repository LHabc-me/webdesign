package com.web_design.backend.mapper;

import com.web_design.backend.entity.BookId;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BookPurchaseMapper {
    @Select("select count(*) from bookPurchase where bookId=#{uuid} and userId=#{userId}")
    boolean findBookPurchaseByFilenameAndUserId(String uuid, int userId);

    @Insert("insert into bookPurchase(bookId,userId,cost,time) values(#{bookId},#{userId},#{cost},#{time})")
    int insertBookPurchase(String bookId, int userId, int cost, String time);

    @Select("select bookId from bookPurchase where userId=#{userId}")
    BookId[] findBookPurchaseByUserId(int userId);
}
