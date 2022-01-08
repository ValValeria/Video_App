package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signin")
@PreAuthorize("isAnonymous()")
public class LoginController {
    private final ResponseService<AppUser> responseService;

    @Autowired
    public LoginController(
       ResponseService<AppUser> responseService
    ) {
       this.responseService = responseService;
    }

    @GetMapping("")
    private Object login() {
      return this.responseService;
    }
}
