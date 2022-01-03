package com.example.rozetka_app.controllers.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@Controller
@ControllerAdvice
public class NotFoundController {
    private final String TAG = NotFoundController.class.getName();
    private final Logger logger = LoggerFactory.getLogger(TAG);

    @ExceptionHandler(NoHandlerFoundException.class)
    @RequestMapping(path = "/**", method = RequestMethod.GET)
    private ModelAndView handleError() {
       logger.info("Handling http request");

       return new ModelAndView("home");
    }
}
