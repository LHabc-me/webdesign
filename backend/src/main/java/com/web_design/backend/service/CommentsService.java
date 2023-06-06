package com.web_design.backend.service;

import com.web_design.backend.entity.Comment;

public interface CommentsService {

    boolean insertComment(String filename, int userId, String content);

    boolean deleteCommentByFilename(String filename);

    Comment[] findCommentByFilename(String filename);

    Comment[] findCommentByUserId(int userId);
}
