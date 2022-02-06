package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.EnumMap;

@Controller
@RequestMapping(path = "/api")
public class UserController {
    private final UserRepository userRepository;
    private final ResponseService<Object> responseService;

    @Autowired
    public UserController(
        UserRepository userRepository,
        ResponseService<Object> responseService
    ) {
        this.userRepository = userRepository;
        this.responseService = responseService;
    }

    @GetMapping("users")
    private ResponseService<Object> getUsersList(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        Page<AppUser> users = this.userRepository.findAll(PageRequest.of(page, size));

        EnumMap<ResponseDataType, Object> enumMap = new EnumMap<>(ResponseDataType.class);
        enumMap.put(ResponseDataType.RESULTS, users.getContent());
        enumMap.put(ResponseDataType.ALL_PAGES, users.getTotalPages());

        this.responseService.setEnumData(enumMap);

        return this.responseService;
    }

    @GetMapping("likes")
    @PreAuthorize("isAuthenticated()")
    private ResponseService<Object> getUserLikes(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        String userName = securityContextHolder.getAuthentication().getName();
        AppUser user = this.userRepository.findByUsername(userName);

        EnumMap<ResponseDataType, Object> enumMap = new EnumMap<>(ResponseDataType.class);
        enumMap.put(ResponseDataType.RESULTS, user.getLikes());

        this.responseService.setEnumData(enumMap);

        return this.responseService;
    }
}
