package com.security.myss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecController {
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
