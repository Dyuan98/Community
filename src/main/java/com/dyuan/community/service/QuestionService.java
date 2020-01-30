package com.dyuan.community.service;

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
 * 同时使用QuestionMapper和UserMapper，起到组装的作用
 * @author dyuan
 * @date 2020/1/30 23:27
 */

@Service
public class QuestionService {
    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question> questionList = questionMapper.list();  // 先获取question中的数据
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());   // 通过question获取的用户id在user表中查找对应的信息
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);  //将question对象上的所有属性值copy到questionDTO对应的属性中
            questionDTO.setUser(user);      // 将刚获取的对应的user对象存入questionDTO中
            questionDTOList.add(questionDTO);  // 存入questionDTOList中
        }
        return questionDTOList;
    }
}
