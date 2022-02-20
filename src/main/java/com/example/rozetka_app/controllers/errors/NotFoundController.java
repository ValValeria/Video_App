package com.example.rozetka_app.controllers.errors;

import com.example.rozetka_app.exceptions.RefreshTokenMissingException;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@ControllerAdvice
public class NotFoundController {
    private final ResponseService<Object> responseService;

    @Autowired
    NotFoundController(ResponseService<Object> responseService){
        this.responseService = responseService;
    }

    @ExceptionHandler(RefreshTokenMissingException.class)
    private Object handleError(Throwable throwable) {
       return this.responseService;
    }
}
