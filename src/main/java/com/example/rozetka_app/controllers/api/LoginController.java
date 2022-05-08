package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.security.SecurityAppUser;
import com.example.rozetka_app.services.ResponseService;
import com.example.rozetka_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ResponseService<Object> responseService;

    @Autowired
    LoginController(
            UserService userService,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            ResponseService<Object> responseService
    ) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.responseService = responseService;
    }

    @PostMapping(path = "api/auth/login")
    private Object login(@RequestBody @Valid AppUser appUser) {
        try{
            UserDetails userDetails = this.userService.loadUserByUsername(appUser.getUsername());

            if (appUser.getPassword().equals(userDetails.getPassword())) {
                responseService.setStatus("ok");

                authenticateUser(userDetails);
            } else {
               throw new UsernameNotFoundException("Invalid password");
            }

        } catch (UsernameNotFoundException e) {
            responseService.setStatus("not ok");
        }

        return this.responseService;
    }

    private void authenticateUser(UserDetails user) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
