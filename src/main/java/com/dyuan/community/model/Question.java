package com.dyuan.community.model;

import lombok.Data;

/**
 * 用户提出问题的Javabean
 * @author dyuan
 * @date 2020/1/30 18:04
 */
// 使用lombok的@Data注解自动生成setter,getter方法
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private Long gmt_create;
    private long gmt_modified;
    private Integer creator;
    private Integer view_count;
    private Integer like_count;
    private Integer comment_count;
    private String tag;
}
