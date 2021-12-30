package com.example.rozetka_app.intercepters;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpRequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String path = request.getServletPath();

        if (!path.startsWith("/public")) {
            response.setHeader("Content-Type", "text/html");
        }

        return true;
    }
}
