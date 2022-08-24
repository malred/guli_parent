package com.malguy.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author malguy-wang sir
 * @create ---
 */
@Data
public class SubjectData {
    @ExcelProperty(index = 0) //一级分类
    private String oneSubjectName;
    @ExcelProperty(index = 1) //二级分类
    private String twoSubjectName;
}
