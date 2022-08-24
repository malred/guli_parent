package com.malguy.eduservice.controller;

import com.malguy.commonutils.R;
import com.malguy.commonutils.UploadUtils;
import com.malguy.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author malguy-wang sir
 * @create ---
 */
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private EduTeacherService teacherService;
    //上传头像的方法
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        //获取上传文件  MultipartFile
        //获取文件的内容
        try {
            InputStream is = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        //生成一个uuid名称出来
        String uuidFilename = UploadUtils.getUUIDName(originalFilename);
//        //在linux下创建文件夹
//        File fileDir = new File("var/blob/guli_user");
//        //若文件夹不存在,则创建出文件夹
//        if (!fileDir.exists()) {
//            fileDir.mkdirs();
//        }
        //创建新的文件
        //linux
//        File newFile = new File("var/blob/guli_user",uuidFilename);
        //windows
        File newFile = new File("D:/blob/guli_user",uuidFilename);
        //将文件输出到目标的文件中
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回上传到oss的路径
        return R.ok().data("url",
                "http://localhost:9001/img/eduservice/teacher/"+uuidFilename);
    }
}
