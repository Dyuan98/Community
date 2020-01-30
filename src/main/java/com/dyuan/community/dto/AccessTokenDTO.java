package com.dyuan.community.dto;

import lombok.Data;

/**
 * @author dyuan
 * @date 2020/1/22 21:38
 * 数据传输模型dto
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
