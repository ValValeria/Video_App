package com.example.rozetka_app.controllers.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.rozetka_app.annotations.AdminOnly;
import com.example.rozetka_app.annotations.EntityMustExists;
import com.example.rozetka_app.exceptions.RefreshTokenMissingException;
import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/token")
@PreAuthorize("isAuthenticated()")
public class RequestTokenController {
    private final UserRepository userRepository;
    private final ResponseService<String> responseService;

    @Autowired
    RequestTokenController(
       UserRepository userRepository,
       ResponseService<String> responseService
    ) {
        this.userRepository = userRepository;
        this.responseService = responseService;
    }

    @GetMapping("/{id}/request-token")
    @EntityMustExists(classType = AppUser.class)
    private Object getToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bear ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bear ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
                String username = decodedJWT.getSubject();

                final AppUser appUser = this.userRepository.findByUsername(username);
                final String accessToken = JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 10000))
                        .withIssuer(request.getRequestURI())
                        .sign(algorithm);

                final EnumMap<ResponseDataType, String> enumMap = new EnumMap<>(ResponseDataType.class);
                enumMap.put(ResponseDataType.REFRESH_TOKEN, refreshToken);
                enumMap.put(ResponseDataType.ACCESS_TOKEN, accessToken);

                this.responseService.setEnumData(enumMap);
            } catch (Throwable throwable) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            throw new RefreshTokenMissingException();
        }

        return this.responseService;
    }
}
