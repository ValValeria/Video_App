package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.models.BaseUser;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.EnumMap;

@Controller("/api")
public class UsersController {
    private UserRepository userRepository;
    private ResponseService<Object> responseService;

    @GetMapping("/users")
    @ResponseBody
    private Object getUsersList(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        Page<BaseUser> users = this.userRepository.findUsersWithHiddenProps(PageRequest.of(page, size));

        EnumMap<ResponseDataType, Object> enumMap = new EnumMap<>(ResponseDataType.class);
        enumMap.put(ResponseDataType.RESULTS, users.getContent());
        enumMap.put(ResponseDataType.ALL_PAGES, users.getTotalPages());

        this.responseService.setEnumData(enumMap);

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
