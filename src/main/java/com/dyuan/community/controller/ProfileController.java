package com.dyuan.community.controller;

import com.dyuan.community.dto.PaginationDTO;
import com.dyuan.community.model.User;
import com.dyuan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户个人信息
 * @author dyuan
 * @date 2020/2/1 17:52
 */
@Controller
public class ProfileController {

    @Autowired(required = false)
    private QuestionService questionService;

    // 下面注释作用：/profile访问profile来调用这个地址{action}动态切换路径
    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action")String action,
                          Model model,
                            @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size){


        // 判断user是否登录，未登陆报错，不能发布问题,并通过user获取发布问题用户的id
        User user = (User)request.getSession().getAttribute("user");
        if (user ==null){
            model.addAttribute("error","用户未登录");
            return "/publish";   // 提交错误，返回提交界面
        }
        if(action.equals("question")){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PaginationDTO pagination = questionService.list(Integer.valueOf(user.getAccountId()), page, size);
        model.addAttribute("pagination",pagination);  // 将获取的question信息列表存入model中,以便在index.html中使用
        return "/profile";
    }
}
