package com.malguy.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.malguy.commonutils.R;
import com.malguy.eduservice.entity.EduTeacher;
import com.malguy.eduservice.entity.vo.TeacherQuery;
import com.malguy.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-22
 */
@CrossOrigin
@Api(description = "讲师管理")//swagger->给接口提供提示
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    //注入service
    @Autowired
    private EduTeacherService teacherService;

    //1,查询所有教师信息
    //rest风格--不同操作用不同请求
    @CrossOrigin
    @ApiOperation(value = "所有讲师列表")//swagger->给方法添加提示
    @GetMapping("findAll")
    public R findAllTeacher() {
        //调用sevice里的方法
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    //2,逻辑删除讲师
    @ApiOperation(value = "逻辑删除讲师")
    @CrossOrigin
    @DeleteMapping("{id}") //通过路径传递id值
    public R removeTeacher(
            @ApiParam(name = "id", value = "讲师id", required = true) //swagger->给参数添加提示
            @PathVariable String id) {//获取路径变量
        boolean flag = teacherService.removeById(id);
        if (flag) return R.ok();
        else return R.error();
    }

    //3,分页查询讲师 current当前页,limit每页记录数
    @ApiOperation(value = "分页查询讲师")
    @CrossOrigin
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        //创建page对象->1代表当前页,3代表每页多少条
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //调用方法实现分页
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//每页数据的集合
//        Map map=new HashMap();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);
        return R.ok().data("total", total).data("rows", records);
    }

    //条件查询带分页
    //@RequestBody需要在post请求里才生效
    @ApiOperation(value = "条件分页查询讲师")
    @CrossOrigin
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  //没加@PathVariable,就是非必须参数
                                  //@RequestBody可以把接收到的json数据封装到对应的对象里
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建page对象
        Page<EduTeacher> teacherPage = new Page(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //判断是否为空
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //非空条件就拼接
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            //eq 等于
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            //ge 大于等于 (第一个参数是数据库里的字段名)
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            //le 小于等于
            wrapper.le("gmt_create", end);
        }
        //排序,让新加的在前排
        wrapper.orderByDesc("gmt_create");
        //调用方法,实现条件查询
        teacherService.page(teacherPage, wrapper);
        long total = teacherPage.getTotal();//总记录数
        List<EduTeacher> records = teacherPage.getRecords();//每页数据的集合
        return R.ok().data("total", total).data("rows", records);
    }

    //添加讲师
    @ApiOperation(value = "添加讲师方法")
    @CrossOrigin
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody(required = true) EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) return R.ok();
        else return R.error();
    }

    //根据讲师id进行查询
    @ApiOperation(value = "根据id查询讲师")
    @CrossOrigin
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }
    //讲师修改
    @ApiOperation(value = "修改讲师")
    @CrossOrigin
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag) return R.ok();
        else return R.error();
    }
}

