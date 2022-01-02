package com.example.rozetka_app.controllers.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/signin")
@PreAuthorize("isAnonymous()")
public class LoginController {
    @GetMapping("**")
    private String loginPage(){
        return "login";
    }
}
