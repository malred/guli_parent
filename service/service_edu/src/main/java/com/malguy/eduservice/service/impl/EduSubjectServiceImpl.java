package com.malguy.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.malguy.eduservice.entity.EduSubject;
import com.malguy.eduservice.entity.excel.SubjectData;
import com.malguy.eduservice.entity.subject.OneSubject;
import com.malguy.eduservice.entity.subject.TwoSubject;
import com.malguy.eduservice.listeners.SubjectExcelListener;
import com.malguy.eduservice.mapper.EduSubjectMapper;
import com.malguy.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-24
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有一级分类 parentid=0
        QueryWrapper<EduSubject> wrapperOne=new QueryWrapper<>();
        wrapperOne.eq("parent_id",0);
        List<EduSubject> oneSubjectList= baseMapper.selectList(wrapperOne);
        //查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo=new QueryWrapper<>();
        wrapperOne.ne("parent_id",0);//不等于
        List<EduSubject> twoSubjectList= baseMapper.selectList(wrapperTwo);
        //存储最终封装数据的集合
        List<OneSubject> finalSubjectList=new ArrayList<OneSubject>();
        //封装一级分类->遍历->封装
        for (int i = 0; i < oneSubjectList.size(); i++) {
            //封装二级分类
            EduSubject eduSubject = oneSubjectList.get(i);//一级分类的数据
            //eduSubject里的值放到OneSubject对象里
            OneSubject oneSubject=new OneSubject();//一级分类
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            //把一个对象的值,copy到另一个 eduSubject->oneSubject
            BeanUtils.copyProperties(eduSubject,oneSubject);
            //多个OneSubject放到finalSubjectList
            finalSubjectList.add(oneSubject);
            //一级分类里循环遍历二级分类,并根据pid放入一级分类
            List<TwoSubject> twoSubjectFinal=new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                //获取每个二级分类
                EduSubject tSubject=twoSubjectList.get(j);
                //封装
                if(tSubject.getParentId().equals(eduSubject.getId())){
                    //复制值,然后放入oneSubject
                    TwoSubject twoSubject=new TwoSubject();//二级分类
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoSubjectFinal.add(twoSubject);
                }
            }
            //所有二级分类放到一级分类里
            oneSubject.setChildren(twoSubjectFinal);
        }
        return finalSubjectList;
    }

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in=file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService))
                    .sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
