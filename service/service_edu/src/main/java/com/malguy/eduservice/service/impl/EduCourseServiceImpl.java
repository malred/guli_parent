package com.malguy.eduservice.service.impl;

import com.malguy.eduservice.entity.EduCourse;
import com.malguy.eduservice.entity.EduCourseDescription;
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
    //添加课程信息
    @Override
    public String saveCourseInfo(CourseInfoVo info) {
        //添加->课程表
        //转换CourseInfoVo转换为EduCourse
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(info,eduCourse);
        int count = baseMapper.insert(eduCourse);
        if(count<=0) throw new GuliException(20001,"添加课程信息失败");
        //成功添加则获取添加之后的课程id
        String cid=eduCourse.getId();
        //添加->课程描述表
        EduCourseDescription courseDescription=new EduCourseDescription();
        courseDescription.setDescription(info.getDescription());
        courseDescription.setId(cid);//设置课程id==描述id
        descriptionService.save(courseDescription);
        return cid;
    }
}
