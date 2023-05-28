package com.web_design.backend.entity.file;

import lombok.Data;

@Data
public class FileInfo {
    String filename;
    String originalFilename;
    String uploader;
    String author;
}
