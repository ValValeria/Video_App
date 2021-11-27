package com.example.rozetka_app.controllers.pages;

import com.example.rozetka_app.annotations.EntityMustExists;
import com.example.rozetka_app.models.User;
import com.example.rozetka_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/{id}")
    @EntityMustExists(classType = User.class, entityKey = "id")
    private ModelAndView getUser(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user", this.userRepository.getById(id));

        return modelAndView;
    }

    @DeleteMapping(path = "/{id}")
    @EntityMustExists(classType = User.class, entityKey = "id")
    private void deleteUser(
         @PathVariable Long id,
         HttpServletResponse response
    ) throws IOException {
       this.userRepository.deleteById(id);
       response.sendRedirect("/login");
    }
}
