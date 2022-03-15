package com.toozy.bbs.pojo;


import lombok.Data;

@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;




    public String getLogin(){
        return name;
    }

}
