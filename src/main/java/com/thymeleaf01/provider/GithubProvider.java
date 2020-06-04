package com.thymeleaf01.provider;

import com.alibaba.fastjson.JSON;
import com.thymeleaf01.dto.AccessTokenDTO;
import com.thymeleaf01.dto.GithubUser;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        //这里把accessTokenDTO重新new了，导致错误
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token?client_id="+accessTokenDTO.getClient_id()+"&client_secret="+accessTokenDTO.getClient_secret()+"&code="+accessTokenDTO.getCode()+"&redirect_uri="+accessTokenDTO.getRedirect_uri()+"&state="+accessTokenDTO.getState())
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split1 = string.split("&");
            String[] token = split1[0].split("=");
          //  System.out.println(token[1]);
            return token[1];
        } catch (IOException e) {
            System.out.println("取token异常");
        }
        return null;
    }
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        System.out.println("进入到提取user信息方法");
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        System.out.println("request= " +request);
        String re = "https://api.github.com/user?access_token=" + accessToken;
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(re);
        CloseableHttpResponse response2 = null;
        try {
            // 由客户端执行(发送)Get请求
            response2 = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response2.getEntity();
            System.out.println("响应状态为:" + response2.getStatusLine());

            Response response = client.newCall(request).execute();
            System.out.println("response= " +response);
            String string = Objects.requireNonNull(response.body()).string();
            System.out.println("string= " +string);
            GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
            System.out.println("githubUser= " +githubUser);
//            System.out.println("名称  "+githubUser.getName()+"  ID   "+githubUser.getId()+"  描述" +githubUser.getBio());
            return githubUser;
        } catch (IOException e) {
            System.out.println("取user异常");
        }
        return null;
    }
}
