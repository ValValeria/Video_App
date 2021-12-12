package com.example.rozetka_app.aop;

import com.example.rozetka_app.models.User;
import com.example.rozetka_app.repositories.UserRepository;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class AdminAspect {
    private final HttpServletResponse response;
    private final UserRepository userRepository;

    @Autowired
    AdminAspect(
            HttpServletResponse httpServletResponse,
            UserRepository userRepository
    ) {
        this.response = httpServletResponse;
        this.userRepository = userRepository;
    }

    @Around(value = "@annotation(com.example.rozetka_app.annotations.AdminOnly)")
    private Object checkForAdmin(ProceedingJoinPoint joinPoint) throws Throwable {
        final User authUser = this.userRepository
                .findByUsername(SecurityAspect.getAuthentication().getName());

        if(authUser != null && authUser.getRole().equals("admin")){
            return joinPoint.proceed();
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        return null;
    }
}
