package com.toozy.bbs.controller;

import com.toozy.bbs.dto.PaginationDTO;
import com.toozy.bbs.mapper.UserMapper;
import com.toozy.bbs.pojo.User;
import com.toozy.bbs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action", value = "") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value= "page",defaultValue="1")Integer page,
                          @RequestParam(value= "size",defaultValue="8")Integer size) {

        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }


        if ("questions".equals(action)) {
                model.addAttribute("section", "questions");
                model.addAttribute("sectionName", "我的提问");
        } else if ("replies".equals(action)) {
                model.addAttribute("section", "replies");
                model.addAttribute("sectionName", "最新回复");
        }


        PaginationDTO paginationDTO = questionService.queryAllQuestionForId(user.getId(), page, size);
        System.out.println(paginationDTO);
        model.addAttribute("pagination",paginationDTO);

        return "profile";

    }
}
