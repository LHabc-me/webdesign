package com.web_design.backend.controller.upload;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UploadAction {

    @PostMapping("/files")
    public String upload(HttpServletRequest request) {
        try {
            for (Part part : request.getParts()) {
                String fileName = extractFileName(part);
                // 将文件写入到指定路径
                part.write(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/index.html";
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");

        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }

        return "";
    }
}
