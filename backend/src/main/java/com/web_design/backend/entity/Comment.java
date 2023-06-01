package com.web_design.backend.entity;

import lombok.Data;

@Data
public class Comment {
    int id;
    String filename;
    String userId;
    String content;
}
