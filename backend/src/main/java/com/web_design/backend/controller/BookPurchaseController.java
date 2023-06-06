package com.web_design.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.web_design.backend.entity.BookId;
import com.web_design.backend.entity.FileInfo;
import com.web_design.backend.service.BookPurchaseService;
import com.web_design.backend.service.FileService;
import com.web_design.backend.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class BookPurchaseController {
    @Resource
    BookPurchaseService service;

    @Resource
    FileService bookService;

    @Resource
    UserInfoService userInfoService;

    @PostMapping("/book/purchase")
    public boolean purchaseBook(@RequestBody JSONObject info) {
        String bookId = info.getString("bookId");
        int userId = info.getIntValue("userId");
        int coins = info.getIntValue("coins");
        int cost = bookService.findFileByFileName(bookId).getPrice();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        String time = dateFormat.format(date);
        return service.insertBookPurchase(bookId, userId, cost, time)
                && userInfoService.updateCoinsById(userId, coins);
    }

    @PostMapping("/book/is-purchased")
    public boolean isBookPurchased(@RequestBody JSONObject info) {
        String bookId = info.getString("bookId");
        int userId = info.getIntValue("userId");
        return service.isBookPurchased(bookId, userId);
    }

    @PostMapping("/book/purchase/search")
    public FileInfo[] findBookPurchaseByUserId(@RequestBody JSONObject info) {
        int userId = info.getIntValue("userId");
        BookId[] bookIds = service.findBookPurchaseByUserId(userId);
        FileInfo[] fileInfos = new FileInfo[bookIds.length];
        for (int i = 0; i < bookIds.length; i++) {
            fileInfos[i] = bookService.findFileByFileName(bookIds[i].getBookId());
        }
        return fileInfos;
    }
}
