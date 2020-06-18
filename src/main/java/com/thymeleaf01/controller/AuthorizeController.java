package com.thymeleaf01.controller;

import com.thymeleaf01.dto.AccessTokenDTO;
import com.thymeleaf01.dto.GithubUser;
import com.thymeleaf01.model.User;
import com.thymeleaf01.provider.GithubProvider;
import com.thymeleaf01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    public GithubProvider githubProvider;
    @Resource
    private UserService userService;

    @Value("${github.Client_id}")
    private String clientId;
    @Value("${github.Client_secret}")
    private String clientSecret;
    @Value("${github.Redirect_uri}")
    private String redirectUri;

    //这里忘记加"/callback"
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        System.out.println("进入callback");
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println("accessToken= "+ accessToken);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println("if");
        if (githubUser != null) {
            User user=new User();
            String token =UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",token));
            //登陆成功，写cookie和session
//            request.getSession().setAttribute("githubUser", githubUser);
        } else {
//            登陆失败
            System.out.println("登陆失败");
        }
        return "redirect:/";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
