package com.web_design.backend.mapper;

import com.web_design.backend.entity.file.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {

    @Select("select * from file where filename=#{filename}")
    FileInfo findFileByFileName(String filename);


}
