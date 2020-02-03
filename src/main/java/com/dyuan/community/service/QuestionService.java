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
 * 第三次更改，增加带有UserId参数的list方法，用于返回当前登录用户的提问问题
 * 第四次更改，增加带有questionid参数的方法，用于返回此id问题的具体信息
 * 第五次更改，增加带有Question对象参数的方法，用于判断此问题在数据库是否存在，若存在则进行更新操作，不存在则进行插入操作
 * @author dyuan
 * @date 2020/1/30 23:27
 */

@Service
public class QuestionService {
    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    // 返回的对象是为主页面信息服务的，所有用户的问题
    public PaginationDTO list(Integer page, Integer size) {
        // size*(page-1)
        Integer offset = size*(page-1);  // 每页第一个
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

        List<Question> questionList = questionMapper.list(offset,size);  // 先获取question中的数据
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

    public PaginationDTO list(Integer userAccountId, Integer page, Integer size) {
        // size*(page-1)
        Integer offset = size*(page-1);  // 每页第一个

        List<QuestionDTO> questionDTOList = new ArrayList<>();  // 将所有的问题放在数组中
        PaginationDTO paginationDTO = new PaginationDTO();  // 页面信息对象，包含问题的数组，分页信息
        Integer totalCount = questionMapper.countByUserId(userAccountId);  // 获取的数据库行数
        paginationDTO.setPagination(totalCount, page, size);  // 调用方法，处理页面标签显示数目和先后顺序
        if(page<1){
            page = 1;
        }
        if(page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }

        List<Question> questionList = questionMapper.listByUserId( userAccountId ,offset,size);  // 先获取question中的数据
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

    // 通过问题的id获得某个问题的具体信息，用于展示问题的具体信息页面
    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);  //将question对象上的所有属性值copy到questionDTO对应的属性中
        User user = userMapper.findById(question.getCreator());   // 通过question获取的此问题创作者的userId在user表中查找对应user具体信息
        questionDTO.setUser(user);
        return questionDTO;
    }

    // 用于判断此问题是新创建还是更新编辑进行插入或更新操作
    public void createOrUpdate(Question question) {

        if(question.getId() == null){
            // 插入数据库问题信息
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else{
            // 更新原有问题信息
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }


    }
}
