package com.toozy.bbs.mapper;


import com.toozy.bbs.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    void insert(User user);
}
