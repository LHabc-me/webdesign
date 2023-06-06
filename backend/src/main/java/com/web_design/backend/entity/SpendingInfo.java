package com.web_design.backend.entity;

import lombok.Data;

@Data
public class SpendingInfo {
    String bookId;
    String time;
    int cost;
    int userId;
}
