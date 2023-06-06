package com.web_design.backend.service.impl;

import com.web_design.backend.entity.BookId;
import com.web_design.backend.mapper.BookPurchaseMapper;
import com.web_design.backend.service.BookPurchaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class BookPurchaseServiceImpl implements BookPurchaseService {
    @Resource
    BookPurchaseMapper mapper;

    @Override
    public boolean isBookPurchased(String bookId, int userId) {
        return mapper.findBookPurchaseByFilenameAndUserId(bookId, userId);
    }

    @Override
    public boolean insertBookPurchase(String bookId, int userId, int cost, String time) {
        return mapper.insertBookPurchase(bookId, userId, cost, time) > 0;
    }

    @Override
    public BookId[] findBookPurchaseByUserId(int userId) {
        return mapper.findBookPurchaseByUserId(userId);
    }
}
