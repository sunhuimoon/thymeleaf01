package com.thymeleaf01.controller;

import com.thymeleaf01.mapper.UserMapper;
import com.thymeleaf01.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Controller
public class Index {
    @GetMapping("/index1")
    public String indexHtml(Model model){
        model.addAttribute("name", "Name");
        return "index1";
    }
    @Resource
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        System.out.println(Arrays.toString(cookies));
        for (Cookie cookie : cookies){
            System.out.println(cookie.getName());
            if (cookie.getName().equals("token")){
                //cookie.getValue()写错了，index html文件写错了
                String token = cookie.getValue();
                System.out.println(token);
                User user =userMapper.findByToken(token);
                if (user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        System.out.println("进入首页");
        return "index";
    }
}
