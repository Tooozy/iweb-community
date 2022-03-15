package com.toozy.bbs.mapper;


import com.toozy.bbs.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    void insertUser(User user);

//    @Select("select * from BBS.user where token = #{token};")
    User findByToken(@Param("token") String token);


}
