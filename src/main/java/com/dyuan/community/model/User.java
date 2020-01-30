package com.dyuan.community.model;

import lombok.Data;

/**
 * @author dyuan
 * @date 2020/1/29 19:42
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
