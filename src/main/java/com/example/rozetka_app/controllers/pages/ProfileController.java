package com.example.rozetka_app.controllers.pages;

import com.example.rozetka_app.annotations.SecurityPermissionsContext;
import com.example.rozetka_app.models.User;
import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.repositories.VideoRepository;
import static com.example.rozetka_app.security.AppSecurityUserRolesList.CAN_VIEW_PROFILE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Optional;


@Controller("/profile/")
@PreAuthorize("isAuthenticated()")
public class ProfileController {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    @Autowired
    public ProfileController(UserRepository userRepository,
                             VideoRepository videoRepository
                             ){
       this.userRepository = userRepository;
       this.videoRepository = videoRepository;
    }

    @GetMapping("/{id}")
    @SecurityPermissionsContext(
            permission = CAN_VIEW_PROFILE,
            className = com.example.rozetka_app.models.User.class
    )
    private String viewProfile(
            @PathVariable Long id,
            User user,
            Model model){
        Optional<User> optionalUser = this.userRepository.findById(id);

        if(optionalUser.isEmpty()){
            return "redirect:/";
        }

        model.addAttribute("user", optionalUser.get());

        return "profile";
    }

    @PutMapping("/{id}/videos/{videoId}")
    @SecurityPermissionsContext(
            permission = CAN_VIEW_PROFILE,
            className = com.example.rozetka_app.models.User.class
    )
    private String addToLikedVideo(@PathVariable Long id,
                                   @PathVariable Long videoId){
        Optional<User> optionalUser = this.userRepository.findById(id);
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

    /**
     * Adds likes
     * @param id
     * @param model
     * @return
     */
    private String getFavoritesVideo(@PathVariable Long id, Model model){
        Optional<User> user = this.userRepository.findById(id);

        if(user.isEmpty()){
            return "redirect:/";
        }

        model.addAttribute("videos", user.get().getVideoList());

        return "user_videos";
    }
}
