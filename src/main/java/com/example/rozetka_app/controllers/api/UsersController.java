package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumMap;

@RestController("/api/users")
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

    @GetMapping
    private Object getUsers(
            @RequestParam int page,
            @RequestParam int size
    ) {
        EnumMap<ResponseDataType, Object> hashMap = new EnumMap<>(ResponseDataType.class);
        hashMap.put(ResponseDataType.RESULTS, this.userRepository.findAll(PageRequest.of(page, size)));

        return this.responseService;
    }
}
