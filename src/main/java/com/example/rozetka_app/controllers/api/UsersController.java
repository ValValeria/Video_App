package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller("/api")
public class UsersController extends BaseUserController {
    private UserRepository userRepository;
    private ResponseService<Object> responseService;

    @Autowired
    public UsersController(
            UserRepository userRepository,
            ResponseService<Object> responseService
    ) {
        this.userRepository = userRepository;
        this.responseService = responseService;
    }

    @GetMapping("/users")
    @ResponseBody
    private Object getUsersList(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        this.addResultToUserService(this.userRepository.findUsersWithHiddenProps(PageRequest.of(page, size)));

        return this.responseService;
    }

    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Inject
    public void setResponseService(ResponseService<Object> responseService) {
        this.responseService = responseService;
    }
}
