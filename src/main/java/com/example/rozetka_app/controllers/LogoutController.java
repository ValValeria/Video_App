package com.example.rozetka_app.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@PreAuthorize("isAuthenticated()")
public class LogoutController {
    @GetMapping(value = "/logout")
    private String index(HttpServletRequest request) throws ServletException {
        request.logout();

        return "redirect:/";
    }
}
