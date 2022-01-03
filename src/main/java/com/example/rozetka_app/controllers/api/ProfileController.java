package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.annotations.EntityMustExists;
import com.example.rozetka_app.annotations.SecurityPermissionsContext;
import com.example.rozetka_app.models.User;
import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.repositories.VideoRepository;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.rozetka_app.security.AppSecurityUserRolesList.CAN_ADD_LIKES;
import static com.example.rozetka_app.security.AppSecurityUserRolesList.CAN_VIEW_PROFILE;


@RestController
@RequestMapping(value = "/api/profile")
@PreAuthorize("isAuthenticated()")
public class ProfileController {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final ResponseService<Object> responseService;

    @Autowired
    public ProfileController(
            UserRepository userRepository,
            VideoRepository videoRepository,
            ResponseService<Object> responseService
    ){
       this.userRepository = userRepository;
       this.videoRepository = videoRepository;
       this.responseService = responseService;
    }

    @GetMapping("/{id}")
    @SecurityPermissionsContext(
            permission = CAN_VIEW_PROFILE,
            className = User.class
    )
    @EntityMustExists(classType = User.class)
    private Object viewProfile(@PathVariable(name = "id") Long entityId){
        User user = this.userRepository.findUserById(entityId);

        Map<String, Object> objectMap = new java.util.HashMap<>();
        objectMap.put("user", this.userRepository.findUserById(entityId));
        objectMap.put("video", user.getVideoList());

        this.responseService.setData(objectMap);

        return this.responseService;
    }

    @PutMapping("/{id}/videos/{videoId}")
    @SecurityPermissionsContext(
            permission = CAN_ADD_LIKES,
            className = User.class
    )
    @EntityMustExists(classType = User.class)
    private Object addToLikedVideo(@PathVariable(name = "id") Long entityId,
                                   @PathVariable Long videoId){
        Optional<User> optionalUser = this.userRepository.findById(entityId);
        Optional<Video> optionalVideo = this.videoRepository.findById(videoId);

        if(optionalUser.isPresent() && optionalVideo.isPresent()){
            Video video = optionalVideo.get();
            User user = optionalUser.get();

            user.addToVideoList(video);
            this.userRepository.deleteById(user.getId());
            this.userRepository.save(user);

            this.responseService.setStatus("ok");
        }

        return this.responseService;
    }
}
