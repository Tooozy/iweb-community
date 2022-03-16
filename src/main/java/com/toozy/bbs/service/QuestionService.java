package com.toozy.bbs.service;

import com.github.pagehelper.PageHelper;
import com.toozy.bbs.dto.PaginationDTO;
import com.toozy.bbs.dto.QuestionDTO;
import com.toozy.bbs.mapper.QuestionMapper;
import com.toozy.bbs.mapper.UserMapper;
import com.toozy.bbs.pojo.Question;
import com.toozy.bbs.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;


    public List<QuestionDTO> queryAllQuestion() {
        List<Question> questionList = questionMapper.queryAllQuestion();
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }


        return questionDTOList;
    }

    public PaginationDTO queryAllQuestionForPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Question> questionList = questionMapper.queryAllQuestion();
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setQuestions(questionDTOList);
        Integer totalPage = 0;
        Integer total = questionMapper.total();
        if (total%pageSize == 0){
            totalPage = total/pageSize;
        }else {
            totalPage = total/pageSize +1;
        }
        paginationDTO.setPagination(totalPage,pageNo);

        return paginationDTO;
    }
}
