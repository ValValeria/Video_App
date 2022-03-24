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

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");

        return true;
    }
}
