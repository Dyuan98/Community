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
    private Long gmtCreate;
    private long gmtModified;
    private String creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private String tag;
}
