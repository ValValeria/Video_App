package com.example.rozetka_app.controllers.errors;

import com.example.rozetka_app.services.ResponseService;
import com.example.rozetka_app.statuscodes.DefinedErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@ControllerAdvice
public class NotFoundController {
    private final String TAG = NotFoundController.class.getName();
    private final Logger logger = LoggerFactory.getLogger(TAG);
    private final ResponseService<Object> responseService;

    @Autowired
    NotFoundController(ResponseService<Object> responseService){
        this.responseService = responseService;
    }

    @ExceptionHandler(Throwable.class)
    @RequestMapping(path = "/error", method = RequestMethod.GET)
    private Object handleError(Throwable throwable) {
       logger.info("Handling error. Error class: " + throwable.getClass().getName());

       throwable.printStackTrace();

       this.responseService.addFullErrorsInfo(DefinedErrors.INVALID_QUERY.getAllInfo());

       return this.responseService;
    }


}
