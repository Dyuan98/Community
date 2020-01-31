package com.dyuan.community.service;

import com.dyuan.community.dto.PaginationDTO;
import com.dyuan.community.dto.QuestionDTO;
import com.dyuan.community.mapper.QuestionMapper;
import com.dyuan.community.mapper.UserMapper;
import com.dyuan.community.model.Question;
import com.dyuan.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 第一次创建，同时使用QuestionMapper和UserMapper，起到组装的作用，返回带有question所有属性及user对象 返回questionDTOList
 * 第二次更改，同时组装 QuestionDTOList和分页信息，返回带有question所有属性及user对象还有分页信息 返回paginationDto
 * @author dyuan
 * @date 2020/1/30 23:27
 */

@Service
public class QuestionService {
    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        // size*(page-1)
        Integer offset = size*(page-1);  // 每页第一个
        List<Question> questionList = questionMapper.list(offset,size);  // 先获取question中的数据
        List<QuestionDTO> questionDTOList = new ArrayList<>();  // 将所有的问题放在数组中

        PaginationDTO paginationDTO = new PaginationDTO();  // 页面信息对象，包含问题的数组，分页信息
        Integer totalCount = questionMapper.count();  // 获取的数据库行数
        paginationDTO.setPagination(totalCount, page, size);  // 调用方法，处理页面标签显示数目和先后顺序
        if(page<1){
            page = 1;
        }
        if(page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }

        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());   // 通过question获取的用户id在user表中查找对应的信息
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);  //将question对象上的所有属性值copy到questionDTO对应的属性中
            questionDTO.setUser(user);      // 将刚获取的对应的user对象存入questionDTO中
            questionDTOList.add(questionDTO);  // 存入questionDTOList中
        }
        paginationDTO.setQuestions(questionDTOList);  // 将questionDTOList数组存入paginationDTO中
        return paginationDTO;  // 返回页面信息
    }
}
