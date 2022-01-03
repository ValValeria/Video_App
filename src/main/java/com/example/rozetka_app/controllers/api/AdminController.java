package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.annotations.AdminOnly;
import com.example.rozetka_app.models.User;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/admin")
public class AdminController {
    private final UserRepository userRepository;
    private final ResponseService<List<User>> responseService;

    @Autowired
    public AdminController(
            UserRepository userRepository,
            ResponseService<List<User>> responseService
    ) {
        this.userRepository = userRepository;
        this.responseService = responseService;
    }

    @AdminOnly
    @GetMapping(path = "/users")
    private ResponseService<List<User>> getUsers() {
        this.responseService.setData(Map.of("results", userRepository.findAll()));

        return this.responseService;
    }
}
