package com.malguy.eduservice.entity.subject;

import lombok.Data;

import java.io.Serializable;

/**
 * @author malguy-wang sir
 * @create ---
 */
//二级分类
@Data //必须提供,不然BeanUtils无法获取属性
public class TwoSubject {
    private String id;
    private String title;
}
