package com.chuan.on_my_way.controller;

import com.chuan.on_my_way.utility.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传和下载
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${omw.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        try {
            String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String s = UUID.randomUUID().toString() + substring;
            File fil = new File(basePath);
            if (!fil.exists()){
                fil.mkdir();
            }
            file.transferTo(new File(basePath + s));

            return R.success(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            FileInputStream inputStream = new FileInputStream(new File(basePath + name));
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[inputStream.available()];
            while ((len = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0 , len);
                outputStream.flush();
            }

            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}