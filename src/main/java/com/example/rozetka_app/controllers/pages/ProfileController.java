package com.example.rozetka_app.controllers.pages;

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
import java.util.Optional;


@Controller("/profile/")
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
    @PreAuthorize("isAuthenticated() && hasPermission(#id, 'com.example.rozetka_app.models.User', 'can:view_profiles')")
    private String viewProfile(@PathVariable int id, Model model){
        Optional<User> user = this.userRepository.findById(id);

        if(user.isEmpty()){
            return "redirect:/";
        }

        model.addAttribute("user", user.get());

        return "profile";
    }

    @GetMapping("/{id}/videos")
    @PreAuthorize("isAuthenticated() && hasPermission(#id, 'com.example.rozetka_app.models.User', 'can:view_profiles')")
    private String getFavoritesVideo(@PathVariable int id, Model model){
        Optional<User> user = this.userRepository.findById(id);

        if(user.isEmpty()){
            return "redirect:/";
        }

        model.addAttribute("videos", user.get().getVideoList());

        return "user_videos";
    }

    @PutMapping("/{id}/videos/{videoId}")
    @PreAuthorize("isAuthenticated() && hasPermission(#id, 'com.example.rozetka_app.models.User', 'can:view_profiles')")
    private String addToLikedVideo(@PathVariable int id,
                                   @PathVariable int videoId){
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
}
