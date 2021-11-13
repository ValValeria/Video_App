package com.example.rozetka_app.controllers.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("/auth")
public class AuthController {
    @GetMapping("/login")
    private String login(){
        return "login";
    }

    @PostMapping("/login")
    private void handleLogin(){

    }

    @GetMapping("/signup")
    private String signup(){
        return "signup";
    }
}
