package com.web_design.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.web_design.backend.service.FileService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class UploadFileController {

    @Resource
    FileService service;

    @PostMapping("/single-file-upload")
//    @ResponseBody
    public JSONObject singleFileUpload(@RequestParam("up-single-file") MultipartFile file, HttpServletRequest request) {
        System.out.println("开始处理上传文件");
        Map<String, Object> info = new HashMap<>();

        try {
            if (!file.isEmpty()) {
                // 上传文件的参数名称
//                info.put("上传文件的参数名称", file.getName());
                // 上传文件的类型
                info.put("file-type", file.getContentType());

                String ext = "unknown";
                String filename = file.getOriginalFilename(); // 获取上传文件原来的名称
                if (filename != null && filename.contains(".")) {
                    // 获取文件的后缀名
                    ext = filename.substring(filename.lastIndexOf("."));
                }
                // 生成服务器使用的文件名称
                String newFileName = UUID.randomUUID() + ext;
                String filePath = "/home/kiakiana_423/UploadTest/";  // 上传文件保存的路径

                // 把文件保存到path路径下
                File temp = new File(filePath);
                if (!temp.exists()) {
                    temp.mkdirs();
                }

                File localFile = new File(filePath + newFileName);
                file.transferTo(localFile);

                info.put("文件原名称", filename);
                info.put("文件名称", newFileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("info: " + info);
        // 返回上传文件的信息，以json格式返回
        return new JSONObject(info);
    }

    // 上传头像
    @PostMapping("/upload/user/avatar")
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
        }

        System.out.println("info: " + info);
        // 重定向到index页面
        return "redirect:http://localhost:8080/login.html";
    }

    // 上传作品
    @PostMapping("/upload/author/new-book")
    @ResponseBody
    public String worksUpload(MultipartFile file, @RequestHeader("form") JSONObject form) {
        System.out.println("开始处理上传文件");
        Map<String, Object> info = new HashMap<>();

        try {
            if (!file.isEmpty()) {
                // 上传文件的作者, 上传者
                String author = form.getString("author");
                String uploader = form.getString("uploader");

                String ext = "unknown";
                String originalFilename = file.getOriginalFilename(); // 获取上传文件原来的名称
                if (originalFilename != null && originalFilename.contains(".")) {
                    // 获取文件的后缀名
                    ext = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                // 生成服务器使用的文件名称
                String newFileName = UUID.randomUUID() + ext;
                String filePath = "/home/kiakiana_423/UploadTest/";  // 上传文件保存的路径

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
                info.put("上传者", uploader);

                // 上传文件信息到数据库
                service.uploadFile(newFileName, uploader, originalFilename, author);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("info: " + info);
        return "上传成功";
    }

}
