package com.web_design.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.web_design.backend.entity.Comment;
import com.web_design.backend.entity.RestBean;
import com.web_design.backend.service.CommentsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/comments")
public class CommentsController {

    @Resource
    CommentsService commentsService;

    @PostMapping("/comments/insert")
    public RestBean insertComment(@RequestBody JSONObject info) {
        String filename = info.getString("bookId");
        int userId = info.getIntValue("userId");
        String content = info.getString("content");
        return commentsService.insertComment(filename, userId, content)
                ? RestBean.success("insert success")
                : RestBean.failure(400, "insert failed");
    }

//    @PostMapping("/comments/find-by-uuid")
//    public RestBean insertComment(String filename, int userId, String content) {
//        return commentsService.insertComment(filename, userId, content)
//                ? RestBean.success("insert success")
//                : RestBean.failure(400, "insert failed");
//    }

    @PostMapping("/comments/find/bookId")
    public Comment[] findCommentByFilename(@RequestBody JSONObject info) {
        String bookId = info.getString("bookId");
        return commentsService.findCommentByFilename(bookId);
    }

    @PostMapping("/comments/find/userid")
    public Comment[] findCommentByUserId(@RequestBody JSONObject info) {
        int userId = info.getIntValue("userId");
        return commentsService.findCommentByUserId(userId);
    }

}
