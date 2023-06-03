package com.web_design.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.web_design.backend.entity.file.FileInfo;
import com.web_design.backend.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/book/search")
public class BookSearchController {
    @Resource
    FileService service;

    @PostMapping("/bookId")
    public FileInfo searchByFilename(@RequestBody JSONObject filename) {
        return service.findFileByFileName(filename.getString("bookId"));
    }

    @PostMapping("/uploaderId")
    public FileInfo[] searchByUploaderId(@RequestBody JSONObject uploaderId) {
        return service.findFileByUploaderId(uploaderId.getIntValue("uploaderId"));
    }

    @PostMapping("/author")
    public FileInfo[] searchByAuthor(@RequestBody JSONObject author) {
        return service.findFileByAuthor(author.getString("author"));
    }

    // 实现多条件查询
    @PostMapping("/keywords")
    public FileInfo[] searchByKeywords(@RequestBody JSONObject keywords) {
        return service.findFileByKeywords("%" + keywords.getString("keywords") + "%");
    }
//    @PostMapping("/keywords")
//    public String searchByKeywords(@RequestBody JSONObject keywords) {
//        System.out.println(keywords.getString("keywords"));
//        return JSON.toJSONString(service
//                .findFileByKeywords(keywords.getString("keywords")));
//    }

//    @PostMapping("/multi-condition-without-original")
//    public JSONObject searchByMultiConditionWithoutIsOriginal(String keywords, String tag, int lprice, int hprice) {
//        return JSON.parseObject(JSON.toJSONString(service
//                .findFileByMultiConditionWithoutIsOriginal(keywords, tag, lprice, hprice)));
//    }
//
//    @PostMapping("/multi-condition-without-price")
//    public JSONObject searchByMultiConditionWithoutPrice(String keywords, String tag, boolean isOriginal) {
//        return JSON.parseObject(JSON.toJSONString(service
//                .findFileByMultiConditionWithoutPrice(keywords, tag, isOriginal)));
//    }
}
