package com.example.rozetka_app.aop;

import com.example.rozetka_app.services.ResponseService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseServiceAspect {
    private ResponseService<Object> responseService;

    @Autowired
    public ResponseServiceAspect(ResponseService<Object> responseService) {
        this.responseService = responseService;
    }

    @Before("execution( public * com.example.rozetka_app.*(..))")
    private void clearData(){
        responseService.clearData();
    }
}
