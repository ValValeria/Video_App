package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.annotations.AdminOnly;
import com.example.rozetka_app.annotations.EntityMustExists;
import com.example.rozetka_app.annotations.SecurityPermissionsContext;
import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.repositories.LikeRepository;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.repositories.VideoRepository;
import com.example.rozetka_app.services.ResponseService;
import com.example.rozetka_app.statuscodes.DefinedErrors;
import com.example.rozetka_app.statuscodes.DefinedStatusCodes;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(path = "/api/user")
public class UserController {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final ResponseService<Object> responseService;
    private final LikeRepository likeRepository;

    @Autowired
    public UserController(
        UserRepository userRepository,
        VideoRepository videoRepository,
        ResponseService<Object> responseService,
        LikeRepository likeRepository
    ) {
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
        this.responseService = responseService;
        this.likeRepository = likeRepository;
    }

    @GetMapping("")
    private ResponseService<Object> getUsersList(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        Page<AppUser> users = this.userRepository.findAll(PageRequest.of(page, size));
        this.responseService.setData(
                Map.of(
                        "all_pages", users.getTotalPages(),
                        "results", users.get().toArray()
                )
        );

        return this.responseService;
    }
}
