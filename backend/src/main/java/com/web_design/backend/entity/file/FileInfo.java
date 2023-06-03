package com.web_design.backend.entity.file;

import lombok.Data;

@Data
public class FileInfo {
    String bookId;
    String originalFilename;
    int uploaderId;
    String author;
    int price = 0;
    boolean flag = true;    // 表示是否被删除
    boolean isOriginal;     // 表示是否为原创
    String description;
    int hot = 0;
    String tag;
}
