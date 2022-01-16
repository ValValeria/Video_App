package com.example.rozetka_app.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.rozetka_app.security.AppUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter (
         AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return this.authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        AppUser appUser = (AppUser) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String accessToken = JWT.create()
                .withSubject(appUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 10000))
                .withIssuer(request.getRequestURI())
                .withClaim("roles", appUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refreshAccessToken = JWT.create()
                .withSubject(appUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 50 * 60 * 10000))
                .withIssuer(request.getRequestURI())
                .withClaim("roles", appUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        response.setHeader("accessToken", accessToken);
        response.setHeader("refreshToken", refreshAccessToken);
    }
}
