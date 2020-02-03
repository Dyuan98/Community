package com.dyuan.community.controller;

import com.dyuan.community.dto.PaginationDTO;
import com.dyuan.community.dto.QuestionDTO;
import com.dyuan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dyuan
 * @date 2020/1/21 23:04
 */
@Controller
public class IndexController {

    @Autowired(required = false)
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size){
                        // page 和 size 是从前端传来的参数，page是页数，这里默认初始值设为1，size是页面问题数这里默认设为5

        PaginationDTO pagination = questionService.list(page, size);  // 接收service层返回的页面信息
        model.addAttribute("pagination",pagination);  // 将获取的question信息列表存入model中,以便在index.html中使用

        return "/index"; }
}
