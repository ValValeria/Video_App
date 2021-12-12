package com.example.rozetka_app.aop;

import com.example.rozetka_app.models.User;
import com.example.rozetka_app.repositories.UserRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
        final SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        final Authentication authentication = securityContextHolder.getAuthentication();
        boolean shouldProceed = false;
        Object retVal = null;

        if(authentication.isAuthenticated()){
            String username = authentication.getName();
            final User authUser = this.userRepository.findByUsername(username);

            if(authUser != null && authUser.getRole().equals("admin")){
               shouldProceed = true;
            }
        }

        if(shouldProceed) {
            retVal = joinPoint.proceed();
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        return retVal;
    }
}
