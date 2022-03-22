package com.toozy.bbs.mapper;

import com.toozy.bbs.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {
    void createQuestion(Question question);

    List<Question> queryAllQuestion();

    Integer total();

    List<Question> queryAllQuestionByCreatorId(@Param("creator") Integer id);

    Integer totalByCreatorId(@Param("creator") Integer id);

    Question findQuestionById(@Param("id")Integer id);

    void updateQuestion(Question question);
}
