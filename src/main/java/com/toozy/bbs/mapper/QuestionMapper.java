package com.toozy.bbs.mapper;

import com.toozy.bbs.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QuestionMapper {
    void createQuestion(Question question);
}
