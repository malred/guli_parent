package com.malguy.servicebase.exception;
import com.malguy.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //指定出现什么异常会进行处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e ){
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error().message("warning!!!全局异常处理");
    }
    //特定异常处理--用得少
    @ExceptionHandler(java.sql.SQLIntegrityConstraintViolationException.class)
    @ResponseBody //返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error().message("不能有重复姓名!!!");
    }
}