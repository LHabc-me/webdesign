package com.web_design.backend.entity;

import lombok.Data;

@Data
public class Comment {
    int id;
    String bookId;
    String userId;
    String content;
}
