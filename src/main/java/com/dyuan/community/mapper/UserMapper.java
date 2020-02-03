package com.dyuan.community.mapper;

import com.dyuan.community.model.User;
import org.apache.ibatis.annotations.*;


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

    // 查询语句，通过id查找信息
    @Select("select * from user where account_id = #{accountId}")
    User findById(@Param("accountId")String accountId);

    // 查询语句，判断此accountId在数据库是否能查到
    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId")String accountId);

    // 更新语句，更新用户登录时的信息
    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl} where account_id =#{accountId}")
    void update(User dbUser);
}

