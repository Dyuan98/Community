package com.dyuan.community.mapper;

import com.dyuan.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户提出问题的数据库操作
 * @author dyuan
 * @date 2020/1/30 17:54
 */

@Mapper
public interface QuestionMapper {
    // 实现插入语句，将用户问题信息存入数据库
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag)" +
            " values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create(Question question);

    // 分页查询所有问题
    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size); // 传入非对象时，需要@Param 映射

    // 查询数据库信息行数
    @Select("select count(1) from question")
    Integer count();
}
