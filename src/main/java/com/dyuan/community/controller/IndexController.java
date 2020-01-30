package com.dyuan.community.controller;

import com.dyuan.community.dto.QuestionDTO;
import com.dyuan.community.mapper.QuestionMapper;
import com.dyuan.community.mapper.UserMapper;
import com.dyuan.community.model.User;
import com.dyuan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author dyuan
 * @date 2020/1/21 23:04
 */
@Controller
public class IndexController {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private QuestionService questionService;

    @GetMapping("/index")
    public String index(HttpServletRequest request,
                        Model model){

        Cookie[] cookies = request.getCookies(); // 获取请求得到的cookie数组
        if(cookies!=null&&cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        List<QuestionDTO> questionList = questionService.list();
        model.addAttribute("questionList",questionList);  // 将获取的question信息列表存入model中,以便在index.html中使用
        return "/index"; }
}
