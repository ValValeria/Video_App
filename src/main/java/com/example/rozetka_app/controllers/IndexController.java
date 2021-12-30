package com.example.rozetka_app.controllers;

import com.example.rozetka_app.models.User;
import com.example.rozetka_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@ControllerAdvice
public class IndexController {
    private final UserRepository userRepository;

    @Autowired
    IndexController(
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @ModelAttribute
    private void addAttributes(Model model) {
        model.addAttribute("likes", 0);
    }

    @GetMapping("/")
    private ModelAndView getHomePage(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("home");
        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            User user = this.userRepository.findByUsername(principal.getName());
            modelAndView.addObject("likes", user.getLikes());
        }

        return modelAndView;
    }
}
