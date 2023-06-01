package com.web_design.backend.mapper;

import com.web_design.backend.entity.file.FileInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {

    @Select("select * from file where filename=#{filename} and flag=true")
    FileInfo findFileByFileName(String filename);

    @Insert("insert into file(filename, uploaderId, original_filename, author, price, is_original, description) " +
            "values(#{filename}, #{uploaderId}, #{originalFilename}, #{author}, #{price}, #{isOriginal}, #{description}, #{tag})")
    int insertFile(String filename, int uploaderId, String originalFilename,
                   String author, int price, boolean isOriginal, String description, String tag);

    @Select("select * from file where uploaderId=#{uploaderId} and flag=true")
    FileInfo[] findFileByUploader(int uploaderId);

    @Select("select * from file where author=#{author} and flag=true")
    FileInfo[] findFileByAuthor(String author);

    @Select("select count(*) from file where flag=true")
    int countFileNumber();

    @Update("update file set flag=false where filename=#{filename} and flag=true")
    int deleteFile(String filename);

    @Update("update file set price=#{price} where filename=#{filename} and flag=true")
    int setPrice(String filename, int price);

    @Update("update file set description=#{description} where filename=#{filename} and flag=true")
    int setDescription(String filename, String description);

    @Update("update file set hot=#{hot} where filename=#{filename} and flag=true")
    int setHot(String filename, int hot);

    @Update("update file set tag=#{tag} where filename=#{filename} and flag=true")
    int setTag(String filename, String tag);

    @Select("select * from file where flag=true and filename like #{keywords} and tag=#{tag} " +
            "and is_original=#{isOriginal} and price between #{lprice} and #{hprice}")
    FileInfo[] findFileByMultiCondition(String keywords, String tag, boolean isOriginal, int lprice, int hprice);

    @Select("select * from file where flag=true and filename like #{keywords} and tag=#{tag} " +
            "and price between #{lprice} and #{hprice}")
    FileInfo[] findFileByMultiConditionWithoutIsOriginal(String keywords, String tag, int lprice, int hprice);

    @Select("select * from file where flag=true and filename like #{keywords} and tag=#{tag} " +
            "and is_original=#{isOriginal}")
    FileInfo[] findFileByMultiConditionWithoutPrice(String keywords, String tag, boolean isOriginal);
}
