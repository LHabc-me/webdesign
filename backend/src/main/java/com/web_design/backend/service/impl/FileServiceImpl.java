package com.web_design.backend.service.impl;

import com.web_design.backend.entity.file.FileInfo;
import com.web_design.backend.mapper.FileMapper;
import com.web_design.backend.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    FileMapper mapper;

    public String uploadFile(String filename, String uploader, String originalFilename, String author) {
        if (mapper.insertFile(filename, uploader, originalFilename, author) > 0)
            return "upload file success";
        else
            return "upload file failed";
    }

    public FileInfo findFileByFileName(String filename) {
        return mapper.findFileByFileName(filename);
    }

    public FileInfo[] findFileByUploader(String uploader) {
        return mapper.findFileByUploader(uploader);
    }

    public FileInfo[] findFileByAuthor(String author) {
        return mapper.findFileByAuthor(author);
    }

    public int countFileNumber() {
        return mapper.countFileNumber();
    }
}
