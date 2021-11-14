package com.example.rozetka_app.controllers.pages;

import com.example.rozetka_app.models.User;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.security.AppSecurityUserPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import java.util.Optional;


@Controller("/profile/{id}")
@PreAuthorize("isAuthenticated()")
public class ProfileController {
    private final UserRepository userRepository;

    @Autowired
    public ProfileController(UserRepository userRepository){
       this.userRepository = userRepository;
    }

    @GetMapping
    @PreAuthorize("hasPermission(#id, 'com.example.rozetka_app.models.User', 'can:view_profiles')")
    private String viewProfile(@PathVariable int id, Model model){
        Optional<User> user = this.userRepository.findById(id);

        if(user.isEmpty()){
            return "redirect:/";
        }

        model.addAttribute("user", user.get());

        return "profile";
    }
}
