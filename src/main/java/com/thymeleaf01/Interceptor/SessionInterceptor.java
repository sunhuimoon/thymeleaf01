package com.thymeleaf01.Interceptor;

import com.thymeleaf01.mapper.UserMapper;
import com.thymeleaf01.model.User;
import com.thymeleaf01.model.UserExample;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
//判断以前是否登陆过
@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Resource
    private UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断以前是否登陆过
        //获得cookie
        Cookie[] cookies=request.getCookies();
//        System.out.println("启动拦截器");
        //cookie是否为空
        if(cookies!=null && cookies.length !=0)
            //检查浏览器缓存的cookies在数据库里有没有。
            for (Cookie cookie : cookies){
//                System.out.println(cookie.getName());
                if (cookie.getName().equals("token")){
                    //cookie.getValue()写错了，index html文件写错了
                    //在cookie里取token值
                    String token = cookie.getValue();
                    System.out.println("浏览器里里的token=  "+token);
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users =userMapper.selectByExample(userExample);
                    if (users.size() !=0){
                        request.getSession().setAttribute("user",users.get(0));
                    }
                    break;
                }
            }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
