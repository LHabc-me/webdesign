package com.web_design.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.web_design.backend.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book/search")
public class BookSearchController {
    @Resource
    FileService service;

    @PostMapping("/bookId")
    public JSONObject searchByFilename(String filename) {
        return JSON.parseObject(JSON.toJSONString(service.findFileByFileName(filename)));
    }

    @PostMapping("/uploaderId")
    public JSONObject searchByUploaderId(int uploaderId) {
        return JSON.parseObject(JSON.toJSONString(service.findFileByUploaderId(uploaderId)));
    }

    @PostMapping("/author")
    public JSONObject searchByAuthor(String author) {
        return JSON.parseObject(JSON.toJSONString(service.findFileByAuthor(author)));
    }

    // 实现多条件查询
    @PostMapping("/multi-condition")
    public JSONObject searchByMultiCondition(String keywords, String tag, boolean isOriginal, int lprice, int hprice) {
        return JSON.parseObject(JSON.toJSONString(service
                .findFileByMultiCondition(keywords, tag, isOriginal, lprice, hprice)));
    }

    @PostMapping("/multi-condition-without-original")
    public JSONObject searchByMultiConditionWithoutIsOriginal(String keywords, String tag, int lprice, int hprice) {
        return JSON.parseObject(JSON.toJSONString(service
                .findFileByMultiConditionWithoutIsOriginal(keywords, tag, lprice, hprice)));
    }

    @PostMapping("/multi-condition-without-price")
    public JSONObject searchByMultiConditionWithoutPrice(String keywords, String tag, boolean isOriginal) {
        return JSON.parseObject(JSON.toJSONString(service
                .findFileByMultiConditionWithoutPrice(keywords, tag, isOriginal)));
    }
}
