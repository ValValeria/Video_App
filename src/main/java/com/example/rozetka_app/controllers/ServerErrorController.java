package com.example.rozetka_app.controllers;

import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@ControllerAdvice
public class ServerErrorController implements ErrorController {
    private final HttpServletResponse response;
    private final ResponseService<Object> responseService;

    @Autowired
    public ServerErrorController(
            HttpServletResponse response,
            ResponseService<Object> responseService)
    {
        this.response = response;
        this.responseService = responseService;
    }

    @RequestMapping(value="/error", produces = MediaType.APPLICATION_JSON_VALUE)
    private Object error(Exception ex) {
        this.responseService.setErrors(new String[]{"Some error has occurred"});

        ex.printStackTrace();

        return this.responseService;
    }
}
