package com.web_design.backend.entity;

import lombok.Data;

@Data
public class FileInfo {
    String bookId;
    int uploaderId;
    String originalFilename;
    String author;
    boolean flag = true;    // 表示是否被删除
    int price = 0;
    boolean isOriginal;     // 表示是否为原创
    String description = "";
    int hot = 0;
    String tag;
}
