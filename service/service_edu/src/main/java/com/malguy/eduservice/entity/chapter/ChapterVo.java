package com.malguy.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author malguy-wang sir
 * @create ---
 */
@Data
public class ChapterVo {
    private String id;
    private String title;
    //大节里有小节
    private List<VideoVo> children=new ArrayList();
}
