package com.dyuan.community.service;

import com.dyuan.community.mapper.UserMapper;
import com.dyuan.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用于客户登录判断曾经是否登陆过
 * @author dyuan
 * @date 2020/2/3 13:17
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if(dbUser == null){
            // 插入数据库新用户信息
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            // 更新原有用户信息
//            dbUser.setGmtCreate(System.currentTimeMillis());
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }

    }
}
