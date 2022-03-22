package com.toozy.bbs.controller;

import com.toozy.bbs.dto.PaginationDTO;
import com.toozy.bbs.mapper.UserMapper;
import com.toozy.bbs.pojo.User;
import com.toozy.bbs.pojo.UserExample;
import com.toozy.bbs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(value= "page",defaultValue="1")Integer page,
                        @RequestParam(value= "size",defaultValue="8")Integer size
    ){

        PaginationDTO paginationDTO= questionService.queryAllQuestionForPage(page, size);
        model.addAttribute("pagination",paginationDTO);

        Cookie[] cookies = request.getCookies();
        if (cookies != null ){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    UserExample example = new UserExample();
                    example.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(example);

                    if (users.size() != 0){
                        request.getSession().setAttribute("user",users.get(0));
                    }
                    break;
                }
            }
        } else {
            return "index";
        }



        return "index";
    }


}
