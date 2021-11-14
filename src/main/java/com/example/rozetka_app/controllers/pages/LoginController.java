package com.example.rozetka_app.controllers.pages;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/login")
@PreAuthorize("isAnonymous()")
public class LoginController {
    @GetMapping()
    private String getLoginPage(){
        return "login";
    }
}
