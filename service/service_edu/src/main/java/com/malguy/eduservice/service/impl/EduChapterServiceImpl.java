package com.malguy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.malguy.eduservice.entity.EduChapter;
import com.malguy.eduservice.entity.EduVideo;
import com.malguy.eduservice.entity.chapter.ChapterVo;
import com.malguy.eduservice.entity.chapter.VideoVo;
import com.malguy.eduservice.mapper.EduChapterMapper;
import com.malguy.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.malguy.eduservice.service.EduVideoService;
import com.malguy.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-25
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;

    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterid查章节id(video),如果有就不删除
        //video表中是否有与传来的id相等的,如果有,表示当前章节有video(小节)
        QueryWrapper<EduVideo> wrapper=new QueryWrapper();
        wrapper.eq("chapter_id",chapterId);
        //不需要获取对象,只需要知道有没有
        int count=videoService.count(wrapper);
        if(count>0){//有小节,不删除
            throw new GuliException(20001,"不能删除");
        }else{//删除章节(chapter)
            int res=baseMapper.deleteById(chapterId);
            return res>0; //成功1>0->true,失败0>0->false
        }
    }
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1根据课程id查课程的所有章节,
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);
        //2根据课程id查询课程里的所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);
        //用于最终封装的list
        List<ChapterVo> finalList = new ArrayList();
        //3遍历章节进行封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            //得到每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            //复制 eduChapter -> chapterVo
            BeanUtils.copyProperties(eduChapter, chapterVo);
            //4遍历查询小节集合,封装
            List<VideoVo> videoList=new ArrayList();
            for (int j = 0; j < eduVideoList.size(); j++) {
                //得到每一个小节
                EduVideo eduVideo=eduVideoList.get(j);
                //判断小节的id是否和章节的id一致
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo=new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoList.add(videoVo);
                }
            }
            //chapter最终封装
            chapterVo.setChildren(videoList);
            //放入最终集合
            finalList.add(chapterVo);
        }
        return finalList;
    }
}
