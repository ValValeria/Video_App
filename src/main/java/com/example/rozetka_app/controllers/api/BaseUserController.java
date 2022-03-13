package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.models.BaseUser;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.data.domain.Page;

import java.util.EnumMap;

public class BaseUserController {
    private ResponseService<Object> responseService;

    protected void addResultToUserService(Page<Object> appUserPage) {
        EnumMap<ResponseDataType, Object> enumMap = new EnumMap<>(ResponseDataType.class);
        enumMap.put(ResponseDataType.RESULTS, appUserPage.getContent());
        enumMap.put(ResponseDataType.ALL_PAGES, appUserPage.getTotalPages());

        this.responseService.setEnumData(enumMap);
    }
}
