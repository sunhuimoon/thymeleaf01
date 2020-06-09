package com.thymeleaf01.controller;

import com.thymeleaf01.mapper.QuestionMapper;
import com.thymeleaf01.mapper.UserMapper;
import com.thymeleaf01.model.Question;
import com.thymeleaf01.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPulish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            HttpServletRequest request,
            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title == null || title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null || description==""){
            model.addAttribute("error","问题描述不能为空");
            return "publish";
        }
        if (tag == null || tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        User user =null;
        Cookie[] cookies = request.getCookies();
        System.out.println("zzzzzzzzzzzzzzzzzz");
        System.out.println(cookies);
        if (cookies !=null){
            for (Cookie cookie :cookies){
                System.out.println(cookie);
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user= userMapper.findByToken(token);
                    System.out.println(user);
                    if (user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        System.out.println(user+"    user222");
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreat(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreat());
        questionMapper.create(question);
        return "redirect:/";
    }
}
