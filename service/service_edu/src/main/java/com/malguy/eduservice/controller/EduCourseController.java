package com.malguy.eduservice.controller;


import com.malguy.commonutils.R;
import com.malguy.eduservice.entity.vo.CourseInfoVo;
import com.malguy.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-25
 */
@RestController
@RequestMapping("/eduservice/edu-course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;
    //添加课程基本信息
    @PostMapping("addCourseInfo")
    public R getCourseInfo(@RequestBody CourseInfoVo info) {
        //返回课程的id,方便编辑课程大纲
        String id=courseService.saveCourseInfo(info);
        return R.ok().data("courseId",id);
    }
}

