package com.dyuan.community.dto;

import com.dyuan.community.model.User;
import lombok.Data;

/**
 * @author dyuan
 * @date 2020/1/30 23:23
 */
@Data
public class QuestionDTO {
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
    private User user;
}
