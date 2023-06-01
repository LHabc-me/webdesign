package com.web_design.backend.service;

public interface CommentsService {

    boolean insertComment(String filename, int userId, String content);

    boolean deleteCommentByFilename(String filename);

    String[] findCommentByFilename(String filename);

    String[] findCommentByUserId(int userId);
}
