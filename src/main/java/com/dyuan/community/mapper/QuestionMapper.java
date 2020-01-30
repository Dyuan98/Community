package com.dyuan.community.mapper;

import com.dyuan.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户提出问题的数据库操作
 * @author dyuan
 * @date 2020/1/30 17:54
 */

@Mapper
public interface QuestionMapper {
    // 实现插入语句，将用户问题信息存入数据库
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag)" +
            " values (#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{comment_count},#{view_count},#{like_count},#{tag})")
    void create(Question question);
}
