package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
@PreAuthorize("isAuthenticated()")
public class LogoutController {
    private final ResponseService<AppUser> responseService;

    @Autowired
    public LogoutController(ResponseService<AppUser> responseService) {
        this.responseService = responseService;
    }

    @GetMapping(value = "/api/logout")
    private Object index(HttpServletRequest request) throws ServletException {
        request.logout();

        responseService.setStatus("ok");

        return this.responseService;
    }
}
