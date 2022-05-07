package com.example.rozetka_app.filters;

import com.example.rozetka_app.security.SecurityAppUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = null;

        try {
            String credentials = request.getHeader(HttpHeaders.AUTHORIZATION);
            SecurityAppUser baseUser = new ObjectMapper().readValue(credentials, SecurityAppUser.class);
            authenticationToken = new UsernamePasswordAuthenticationToken(baseUser.getUsername(), baseUser.getPassword());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return authenticationToken;
    }
}
