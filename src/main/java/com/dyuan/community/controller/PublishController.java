package com.dyuan.community.controller;

import com.dyuan.community.dto.QuestionDTO;
import com.dyuan.community.mapper.QuestionMapper;
import com.dyuan.community.model.Question;
import com.dyuan.community.model.User;
import com.dyuan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 发布及编辑问题模块
 * @author dyuan
 * @date 2020/1/30 14:31
 */


@Controller
public class PublishController{

    @Autowired(required = false)
    private QuestionService questionService;

    // 编辑已经发布过的问题
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        // 将原问题内容回显到页面上
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());  //获取问题的唯一标识
        return "publish";
    }

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
            @RequestParam(value = "id", required = false) Integer id,
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
        User user = (User)request.getSession().getAttribute("user");
        if (user ==null){
            model.addAttribute("error","用户未登录");
            return "/publish";   // 提交错误，返回提交界面
        }

        // 实例化Question对象，将信息通过参数写入对象中
        Question question = new Question();
        question.setTitle(title);               // 问题标题
        question.setDescription(description);   // 问题描述
        question.setTag(tag);                   // 问题标签
        question.setCreator(user.getAccountId());      // 用户gitHub账号的accountId
        question.setGmtCreate(System.currentTimeMillis());  // 问题创建时间
        question.setGmtModified(question.getGmtCreate());  // 问题修改时间
        question.setId(id);  // 保存问题的id，用此id查找数据库中的对应问题
        questionService.createOrUpdate(question);  // 判断该问题是否存在，若存在则进行更新操作，若不存在则进行插入操作
        return "redirect:/";
    }
}
