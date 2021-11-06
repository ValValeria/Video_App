package com.example.rozetka_app.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ResponseServiceAspect {
    @AfterReturning(pointcut = "execution( public com.example.rozetka_app.*(..))")
    private void clearData(){}
}
