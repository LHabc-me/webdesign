package com.web_design.backend.service.impl;

import com.web_design.backend.mapper.CommentsMapper;
import com.web_design.backend.service.CommentsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Resource
    CommentsMapper mapper;

    @Override
    public boolean insertComment(String filename, int userId, String content) {
        return mapper.insertComment(filename, userId, content) > 0;
    }

    @Override
    public boolean deleteCommentByFilename(String filename) {
        return mapper.deleteCommentByFilename(filename) > 0;
    }

    @Override
    public String[] findCommentByFilename(String filename) {
        return mapper.findCommentByFilename(filename);
    }

    @Override
    public String[] findCommentByUserId(int userId) {
        return mapper.findCommentByUserId(userId);
    }
}
