package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.security.AppSecurityUserRoles;
import com.example.rozetka_app.security.SecurityAppUser;
import com.example.rozetka_app.services.ResponseService;
import com.example.rozetka_app.statuscodes.DefinedErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth/sign-up")
@PreAuthorize("isAnonymous()")
@Validated
public class AuthController {
    private final UserRepository userRepository;
    private final ResponseService<Object> responseService;

    @Autowired
    public AuthController(
            UserRepository userRepository,
            ResponseService<Object> responseService
    ) {
        this.userRepository = userRepository;
        this.responseService = responseService;
    }

    @PostMapping("")
    protected Object createUser(
            @Valid @RequestBody() AppUser user,
            BindingResult bindingResult
    ) {
        if (!bindingResult.hasErrors()) {
            AppUser appUser = this.userRepository.findAppUserByUsername(user.getUsername());

            System.out.println(user.getPassword() + "!!!!");

            if (appUser == null) {
                SecurityAppUser securityUser = new SecurityAppUser(
                        user.getUsername(),
                        user.getPassword(),
                        AppSecurityUserRoles.READER.getAuthorities()
                );

                if (securityUser.getRole() != null) {
                    user.setRole(securityUser.getRole());
                    userRepository.save(user);
                    authenticateUser(securityUser);

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

    private void authenticateUser(SecurityAppUser user) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
