package com.web_design.backend.service;

import com.web_design.backend.entity.FileInfo;

public interface FileService {
    String uploadFile(String filename, int uploaderId, String originalFilename,
                      String author, int price, boolean isOriginal, String description, String tag);

    FileInfo findFileByFileName(String filename);

//    FileInfo[] findFileByMultiCondition(String keywords, String tag, boolean isOriginal, int lprice, int hprice);
//
//    FileInfo[] findFileByMultiConditionWithoutIsOriginal(String keywords, String tag, int lprice, int hprice);
//
//    FileInfo[] findFileByMultiConditionWithoutPrice(String keywords, String tag, boolean isOriginal);

    FileInfo[] findFileByKeywords(String keywords);

    FileInfo[] findFileByUploaderId(int uploader);

    FileInfo[] findFileByAuthor(String author);

    int countFileNumber();

    boolean deleteFile(String filename);

    boolean setFileHot(String filename, int hot);

    boolean setFileAuthor(String filename, String author);

    boolean setFileOriginal(String filename, boolean isOriginal);

    boolean setFilePrice(String filename, int price);

    boolean setFileDescription(String filename, String description);

    boolean setFileTag(String filename, String tag);
}
