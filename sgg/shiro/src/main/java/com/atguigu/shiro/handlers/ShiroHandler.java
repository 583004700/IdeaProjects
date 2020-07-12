package com.atguigu.shiro.handlers;

import com.atguigu.shiro.services.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequestMapping("/shiro")
@Controller
public class ShiroHandler {
    @Autowired
    private ShiroService shiroService;

    @RequestMapping("/testShiroAnnotation")
    public String testShiroAnnotation(HttpSession session){
        shiroService.testMethod();
        session.setAttribute("key",12345);
        return "redirect:/list.jsp";
    }

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,@RequestParam("password") String password){
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            token.setRememberMe(true);
            System.out.println("1.token:"+token.hashCode());
            try {
                currentUser.login(token);
            }catch (AuthenticationException e){
                System.out.println("登录失败"+e.getMessage());
            }
        }
        return "redirect:/list.jsp";
    }
}
