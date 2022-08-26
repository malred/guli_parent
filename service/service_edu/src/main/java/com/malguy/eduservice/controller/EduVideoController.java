package com.malguy.eduservice.controller;


import com.malguy.commonutils.R;
import com.malguy.eduservice.entity.EduVideo;
import com.malguy.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-25
 */
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;
    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        System.out.println(eduVideo);
        videoService.save(eduVideo);
        return R.ok();
    }
    //删除小节
    //todo 删除同时要删视频
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        boolean flag = videoService.removeById(id);
        if(flag) return R.ok();
        else return R.error();
    }
    //更新小节

    //获取小节
}

