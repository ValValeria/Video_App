package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.statuscodes.DefinedErrors;
import com.example.rozetka_app.models.AppUser;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    protected Object signup(@Valid AppUser user, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()){
            AppUser user1 = userRepository.findByUsername(user.getUsername());

            if(user1 == null){
                user.setRole("user");
                user.setPassword(user.getPassword());
                userRepository.save(user);

                authenticateUser(user);

                this.responseService.setStatus("ok");
            } else {
                this.responseService.addFullErrorsInfo(DefinedErrors.AUTH_USER_EXISTS.getAllInfo());
            }
        } else {
            this.responseService.addFullErrorsInfo(DefinedErrors.INPUT_FIELD_ERRORS.getAllInfo());
        }

        return this.responseService;
    }

    private void authenticateUser(AppUser user){
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Collection<? extends GrantedAuthority> authorityCollection = AppSecurityUserRoles.READER.getAuthorities();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorityCollection);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
