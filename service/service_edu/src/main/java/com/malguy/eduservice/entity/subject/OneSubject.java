package com.malguy.eduservice.entity.subject;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author malguy-wang sir
 * @create ---
 */
//一级分类
@Data
public class OneSubject {
    private String id;
    private String title;
    //一级分类下有多个二级分类
    private List<TwoSubject> children=new ArrayList<TwoSubject>();
}
