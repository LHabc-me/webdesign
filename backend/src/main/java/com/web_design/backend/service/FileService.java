package com.web_design.backend.service;

import com.web_design.backend.entity.file.FileInfo;

public interface FileService {
    String uploadFile(String filename, String uploader, String originalFilename, String author);

    FileInfo findFileByFileName(String filename);

    FileInfo[] findFileByUploader(String uploader);

    FileInfo[] findFileByAuthor(String author);

    int countFileNumber();
}
