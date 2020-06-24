package com.thymeleaf01.controller;

import com.thymeleaf01.dto.PaginationDTO;
import com.thymeleaf01.mapper.UserMapper;
import com.thymeleaf01.model.User;
import com.thymeleaf01.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
//我的问题页
@Controller
public class ProfileController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page" ,defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "3")Integer size){
        User user =(User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination",paginationDTO);

        return "profile";
    }

}
