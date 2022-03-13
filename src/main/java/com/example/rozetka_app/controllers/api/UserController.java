package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.annotations.EntityMustExists;
import com.example.rozetka_app.annotations.SecurityPermissionsContext;
import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.EnumMap;

import static com.example.rozetka_app.security.AppSecurityUserRolesList.CAN_VIEW_PROFILE;

@Controller
@RequestMapping(path = "/api/user")
@PreAuthorize("isAuthenticated()")
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

    @GetMapping("/{id}/likes")
    @SecurityPermissionsContext(
            permission = CAN_VIEW_PROFILE,
            className = AppUser.class
    )
    @EntityMustExists(classType = AppUser.class)
    private ResponseService<Object> getUserLikes(@PathVariable(name = "id") Long entityId) {
        AppUser user = this.userRepository.findUserById(entityId);

        EnumMap<ResponseDataType, Object> enumMap = new EnumMap<>(ResponseDataType.class);
        enumMap.put(ResponseDataType.RESULTS, user.getLikes());

        this.responseService.setEnumData(enumMap);

        return this.responseService;
    }
}
