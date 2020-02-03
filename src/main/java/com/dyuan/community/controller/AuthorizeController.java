package com.dyuan.community.controller;


import com.dyuan.community.dto.AccessTokenDTO;
import com.dyuan.community.dto.GithubUser;
import com.dyuan.community.mapper.UserMapper;
import com.dyuan.community.model.User;
import com.dyuan.community.provider.GithubProvider;
import com.dyuan.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 实现登陆模块授权
 * 和退出登录
 * @author dyuan
 * @date 2020/1/22 21:19
 */
@Controller
public class AuthorizeController {
    @Autowired    // 把Spring容器里实例化的实例加载到当前上下文
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;

    //读取配置文件中的值
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    @GetMapping("/callback")
    public String callback(@RequestParam(name ="code")String code,
                           @RequestParam(name ="state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        // 实例化AccessTokenDTO对象，传入对应值
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClientId(clientId);
        accessTokenDTO.setClientSecret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirectUri(redirectUri);
        accessTokenDTO.setState(state);
        // 获取accessToken
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        // 获取GithubUser的信息
        GithubUser githubUser = githubProvider.getUser(accessToken);
        // 判断用户是否登陆成功
        if (githubUser!=null && githubUser.getId()!= null){
            User user = new User();
            String token = UUID.randomUUID().toString(); // 生成一个唯一的token
            user.setToken(token);
            user.setName(githubUser.getLogin());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);  // 判断此用户是否登陆过，若登陆过，则只需更新token即可，若第一次登陆，学在数据库中插入user信息
            // 登陆成功，将用户信息存入数据库
            response.addCookie(new Cookie("token", token));
//            request.getSession().setAttribute("user",githubUser);
        }else{
            // 登陆失败，重新登陆
                    System.out.println("失败-------------------");
        }
        return "redirect:/";
    }

    // 退出登录
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);  //将token设为null
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
