package com.example.rozetka_app.controllers.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@PreAuthorize("isAuthenticated()")
public class LogoutController {
    @GetMapping(value = "/api/logout")
    private String index(HttpServletRequest request) throws ServletException {
        request.logout();

        return "redirect:/";
    }
}
