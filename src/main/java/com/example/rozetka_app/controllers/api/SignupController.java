package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.models.User;
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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;

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
    protected Object signup(@Valid User user, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()){
            User user1 = userRepository.findByUsername(user.getUsername());

            if(user1 == null){
                user.setRole("user");
                user.setPassword(user.getPassword());
                userRepository.save(user);
                authenticateUser(user);

                this.responseService.setStatus("ok");
            }
        }

        return this.responseService;
    }

    private void authenticateUser(User user){
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Collection<? extends GrantedAuthority> authorityCollection = AppSecurityUserRoles.READER.getAuthorities();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorityCollection);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
