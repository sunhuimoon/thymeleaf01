package com.thymeleaf01.controller;

import com.thymeleaf01.dto.PaginationDTO;
import com.thymeleaf01.dto.QuestionDTO;
import com.thymeleaf01.mapper.QuestionMapper;
import com.thymeleaf01.mapper.UserMapper;
import com.thymeleaf01.model.Question;
import com.thymeleaf01.model.User;
import com.thymeleaf01.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;


@Controller
public class Index {
    @Resource
    private UserMapper userMapper;
    @Resource
    private QuestionService questionService;
    //首页
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page" ,defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "3")Integer size){
        //判断以前是否登陆过
        //获得cookie
        Cookie[] cookies=request.getCookies();
        System.out.println(Arrays.toString(cookies));
        //cookie是否为空
        if(cookies!=null && cookies.length !=0)
            //检查浏览器缓存的cookies在数据库里有没有。
            for (Cookie cookie : cookies){
                System.out.println(cookie.getName());
                if (cookie.getName().equals("token")){
                    //cookie.getValue()写错了，index html文件写错了
                    //在cookie里取token值
                    String token = cookie.getValue();
                    System.out.println(token);
                    User user =userMapper.findByToken(token);
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        //问题展示
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);

        System.out.println("进入首页");
        return "index";
    }
}
