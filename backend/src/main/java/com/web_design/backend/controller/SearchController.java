package com.web_design.backend.controller;

import com.web_design.backend.entity.file.FileInfo;
import com.web_design.backend.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController {
    @Resource
    FileService service;

    @RequestMapping("/search-by-filename")
    public FileInfo searchByFilename(String filename) {
        return service.findFileByFileName(filename);
    }

    @RequestMapping("/search-by-uploader")
    public FileInfo searchByUploader(String uploader) {
        return service.findFileByUploader(uploader);
    }
}
