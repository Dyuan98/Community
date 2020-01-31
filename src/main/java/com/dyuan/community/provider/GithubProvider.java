package com.dyuan.community.provider;

import com.alibaba.fastjson.JSON;
import com.dyuan.community.dto.AccessTokenDTO;
import com.dyuan.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author dyuan
 * @date 2020/1/22 21:32
 */
//Component 把当前类初始化到Spring容器的上下文当中
@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token?client_id="+accessTokenDTO.getClientId()
                        + "&client_secret="+accessTokenDTO.getClientSecret()+"&code="+accessTokenDTO.getCode()
                        +"&redirect_uri="+accessTokenDTO.getRedirectUri()+"&state="+accessTokenDTO.getState())
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            String token = str.split("&")[0].split("=")[1];
            System.out.println(str+"---------str-------------");
            System.out.println(token+"--------token----------");
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        System.out.println(accessToken+"----accessToken------");
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
//            System.out.println("------------>"+str);
            GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
            return githubUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
