package com.thymeleaf01.controller;

import com.thymeleaf01.dto.PaginationDTO;
import com.thymeleaf01.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
//首页
@Controller
public class Index {
    @Resource
    private QuestionService questionService;
    //首页
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page" ,defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "3")Integer size){
        //问题展示
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        System.out.println("进入首页");
        return "index";
    }
}
