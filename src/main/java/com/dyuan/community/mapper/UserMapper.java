package com.dyuan.community.mapper;

import com.dyuan.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * @author dyuan
 * @date 2020/1/29 19:25
 */
@Mapper
public interface UserMapper {

    // 实现插入语句，将用户信息存入数据库
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified,avatar_url)" +
            " values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    // 查询语句，查找对应token的用户信息
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}

