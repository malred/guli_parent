package com.malguy.eduservice.controller;

import com.malguy.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author malguy-wang sir
 * @create ---
 */
@Api(description = "模拟登录")//swagger->给接口提供提示
@RestController
@CrossOrigin
@RequestMapping("/eduservice/user")
public class EduLoginController {
    //登录
    @ApiOperation("登录接口")
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }
    //info
    @ApiOperation("获取用户信息")
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]")
                .data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
