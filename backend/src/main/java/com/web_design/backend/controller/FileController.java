package com.web_design.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.web_design.backend.service.CommentsService;
import com.web_design.backend.service.FileService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
//@RequestMapping("/file")
public class FileController {

    @Resource
    FileService fileService;

    @Resource
    CommentsService commentsService;

    // 上传头像
    @PostMapping("/upload/avatar")
    @ResponseBody
    public String avatarUpload(MultipartFile file, HttpServletRequest request) {
        System.out.println("开始处理上传头像");
        Map<String, Object> info = new HashMap<>();

        try {
            if (!file.isEmpty()) {
                info.put("上传文件的参数名称", file.getName());
                info.put("内容类型", file.getContentType());
                // 生成文件名称
                // token为不带后缀的文件名，供前端读取作为头像使用
                String token = request.getSession().getAttribute("token").toString();
                // 生成存储到数据库中的文件名
                String filePath = "/home/kiakiana_423/Avatar/";  // 上传文件保存的路径

                // 把文件保存到path路径下
                File temp = new File(filePath);
                if (!temp.exists()) {
                    temp.mkdirs();
                }

                File localFile = new File(filePath + token);
                file.transferTo(localFile);

                info.put("token", token);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "upload failed";
        }

        System.out.println("info: " + info);
        return "upload success";
    }

    // 上传作品
    @PostMapping("/upload/book")
    @ResponseBody
//    public String worksUpload(MultipartFile file, @RequestBody JSONObject form) {
    public String worksUpload(MultipartFile file,
                              @RequestParam("author") String author,
                              @RequestParam("uploaderId") int uploader,
                              @RequestParam("price") int price,
                              @RequestParam("isOriginal") boolean isOriginal,
                              @RequestParam("description") String description,
                              @RequestParam("tag") String tag) {
        System.out.println("开始处理上传文件");
        Map<String, Object> info = new HashMap<>();
        try {
            if (!file.isEmpty()) {
                // 上传文件的作者, 上传者
//                String author = form.getString("author");
//                int uploader = form.getIntValue("uploader");
                System.out.println("author: " + author + ", uploader: " + uploader);
                String ext = "unknown";
                String originalFilename = file.getOriginalFilename(); // 获取上传文件原来的名称
                if (originalFilename != null && originalFilename.contains(".")) {
                    // 获取文件的后缀名
                    ext = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                // 生成服务器使用的文件名称
                String newFileName = UUID.randomUUID() + ext;
                String filePath = "/home/kiakiana_423/Books/";  // 上传文件保存的路径

                // 把文件保存到path路径下
                File temp = new File(filePath);
                if (!temp.exists()) {
                    temp.mkdirs();
                }

                File localFile = new File(filePath + newFileName);
                file.transferTo(localFile);

                info.put("文件原名称", originalFilename);
                info.put("文件新名称", newFileName);
                info.put("作者", author);
                info.put("上传者ID", uploader);
                info.put("价格", price);
                info.put("是否原创", isOriginal);
                info.put("介绍", description);

                // 上传文件信息到数据库
                System.out.println("info: " + info);
                return fileService.uploadFile(newFileName, uploader, originalFilename,
                        author, price, isOriginal, description, tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "upload failed";
        }

//        System.out.println("info: " + info);
        return "upload success";
    }

    @PostMapping("/book/delete")
    @ResponseBody
    public String deleteBook(@RequestBody JSONObject jsonParam) {
        String filename = jsonParam.getString("bookId");
        return fileService.deleteFile(filename) && commentsService.deleteComment(filename) ? "delete success" : "delete failed";
    }
}
