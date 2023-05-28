package com.web_design.backend.mapper;

import com.web_design.backend.entity.file.FileInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {

    @Select("select * from file where filename=#{filename}")
    FileInfo findFileByFileName(String filename);

    @Insert("insert into file(filename, original-filename, uploader, author) values(#{filename}, #{originalFilename}, #{uploader}, #{author})")
    int insertFile(String filename, String uploader, String originalFilename, String author);

    @Select("select * from file where uploader=#{uploader}")
    FileInfo findFileByUploader(String uploader);
}
