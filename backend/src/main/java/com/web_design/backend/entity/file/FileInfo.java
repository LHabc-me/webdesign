package com.web_design.backend.entity.file;

import lombok.Data;

@Data
public class FileInfo {
    String id;
    String name;
    String url;
    String type;
    long size;
    String uploadTime;
    String uploader;
    String description;
}
