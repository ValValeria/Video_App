package com.example.rozetka_app.controllers;

import com.example.rozetka_app.annotations.AdminOnly;
import com.example.rozetka_app.annotations.EntityMustExists;
import com.example.rozetka_app.models.User;
import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    @Autowired
    public UserController(
        UserRepository userRepository,
        VideoRepository videoRepository
    ) {
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
    }

    @GetMapping(path = "/{id}")
    @EntityMustExists(classType = User.class)
    private ModelAndView getUser(@PathVariable(name = "id") Long entityId) {
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user", this.userRepository.getById(entityId));

        return modelAndView;
    }

    @AdminOnly
    @DeleteMapping(path = "/{id}")
    @EntityMustExists(classType = User.class)
    private void deleteUser(
         @PathVariable(name = "id") Long entityId,
         HttpServletResponse response
    ) throws IOException {
       this.userRepository.deleteById(entityId);
       response.sendRedirect("/login");
    }

    @PostMapping("/{id}/{videoId}")
    @EntityMustExists(classType = User.class)
    private void addLikes(
         HttpServletResponse response,
         @PathVariable(name = "id") Long entityId,
         @PathVariable(name = "videoId") Long videoEntityId
    ) throws IOException {
        Optional<Video> optionalVideo = this.videoRepository.findById(videoEntityId);
        Optional<User> optionalUser = this.userRepository.findById(entityId);

        if(optionalVideo.isPresent()) {
          Video video = optionalVideo.get();
        } else {
          response.setStatus(HttpServletResponse.SC_NOT_FOUND);
          response.sendRedirect("/");
        }
    }
}
