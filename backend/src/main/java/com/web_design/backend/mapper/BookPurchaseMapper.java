package com.web_design.backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BookPurchaseMapper {
    @Select("select * from book_purchase where filename=#{uuid} and user=#{userId}")
    int findBookPurchaseByFilenameAndUserId(String uuid, int userId);

    @Insert("insert into book_purchase(filename,user) values(#{uuid},#{userId})")
    int insertBookPurchase(String uuid, int userId);
}
