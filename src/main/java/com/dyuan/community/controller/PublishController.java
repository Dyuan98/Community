package com.dyuan.community.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 发布问题模块
 * @author dyuan
 * @date 2020/1/30 14:31
 */
@Controller
public class PublishController{
    @GetMapping("/publish")
    public String publish(){
        return "/publish";
    }
}
