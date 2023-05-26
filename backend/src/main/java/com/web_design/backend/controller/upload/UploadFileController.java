package com.web_design.backend.controller.upload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class UploadFileController {

    @PostMapping("/single-file-upload")
    public String singleFileUpload(@RequestParam("up-single-file") MultipartFile file) {
        System.out.println("开始处理上传文件");
        Map<String, Object> info = new HashMap<>();

        try {
            if (!file.isEmpty()) {
                info.put("上传文件的参数名称", file.getName());
                info.put("内容类型", file.getContentType());

                String ext = "unknown";
                String filename = file.getOriginalFilename(); // 获取上传文件原来的名称
                if (filename != null && filename.contains(".")) {
                    // 获取文件的后缀名
                    ext = filename.substring(filename.lastIndexOf("."));
                }
                // 生成服务器使用的文件名称
                String newFileName = UUID.randomUUID().toString() + ext;
                String filePath = "/home/kiakiana_423/UploadTest/";  // 上传文件保存的路径

                // 把文件保存到path路径下
                File temp = new File(filePath);
                if (!temp.exists()) {
                    temp.mkdirs();
                }

                File localFile = new File(filePath + newFileName);
                file.transferTo(localFile);

                info.put("文件名称", newFileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("info: " + info);
        // 重定向到index页面
        return "redirect:/index.html";
    }

    @PostMapping("/multi-file-upload")
    public String multiFileUpload(@RequestParam("up-multi-file") MultipartFile[] files) {
        System.out.println("开始处理上传文件");
        Map<String, Object> info = new HashMap<>();

        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    info.put("上传文件的参数名称", file.getName());
                    info.put("内容类型", file.getContentType());

                    String ext = "unknown";
                    String filename = file.getOriginalFilename(); // 获取上传文件原来的名称
                    if (filename != null && filename.contains(".")) {
                        // 获取文件的后缀名
                        ext = filename.substring(filename.lastIndexOf("."));
                    }
                    // 生成服务器使用的文件名称
                    String newFileName = UUID.randomUUID().toString() + ext;
                    String filePath = "/home/kiakiana_423/UploadTest/";  // 上传文件保存的路径

                    // 把文件保存到path路径下
                    File temp = new File(filePath);
                    if (!temp.exists()) {
                        temp.mkdirs();
                    }

                    File localFile = new File(filePath + newFileName);
                    file.transferTo(localFile);

                    info.put("文件名称", newFileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("info: " + info);
        // 重定向到index页面
        return "redirect:/index.html";
    }

//    @PostMapping("/multiUpload")
//    public Object multiUpload(@RequestParam("file") MultipartFile[] files) {
//        System.out.println("文件的个数:" + files.length);
//        for (MultipartFile f : files) {
//            saveFile(f);
//        }
//        return "ok";
//    }
//
//    private Object saveFile(MultipartFile file) {
//        if (file.isEmpty()) {
//            return "未选择文件";
//        }
//        String filename = file.getOriginalFilename(); //获取上传文件原来的名称
//        String filePath = "/home/kiakiana_423/UploadTest";
//        File temp = new File(filePath);
//        if (!temp.exists()) {
//            temp.mkdirs();
//        }
//
//        File localFile = new File(filePath + filename);
//        try {
//            file.transferTo(localFile); //把上传的文件保存至本地
//            System.out.println(file.getOriginalFilename() + " 上传成功");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "上传失败";
//        }
//
//        return "ok";
//    }


}
