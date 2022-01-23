package com.example.rozetka_app.controllers.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.statuscodes.DefinedErrors;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.security.AppSecurityUserRoles;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/signup")
@PreAuthorize("isAnonymous()")
public class SignupController {
    private final UserRepository userRepository;
    private final ResponseService<Object> responseService;

    @Autowired
    public SignupController(
            UserRepository userRepository,
            ResponseService<Object> responseService
    ){
        this.userRepository = userRepository;
        this.responseService = responseService;
    }

    @PostMapping("")
    protected Object signup(
            HttpServletRequest request,
            @Valid AppUser user,
            BindingResult bindingResult
    ) {
        if(!bindingResult.hasErrors()){
            AppUser appUser = this.userRepository.findByUsername(user.getUsername());

            if(appUser == null){
                com.example.rozetka_app.security.AppUser securityUser = new com.example.rozetka_app.security.AppUser(
                        user.getUsername(),
                        user.getPassword(),
                        AppSecurityUserRoles.READER.getAuthorities()
                );

                if (securityUser != null) {
                    user.setRole(securityUser.getRole());
                    userRepository.save(user);
                    authenticateUser(securityUser, request);

                    this.responseService.setStatus("ok");
                }
            } else {
                this.responseService.addFullErrorsInfo(DefinedErrors.AUTH_USER_EXISTS.getAllInfo());
            }
        } else {
            this.responseService.addFullErrorsInfo(DefinedErrors.INPUT_FIELD_ERRORS.getAllInfo());
        }

        return this.responseService;
    }

    private void authenticateUser(
            com.example.rozetka_app.security.AppUser user,
            HttpServletRequest request){
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        this.getAccessToken(request, user);
    }

    private void getAccessToken(
            HttpServletRequest request,
            com.example.rozetka_app.security.AppUser user
    ) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 10000))
                .withIssuer(request.getRequestURI())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refreshAccessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 50 * 60 * 10000))
                .withIssuer(request.getRequestURI())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        EnumMap<ResponseDataType, Object> dataHashMap = new EnumMap<ResponseDataType, Object>(ResponseDataType.class);
        dataHashMap.put(ResponseDataType.ACCESS_TOKEN, accessToken);
        dataHashMap.put(ResponseDataType.REFRESH_TOKEN, refreshAccessToken);

        this.responseService.setEnumData(dataHashMap);
    }
}
