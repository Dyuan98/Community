package com.dyuan.community.controller;

import com.dyuan.community.mapper.QuestionMapper;
import com.dyuan.community.mapper.UserMapper;
import com.dyuan.community.model.Question;
import com.dyuan.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 发布问题模块
 * @author dyuan
 * @date 2020/1/30 14:31
 */


@Controller
public class PublishController{
    //注入
    @Autowired(required = false)
    private QuestionMapper questionMapper;
    @Autowired(required = false)
    private UserMapper userMapper;

    // 返回到渲染publish界面
    @GetMapping("/publish")
    public String publish(){
        return "/publish";
    }

    // 处理publish界面的信息
    @PostMapping("/publish")
    public String doPublish(
            // 接收publish界面用户输入的信息
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            HttpServletRequest request,
            Model model){

        // 为了能将输入回显到页面上，将获取的用户输入信息放入model里面
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null || title.equals("")){
            model.addAttribute("error","标题不能为空");
            return "/publish";   // 提交错误，返回提交界面
        }
        if (description==null || description.equals("")){
            model.addAttribute("error","问题补充不能为空");
            return "/publish";   // 提交错误，返回提交界面
        }
        if (tag==null || tag.equals("")){
            model.addAttribute("error","标签不能为空");
            return "/publish";   // 提交错误，返回提交界面
        }

        // 判断user是否登录，未登陆报错，不能发布问题,并通过user获取发布问题用户的id
        User user = null;
        Cookie[] cookies = request.getCookies(); // 获取请求得到的cookie数组

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if (user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        if (user ==null){
            model.addAttribute("error","用户未登录");
            return "/publish";   // 提交错误，返回提交界面
        }

        // 实例化Question对象，将信息通过参数写入对象中
        Question question = new Question();
        question.setTitle(title);               // 问题标题
        question.setDescription(description);   // 问题描述
        question.setTag(tag);                   // 问题标签
        question.setCreator(user.getId());      // 用户ID
        question.setGmt_create(System.currentTimeMillis());  // 问题创建时间
        question.setGmt_modified(question.getGmt_create());  // 问题修改时间

        questionMapper.create(question);
        return "redirect:index";
    }
}
