package com.web_design.backend.service.impl;

import com.web_design.backend.entity.FileInfo;
import com.web_design.backend.mapper.FileMapper;
import com.web_design.backend.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    FileMapper mapper;

    @Override
    public String uploadFile(String filename, int uploaderId, String originalFilename,
                             String author, int price, boolean isOriginal, String description, String tag) {
        if (mapper.insertFile(filename, uploaderId, originalFilename, author, price, isOriginal, description, tag) > 0)
            return "upload file success";
        else
            return "upload file failed";
    }

    @Override
    public FileInfo findFileByFileName(String filename) {
        FileInfo fileInfo = mapper.findFileByFileName(filename);
        System.out.println(fileInfo);
        return fileInfo;
    }

    @Override
    public FileInfo[] findFileByUploaderId(int uploaderId) {
        return mapper.findFileByUploader(uploaderId);
    }

    @Override
    public FileInfo[] findFileByAuthor(String author) {
        return mapper.findFileByAuthor(author);
    }

    @Override
    public FileInfo[] findFileByKeywords(String keywords) {
        return mapper.findFileByKeywords(keywords);
    }

    @Override
    public int countFileNumber() {
        return mapper.countFileNumber();
    }

    @Override
    public boolean deleteFile(String filename) {
        return mapper.deleteFile(filename) > 0;
    }

    @Override
    public boolean setFileHot(String filename, int hot) {
        return mapper.setHot(filename, hot) > 0;
    }

    @Override
    public boolean setFilePrice(String filename, int price) {
        return mapper.setPrice(filename, price) > 0;
    }

    @Override
    public boolean setFileAuthor(String filename, String author) {
        return mapper.setAuthor(filename, author) > 0;
    }

    @Override
    public boolean setFileOriginal(String filename, boolean isOriginal) {
        return mapper.setOriginal(filename, isOriginal) > 0;
    }

    @Override
    public boolean setFileDescription(String filename, String description) {
        return mapper.setDescription(filename, description) > 0;
    }

    @Override
    public boolean setFileTag(String filename, String tag) {
        return mapper.setTag(filename, tag) > 0;
    }

//    @Override
//    public FileInfo[] findFileByMultiCondition(String keywords, String tag, boolean isOriginal, int lprice, int hprice) {
//        return mapper.findFileByMultiCondition(keywords, tag, isOriginal, lprice, hprice);
//    }
//
//    @Override
//    public FileInfo[] findFileByMultiConditionWithoutIsOriginal(String keywords, String tag, int lprice, int hprice) {
//        return mapper.findFileByMultiConditionWithoutIsOriginal(keywords, tag, lprice, hprice);
//    }
//
//    @Override
//    public FileInfo[] findFileByMultiConditionWithoutPrice(String keywords, String tag, boolean isOriginal) {
//        return mapper.findFileByMultiConditionWithoutPrice(keywords, tag, isOriginal);
//    }
}
