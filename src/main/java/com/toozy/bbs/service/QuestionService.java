package com.toozy.bbs.service;

import com.github.pagehelper.PageHelper;
import com.toozy.bbs.dto.PaginationDTO;
import com.toozy.bbs.dto.QuestionDTO;
import com.toozy.bbs.mapper.QuestionMapper;
import com.toozy.bbs.mapper.UserMapper;
import com.toozy.bbs.pojo.Question;
import com.toozy.bbs.pojo.QuestionExample;
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


//    public List<QuestionDTO> queryAllQuestion() {
//        List<Question> questionList = questionMapper.queryAllQuestion();
//        List<QuestionDTO> questionDTOList = new ArrayList<>();
//
//        for (Question question : questionList) {
//            User user = userMapper.findById(question.getCreator());
//            QuestionDTO questionDTO = new QuestionDTO();
//            BeanUtils.copyProperties(question,questionDTO);
//            questionDTO.setUser(user);
//            questionDTOList.add(questionDTO);
//        }
//
//
//        return questionDTOList;
//    }

    public PaginationDTO queryAllQuestionForPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Question> questionList = questionMapper.selectByExample(new QuestionExample());


        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList) {


            User user = userMapper.selectByPrimaryKey(question.getCreator());



            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setQuestions(questionDTOList);
        Integer totalPage = 0;
        Integer total = (int)questionMapper.countByExample(new QuestionExample());
        if (total%pageSize == 0){
            totalPage = total/pageSize;
        }else {
            totalPage = total/pageSize +1;
        }
        paginationDTO.setPagination(totalPage,pageNo);

        return paginationDTO;
    }

    public PaginationDTO queryAllQuestionForId(Integer id, Integer page, Integer size) {
        PageHelper.startPage(page,size);

//        List<Question> questionList = questionMapper.queryAllQuestionByCreatorId(id);

        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(id);
        List<Question> questionList = questionMapper.selectByExample(example);



        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList) {


            User user = userMapper.selectByPrimaryKey(question.getCreator());



            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setQuestions(questionDTOList);
        Integer totalPage = 0;
//        Integer total = questionMapper.totalByCreatorId(id);
        Integer total = questionList.size();
        if (total%size == 0){
            totalPage = total/size;
        }else {
            totalPage = total/size +1;
        }
        paginationDTO.setPagination(totalPage,page);


        return paginationDTO;


    }

    public QuestionDTO getQuestionDTO(Integer id) {


        Question question = questionMapper.selectByPrimaryKey(id);



        User user = userMapper.selectByPrimaryKey(question.getCreator());



        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);


        return questionDTO;
    }

    public void doCreateOrUpdate(Question question) {

        if (question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);

        }else {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());


            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion, example);
        }

    }
}
