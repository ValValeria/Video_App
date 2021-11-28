package com.example.rozetka_app.controllers.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
@ControllerAdvice
public class ErrorController {
    private final HttpServletResponse response;

    public ErrorController(HttpServletResponse response) {
        this.response = response;
    }

    @ExceptionHandler(Throwable.class)
    private ModelAndView error(Exception ex) {
        response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        return new ModelAndView("error");
    }
}
