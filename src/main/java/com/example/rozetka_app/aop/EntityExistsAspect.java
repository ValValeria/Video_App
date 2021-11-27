package com.example.rozetka_app.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
@Aspect
public class EntityExistsAspect {
    private final HttpServletResponse response;

    @Autowired
    public EntityExistsAspect(HttpServletResponse httpServletResponse) {
       this.response = httpServletResponse;
    }

    @Before("@annotation(com.example.rozetka_app.annotations.EntityMustExists)")
    private void getRequiredEntity(JoinPoint joinPoint) {

    }
}
