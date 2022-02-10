package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.annotations.AdminOnly;
import com.example.rozetka_app.models.BaseUser;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumMap;

@RestController("/api")
public class UsersController {
    private final UserRepository userRepository;
    private final ResponseService<Object> responseService;

    @Autowired
    public UsersController(
            UserRepository userRepository,
            ResponseService<Object> responseService
    ) {
        this.userRepository = userRepository;
        this.responseService = responseService;
    }

    @GetMapping("users")
    @AdminOnly
    private String getUsersList(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) throws JsonProcessingException {
        Page<BaseUser> users = this.userRepository.findUsersWithHiddenProps(PageRequest.of(page, size));

        EnumMap<ResponseDataType, Object> enumMap = new EnumMap<>(ResponseDataType.class);
        enumMap.put(ResponseDataType.RESULTS, users.getContent());
        enumMap.put(ResponseDataType.ALL_PAGES, users.getTotalPages());

        this.responseService.setEnumData(enumMap);

        return new ObjectMapper().writeValueAsString(this.responseService);
    }
}
