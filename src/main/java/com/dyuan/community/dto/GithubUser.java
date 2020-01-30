package com.dyuan.community.dto;

import lombok.Data;

/**
 * @author dyuan
 * @date 2020/1/22 22:46
 */
@Data
public class GithubUser {
    private String login;
    private Long id;
    private String bio;
    private String avatar_url;
}
