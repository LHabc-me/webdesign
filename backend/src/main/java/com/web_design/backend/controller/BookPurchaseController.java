package com.web_design.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.web_design.backend.entity.ErrCode;
import com.web_design.backend.service.BookPurchaseService;
import com.web_design.backend.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookPurchaseController {
    @Resource
    BookPurchaseService service;

    @Resource
    UserInfoService userInfoService;

    @PostMapping("/book/purchase")
    public boolean purchaseBook(@RequestBody JSONObject info) {
        String bookId = info.getString("bookId");
        int userId = info.getIntValue("userId");
        int coins = info.getIntValue("coins");
        return service.insertBookPurchase(bookId, userId)
                && (userInfoService.updateCoinsById(userId, coins) == ErrCode.Success);
    }

    @PostMapping("/book/is-purchased")
    public boolean isBookPurchased(@RequestBody JSONObject info) {
        String bookId = info.getString("bookId");
        int userId = info.getIntValue("userId");
        return service.isBookPurchased(bookId, userId);
    }

}
