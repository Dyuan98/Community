package com.dyuan.community.controller;


import com.dyuan.community.dto.AccessTokenDTO;
import com.dyuan.community.dto.GithubUser;
import com.dyuan.community.mapper.UserMapper;
import com.dyuan.community.model.User;
import com.dyuan.community.provider.GithubProvider;
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
 * @author dyuan
 * @date 2020/1/22 21:19
 */
@Controller
public class AuthorizeController {
    @Autowired    // 把Spring容器里实例化的实例加载到当前上下文
    private GithubProvider githubProvider;
    //读取配置文件中的值
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired(required = false)
    private UserMapper userMapper;  // h2数据库存入用户登录信息所用的javabean
    @GetMapping("/callback")
    public String callback(@RequestParam(name ="code")String code,
                           @RequestParam(name ="state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        // 实例化AccessTokenDTO对象，传入对应值
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        // 获取accessToken
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        // 获取GithubUser的信息
        GithubUser githubUser = githubProvider.getUser(accessToken);
        // 判断用户是否登陆成功
        if (githubUser!=null){
            User user = new User();
            String token = UUID.randomUUID().toString(); // 生成一个唯一的token
            user.setToken(token);
            user.setName(githubUser.getLogin());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            // 登陆成功，将用户信息存入数据库
            userMapper.insert(user);
            // 将生成的token放在cookie里
            response.addCookie(new Cookie("token", token));
            // System.out.println(githubUser.getLogin());
//            request.getSession().setAttribute("user",githubUser);
        }else{
            // 登陆失败，重新登陆
                    System.out.println("失败-------------------");
        }
        return "redirect:/";


    }
}
