package com.web_design.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.web_design.backend.entity.RestBean;
import com.web_design.backend.service.CommentsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/comments")
public class CommentsController {

    @Resource
    CommentsService commentsService;

    @PostMapping("/comments/insert")
    public RestBean insertComment(String filename, int userId, String content) {
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
    public JSONObject findCommentByFilename(String filename) {
        return JSON.parseObject(JSONObject.toJSONString(commentsService.findCommentByFilename(filename)));
    }

    @PostMapping("/comments/find/userid")
    public JSONObject findCommentByUserId(int userId) {
        return JSON.parseObject(JSONObject.toJSONString(commentsService.findCommentByUserId(userId)));
    }

}
