package com.dyuan.community.controller;

import com.dyuan.community.mapper.UserMapper;
import com.dyuan.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author dyuan
 * @date 2020/1/21 23:04
 */
@Controller
public class IndexController {
    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/index")
    public String index( HttpServletRequest request){
        Cookie[] cookies = request.getCookies(); // 获取请求得到的cookie数组
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if (user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        return "/index"; }
}
