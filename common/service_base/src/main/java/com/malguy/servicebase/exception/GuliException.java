package com.malguy.servicebase.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author malguy-wang sir
 * @create ---
 */
//自定义异常类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class  GuliException extends RuntimeException {
    @ApiModelProperty(value = "状态码")
    private Integer code;
    private String msg;
}
