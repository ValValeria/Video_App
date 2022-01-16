package com.example.rozetka_app.aop;

import com.example.rozetka_app.services.ResponseService;
import com.example.rozetka_app.statuscodes.DefinedErrors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.aspectj.lang.annotation.Aspect;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.codec.Utf8;
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
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;


@Aspect
@Component
public class EntityExistsAspect {
    private final HttpServletResponse response;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final ResponseService<Object> responseService;
    private Logger logger = LoggerFactory.getLogger(EntityExistsAspect.class.getName());
    private boolean isAllowed = false;

    @Autowired
    public EntityExistsAspect(
            HttpServletResponse httpServletResponse,
            CommentRepository commentRepository,
            UserRepository userRepository,
            VideoRepository videoRepository,
            ResponseService<Object> responseService
    ) {
        this.response = httpServletResponse;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
        this.responseService = responseService;
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
            object = this.commentRepository.findById(entityId);
        }

        if (object != null) {
            result = joinPoint.proceed();
        } else {
            setUpResponseError();
        }

        return result;
    }

    private void setUpResponseError() throws IOException {
        final Writer writer = response.getWriter();
        final String redirectUrl = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/")
                .toUriString();

        responseService.addFullErrorsInfo(DefinedErrors.ENTITY_NOT_FOUND.getAllInfo());
        responseService.setStatus("not ok");

        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.encodeRedirectURL(redirectUrl);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        writer.write(new JSONObject(this.responseService).toString());
    }
}
