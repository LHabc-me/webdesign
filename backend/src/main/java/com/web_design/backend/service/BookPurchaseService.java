package com.web_design.backend.service;

public interface BookPurchaseService {
    boolean isBookPurchased(String uuid, int userId);

    boolean insertBookPurchase(String uuid, int userId);

}
