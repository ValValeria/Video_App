package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.models.BaseUser;
import com.example.rozetka_app.repositories.BaseUserRepository;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumMap;

@RestController("/api")
public class UsersController extends BaseUserController {
    private BaseUserRepository userRepository;
    private ResponseService<Object> responseService;

    @Autowired
    public UsersController(
            BaseUserRepository userRepository,
            ResponseService<Object> responseService
    ) {
        this.userRepository = userRepository;
        this.responseService = responseService;
    }

    @GetMapping("users")
    private Object getUsers(
            @RequestParam int page,
            @RequestParam int size
    ) {
        EnumMap<ResponseDataType, Object> hashMap = new EnumMap<>(ResponseDataType.class);
        hashMap.put(ResponseDataType.RESULTS, this.userRepository.findAll(PageRequest.of(page, size)));

        this.responseService.setEnumData(hashMap);

        return this.responseService;
    }

    @GetMapping("public-user/{id}")
    private Object getUser(@PathVariable Long id) {
        EnumMap<ResponseDataType, Object> hashMap = new EnumMap<>(ResponseDataType.class);
        hashMap.put(ResponseDataType.RESULT, this.userRepository.findById(id));

        this.responseService.setEnumData(hashMap);

        return this.responseService;
    }
}
