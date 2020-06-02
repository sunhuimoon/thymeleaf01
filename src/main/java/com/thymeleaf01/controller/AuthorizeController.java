package com.thymeleaf01.controller;

import com.thymeleaf01.dto.AccessTokenDTO;
import com.thymeleaf01.dto.GithubUser;
import com.thymeleaf01.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    public GithubProvider githubProvider;

    @Value("${github.Client_id}")
    private String clientId;
    @Value("${github.Client_secret}")
    private String clientSecret;
    @Value("${github.Redirect_uri}")
    private String redirectUri;

    //这里忘记加"/callback"
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        System.out.println("进入callback");
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        return "index";
    }
}
