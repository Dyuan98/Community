package com.dyuan.community.dto;

import lombok.Data;

/**
 * @author dyuan
 * @date 2020/1/22 21:38
 * 数据传输模型dto
 */
@Data
public class AccessTokenDTO {
    private String clientId;
    private String clientSecret;
    private String code;
    private String redirectUri;
    private String state;

}
