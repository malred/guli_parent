package com.malguy.eduservice.service;

import com.malguy.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.malguy.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-08-25
 */
public interface EduCourseService extends IService<EduCourse> {
    String saveCourseInfo(CourseInfoVo info);
}
