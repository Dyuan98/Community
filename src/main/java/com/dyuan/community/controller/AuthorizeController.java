package com.dyuan.community.controller;

import com.dyuan.community.dto.AccessTokenDTO;
import com.dyuan.community.dto.GithubUser;
import com.dyuan.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/callback")
    public String callback(@RequestParam(name ="code")String code,
                           @RequestParam(name ="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getUser());
        return "index";  //登录成功后，返回index页面
    }
}
