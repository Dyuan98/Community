package com.dyuan.community.controller;

import com.dyuan.community.dto.QuestionDTO;
import com.dyuan.community.mapper.QuestionMapper;
import com.dyuan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * 展示主页面点击某个问题页面
 * @author dyuan
 * @date 2020/2/2 14:49
 */

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String profile(@PathVariable(name = "id")Integer id,
                            Model model) {
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("question",question);  // 将获取的question信息列表存入model中,以便在question.html中使用
        return "question";
    }
}


