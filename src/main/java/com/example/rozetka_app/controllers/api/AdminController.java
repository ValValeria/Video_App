package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.annotations.AdminOnly;
import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.EnumMap;

@Controller
@RequestMapping("/api/admin")
public class AdminController {
    private final UserRepository userRepository;
    private final ResponseService<Object> responseService;

    @Autowired
    public AdminController(
            UserRepository userRepository,
            ResponseService<Object> responseService
    ) {
        this.userRepository = userRepository;
        this.responseService = responseService;
    }

    @AdminOnly
    @GetMapping("users")
    private Object getUsersList(@RequestParam Integer page, @RequestParam Integer size ){
        Page<AppUser> users = this.userRepository.findAll(PageRequest.of(page, size));

        EnumMap<ResponseDataType, Object> enumMap = new EnumMap<>(ResponseDataType.class);
        enumMap.put(ResponseDataType.RESULTS, users.getContent());
        enumMap.put(ResponseDataType.ALL_PAGES, users.getTotalPages());

        this.responseService.setEnumData(enumMap);

        return this.responseService;
    }
}
