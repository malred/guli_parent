package com.malguy.eduservice.controller;


import com.malguy.commonutils.R;
import com.malguy.eduservice.entity.subject.OneSubject;
import com.malguy.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-24
 */
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
@Api(description = "课程管理api")
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传过来文件，把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //上传过来excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }
    //课程分类列表
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list=subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

