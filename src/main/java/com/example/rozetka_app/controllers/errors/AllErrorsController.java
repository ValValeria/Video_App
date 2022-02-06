package com.example.rozetka_app.controllers.errors;

import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AllErrorsController {
    private final ResponseService<Object> responseService;

    @Autowired
    public AllErrorsController(
        ResponseService<Object> responseService
    ) {
        this.responseService = responseService;
    }

    @GetMapping("/error")
    private Object handleError() {
        return this.responseService;
    }
}
