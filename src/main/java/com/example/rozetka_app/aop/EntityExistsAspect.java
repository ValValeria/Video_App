package com.example.rozetka_app.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.example.rozetka_app.annotations.EntityMustExists;
import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.CommentRepository;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.repositories.VideoRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


@Aspect
@Component
public class EntityExistsAspect {
    private final HttpServletResponse response;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private boolean isAllowed = false;

    @Autowired
    public EntityExistsAspect(
            HttpServletResponse httpServletResponse,
            CommentRepository commentRepository,
            UserRepository userRepository,
            VideoRepository videoRepository
    ) {
        this.response = httpServletResponse;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
    }

    @Around("@annotation(com.example.rozetka_app.annotations.EntityMustExists) && args(entityId, ..)")
    private Object getRequiredEntity(
            ProceedingJoinPoint joinPoint,
            Long entityId
    ) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        EntityMustExists entityMustExists = method.getAnnotation(EntityMustExists.class);
        Class<?> classType = entityMustExists.classType();
        Object result = null;
        Object object = null;

        if(classType.isAssignableFrom(AppUser.class)){
            object = this.userRepository.findUserById(entityId);
        }
        if(classType.isAssignableFrom(Video.class)){
            object = this.videoRepository.findVideoById(entityId);
        }
        if(classType.isAssignableFrom(AppUser.class)){
            object = this.commentRepository.findCommentById(entityId);
        }

        if (object != null) {
            result = joinPoint.proceed();
        } else {
            final String redirectUrl = ServletUriComponentsBuilder.fromCurrentRequest()
                    .replacePath("/").toUriString();
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.encodeRedirectURL(redirectUrl);
        }

        return result;
    }
}
