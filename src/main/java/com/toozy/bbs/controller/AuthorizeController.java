package com.toozy.bbs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.toozy.bbs.GithubProvider.GithubProvider;
import com.toozy.bbs.dto.AccessTokenDTO;
import com.toozy.bbs.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) throws JsonProcessingException {


        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setClient_id("5005344c8fa154a8d9ce");
        accessTokenDTO.setClient_secret("4b2966c6810f2d3faaf6078ac34247755219e16d");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);


        System.out.println(user.getId());


        return "index";
    }
}
