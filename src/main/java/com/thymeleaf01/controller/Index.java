package com.thymeleaf01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class Index {
    @GetMapping("/index1")
    public String indexHtml(Model model){
        model.addAttribute("name", "Name");
        return "index1";
    }
    @GetMapping("/index")
    public String bb(){
        System.out.println("进入首页");
        return "index";
    }
}
