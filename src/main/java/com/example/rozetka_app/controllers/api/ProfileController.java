package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.annotations.EntityMustExists;
import com.example.rozetka_app.annotations.SecurityPermissionsContext;
import com.example.rozetka_app.models.User;
import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

import static com.example.rozetka_app.security.AppSecurityUserRolesList.CAN_ADD_LIKES;
import static com.example.rozetka_app.security.AppSecurityUserRolesList.CAN_VIEW_PROFILE;


@Controller
@RequestMapping(value = "/profile")
@PreAuthorize("isAuthenticated()")
public class ProfileController {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    @Autowired
    public ProfileController(
            UserRepository userRepository,
            VideoRepository videoRepository
    ){
       this.userRepository = userRepository;
       this.videoRepository = videoRepository;
    }

    @GetMapping("/{id}")
    @SecurityPermissionsContext(
            permission = CAN_VIEW_PROFILE,
            className = User.class
    )
    @EntityMustExists(classType = User.class)
    private String viewProfile(
            @PathVariable(name = "id") Long entityId,
            Model model){
        User user = this.userRepository.findUserById(entityId);
        List<Video> videoList = user.getVideoList();

        model.addAttribute("user", this.userRepository.findUserById(entityId));
        model.addAttribute("video", videoList);

        return "profile";
    }

    @PutMapping("/{id}/videos/{videoId}")
    @SecurityPermissionsContext(
            permission = CAN_ADD_LIKES,
            className = User.class
    )
    @EntityMustExists(classType = User.class)
    private String addToLikedVideo(@PathVariable(name = "id") Long entityId,
                                   @PathVariable Long videoId){
        Optional<User> optionalUser = this.userRepository.findById(entityId);
        Optional<Video> optionalVideo = this.videoRepository.findById(videoId);
        String url = "/videos";

        if(optionalUser.isPresent() && optionalVideo.isPresent()){
            Video video = optionalVideo.get();
            User user = optionalUser.get();

            user.addToVideoList(video);
            this.userRepository.deleteById(user.getId());
            this.userRepository.save(user);

            url = "/video?isSuccess=true";
        }

        return url;
    }
}
