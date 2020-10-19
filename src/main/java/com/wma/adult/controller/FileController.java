package com.wma.adult.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
public class FileController {
    @PostMapping("/uploadFile")
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        try {
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println("文件名字：" + fileName + " 后缀名字：" + suffixName);
            String path = "F:\\upload\\";
            String filePath = path + fileName;
            File desc = new File(filePath);
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
            file.transferTo(desc);
            System.out.println("path = " + desc.getAbsolutePath());
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }


    @PostMapping("/uploadFiles")
    public @ResponseBody
    String uploadFiles(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("fileName");
        System.out.println("files.size() = " + files.size());
        if (files.isEmpty()) {
            return "false";
        }

        String path = "F:\\upload\\";
        try {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                System.out.println("文件名字：" + fileName + " 后缀名字：" + suffixName);
                String filePath = path + fileName;
                File desc = new File(filePath);
                if (!desc.getParentFile().exists()) {
                    desc.getParentFile().mkdirs();
                }
                file.transferTo(desc);
                System.out.println("path = " + desc.getAbsolutePath());
            }
            return "上传成功";

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response, @RequestParam("fileName") String fileName) throws Exception {
        // 文件地址，真实环境是存放在数据库中的
        String path = "F:\\upload\\";
        String descPath = path + fileName;
        System.out.println(descPath);
        File file = new File(descPath);
        // 穿件输入对象
        FileInputStream fis = new FileInputStream(file);
        // 设置相关格式
        response.setContentType("application/force-download");
        // 设置下载后的文件名以及header
        response.addHeader("Content-disposition", "attachment;fileName=" + fileName);
        // 创建输出对象
        OutputStream os = response.getOutputStream();
        // 常规操作
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        fis.close();
    }
}
