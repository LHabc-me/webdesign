package com.web_design.backend.controller;

import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.base64.Base64Encoder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;

@RestController
public class SendFileToFrontController {

    @Value("${spring.servlet.multipart.location}")
    private String uploadFolder;

    @PostMapping("/book/send")
    public void sendFileToFront(@RequestBody JSONObject info, HttpServletResponse response) {
        try {
            // path是指想要下载的文件的路径
            String path = uploadFolder + "Books/";
            String bookId = info.getString("bookId");
            File file = new File(path + bookId);
            // 获取文件名
            String filename = file.getName();
            // 获取文件后缀名
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

            // 将文件写入输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);


            // 清空response
//            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
//            response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // 告知浏览器文件的大小
//            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/pdf");
//            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.getOutputStream().write(Base64.getEncoder().encodeToString(buffer).getBytes());
            fis.close();
//            outputStream.write(buffer);
//            outputStream.flush();
//            outputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
//            return "send failed";
        }
//        return response.toString();
    }
}
