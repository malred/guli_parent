package com.malguy.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.malguy.commonutils.R;
import com.malguy.eduservice.entity.EduCourse;
import com.malguy.eduservice.entity.EduTeacher;
import com.malguy.eduservice.entity.chapter.CoursePublishVo;
import com.malguy.eduservice.entity.vo.CourseInfoVo;
import com.malguy.eduservice.entity.vo.CourseQuery;
import com.malguy.eduservice.entity.vo.TeacherQuery;
import com.malguy.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.Bidi;
import java.util.List;

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
    //课程列表查询
    @GetMapping
    public R getCourseList(){
        List<EduCourse> list=courseService.list(null);
        return R.ok().data("list",list);
    }
    //课程列表查询+条件+分页
    @ApiOperation(value = "条件分页查询课程")
    @PostMapping("get/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  //没加@PathVariable,就是非必须参数
                                  //@RequestBody可以把接收到的json数据封装到对应的对象里
                                  @RequestBody(required = false) CourseQuery courseQuery) {
        //创建page对象
        Page<EduCourse> coursePage = new Page(current, limit);
        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断是否为空
        //非空条件就拼接
        String teacherId = courseQuery.getTeacherId();
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id", teacherId);
        }
        String subjectId = courseQuery.getSubjectId();
        if (!StringUtils.isEmpty(subjectId)) {
            //eq 等于
            wrapper.eq("subject_id", subjectId);
        }
        String subjectParentId = courseQuery.getSubjectParentId();
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        String title = courseQuery.getTitle();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        BigDecimal price = courseQuery.getPrice();
        if (!StringUtils.isEmpty(price)) {
            wrapper.le("price", price);
        }
        Integer lessonNum = courseQuery.getLessonNum();
        if (!StringUtils.isEmpty(lessonNum)) {
            wrapper.ge("lesson_num", lessonNum);
        }
        Long buyCount = courseQuery.getBuyCount();
        if (!StringUtils.isEmpty(buyCount)) {
            wrapper.ge("buy_count", buyCount);
        }
        Long viewCount = courseQuery.getViewCount();
        if (!StringUtils.isEmpty(viewCount)) {
            wrapper.ge("view_count", viewCount);
        }
        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(status)) {
            wrapper.ge("status",status);
        }
        //排序,让新加的在前排
        wrapper.orderByDesc("gmt_create");
        //调用方法,实现条件查询
        courseService.page(coursePage, wrapper);
        long total = coursePage.getTotal();//总记录数
        List<EduCourse> records = coursePage.getRecords();//每页数据的集合
        return R.ok().data("total", total).data("rows", records);
    }
    //添加课程基本信息
    @PostMapping("addCourseInfo")
    public R getCourseInfo(@RequestBody CourseInfoVo info) {
        //返回课程的id,方便编辑课程大纲
        String id = courseService.saveCourseInfo(info);
        return R.ok().data("courseId", id);
    }

    //根据课程id查询
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfoByCourseId(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.geCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }
    //根据课程id获取课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublish=courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublish);
    }
    //修改课程的发布状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse=new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程发布状态
        boolean flag = courseService.updateById(eduCourse);
        if (flag) return R.ok();
        else return R.error();
    }
}

