package com.security.myss.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecController {

    @ResponseBody
    @GetMapping("/testlogin")
    public String testLogin(Authentication authentication){
        System.err.println(authentication.getPrincipal());

        return "세션 정보 확인";
    }

    @GetMapping("/")
    public String index(){
        return "home";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }
    @GetMapping("/manager")
    public String manager(){
        return "manager";
    }
    @GetMapping("/user")
    public String user(){
        return "user";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }


}
