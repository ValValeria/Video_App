package com.example.rozetka_app.controllers.errors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ControllerAdvice
public class ErrorController {
    @RequestMapping(path = "/error", method = RequestMethod.GET)
    @ExceptionHandler(value = Throwable.class)
    private ModelAndView handleError(Throwable throwable) {
        throwable.printStackTrace();

        return new ModelAndView("home");
    }
}

