package com.malguy.eduservice.service.impl;

import com.malguy.eduservice.entity.EduCourse;
import com.malguy.eduservice.entity.EduCourseDescription;
import com.malguy.eduservice.entity.chapter.CoursePublishVo;
import com.malguy.eduservice.entity.vo.CourseInfoVo;
import com.malguy.eduservice.mapper.EduCourseMapper;
import com.malguy.eduservice.service.EduCourseDescriptionService;
import com.malguy.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.malguy.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-25
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService descriptionService;

    /**
     * 添加课程信息
     *
     * @param info 与前端交互的课程vo类
     * @return cid 返回课程id,以便前端进行后续操作
     */
    @Override
    public String saveCourseInfo(CourseInfoVo info) {
        //添加->课程表
        //转换CourseInfoVo转换为EduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(info, eduCourse);
        int count = baseMapper.insert(eduCourse);
        if (count <= 0) throw new GuliException(20001, "添加课程信息失败");
        //成功添加则获取添加之后的课程id
        String cid = eduCourse.getId();
        //添加->课程描述表
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(info.getDescription());
        courseDescription.setId(cid);//设置课程id==描述id
        descriptionService.save(courseDescription);
        return cid;
    }

    /**
     * 根据课程id查询
     *
     * @param courseId
     * @return 课程信息vo类
     */
    @Override
    public CourseInfoVo geCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        //查询描述表
        EduCourseDescription eduCourseDescription = descriptionService.getById(courseId);
        //封装vo
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        BeanUtils.copyProperties(eduCourseDescription, courseInfoVo);
        return courseInfoVo;
    }

    /**
     * 修改课程信息
     *
     * @param courseInfoVo
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update <= 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }
        //修改简介表
        EduCourseDescription eduDescription = new EduCourseDescription();
        eduDescription.setId(courseInfoVo.getId());
        eduDescription.setDescription(courseInfoVo.getDescription());
        descriptionService.updateById(eduDescription);
    }

    /**
     * 根据id获取课程信息vo类
     * @param id
     * @return
     */
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo cp = baseMapper.getPublishCourseInfo(id);
        return cp;
    }
}
