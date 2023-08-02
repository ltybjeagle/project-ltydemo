package com.sunny.maven.middle.thymeleaf.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author SUNNY
 * @description: 上传
 * @create: 2023/7/21 11:20
 */
@RestController
public class FileUploadController {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @PostMapping("/upload")
    public String upload(MultipartFile[] uploadFiles, HttpServletRequest req) {
        String realPath = req.getSession().getServletContext().getRealPath("/uploadFile/");
        String format = sdf.format(new Date());
        File folder = new File(realPath + format);
        if (!folder.isDirectory()) {
            boolean isSuccess = folder.mkdirs();
            if (!isSuccess) return "上传目录创建失败!";
        }
        StringBuilder uploadResult = new StringBuilder();
        for (MultipartFile uploadFile : uploadFiles) {
            String oldName = uploadFile.getOriginalFilename();
            assert oldName != null;
            String newName = UUID.randomUUID() + oldName.substring(oldName.indexOf("."));

            try {
                uploadFile.transferTo(new File(folder, newName));
                uploadResult.append(req.getScheme()).
                        append("://").
                        append(req.getServerName()).
                        append(":").
                        append(req.getServerPort()).
                        append("/uploadFile/").
                        append(format).
                        append(newName).
                        append(";");
            } catch (Exception e) {
                uploadResult.setLength(0);
                uploadResult.append("上传失败!");
                e.printStackTrace();
            }
        }
        return uploadResult.toString();
    }
}
