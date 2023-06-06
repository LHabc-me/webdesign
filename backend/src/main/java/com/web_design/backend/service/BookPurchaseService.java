package com.web_design.backend.service;

import com.web_design.backend.entity.BookId;

public interface BookPurchaseService {
    boolean isBookPurchased(String uuid, int userId);

    boolean insertBookPurchase(String bookId, int userId, int cost, String time);

    BookId[] findBookPurchaseByUserId(int userId);

}
