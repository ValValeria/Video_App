package com.example.rozetka_app.controllers.pages;

import com.example.rozetka_app.models.User;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.security.AppSecurityUserPermissions;
import com.example.rozetka_app.security.AppSecurityUserRoles;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Controller()
@RequestMapping("/signup")
@PreAuthorize("isAnonymous()")
public class SignupController {
    private final UserRepository userRepository;

    @Autowired
    public SignupController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("")
    private String index(){
        return "signup";
    }

    @PostMapping("")
    protected void signup(@Valid User user,
                          BindingResult bindingResult,
                          HttpServletResponse httpServletResponse,
                          HttpServletRequest httpServletRequest
    ) throws IOException {
        String baseUrl = ServletUriComponentsBuilder.fromContextPath(httpServletRequest).toString();

        if(!bindingResult.hasErrors()){
            User user1 = userRepository.findByUsername(user.getUsername());

            if(user1 != null){
                setUpRedirect(baseUrl, httpServletResponse, "error", "");
            } else {
                user.setRole("user");
                user.setPassword(user.getPassword());
                userRepository.save(user);
                authenticateUser(user);

                httpServletResponse.sendRedirect("/");
            }
        } else {
            setUpRedirect(baseUrl, httpServletResponse, "inputError", "");
        }
    }

    private void setUpRedirect( String baseUrl, HttpServletResponse httpServletResponse, String queryParam, String queryParamValue) throws IOException {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(baseUrl);
        UriBuilder uriBuilder = uriBuilderFactory.uriString("/signup").queryParam(queryParam, queryParamValue);

        httpServletResponse.sendRedirect(uriBuilder.build().toString());
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
