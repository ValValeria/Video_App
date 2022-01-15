package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.annotations.AdminOnly;
import com.example.rozetka_app.annotations.EntityMustExists;
import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.models.Like;
import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.LikeRepository;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.repositories.VideoRepository;
import com.example.rozetka_app.services.ResponseService;
import com.example.rozetka_app.statuscodes.DefinedErrors;
import com.example.rozetka_app.statuscodes.DefinedStatusCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/user")
public class UserController {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final ResponseService<AppUser> responseService;
    private final LikeRepository likeRepository;

    @Autowired
    public UserController(
        UserRepository userRepository,
        VideoRepository videoRepository,
        ResponseService<AppUser> responseService,
        LikeRepository likeRepository
    ) {
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
        this.responseService = responseService;
        this.likeRepository = likeRepository;
    }

    @GetMapping(path = "/{id}")
    @EntityMustExists(classType = AppUser.class)
    private Object getUser(@PathVariable(name = "id") Long entityId) {
        this.responseService.setData(Map.of("user", this.userRepository.findById(entityId).get()));

        return this.responseService;
    }

    @AdminOnly
    @DeleteMapping(path = "/{id}")
    @EntityMustExists(classType = AppUser.class)
    private Object deleteUser(@PathVariable(name = "id") Long entityId){
        this.responseService.addFullStatusInfo(DefinedStatusCodes.STATUS_OK.getAllInfo());

        return this.responseService;
    }

    @PostMapping("/{id}/{videoId}")
    @EntityMustExists(classType = AppUser.class)
    private Object addLikes(
         @PathVariable(name = "id") Long entityId,
         @PathVariable(name = "videoId") Long videoEntityId
    ) {
        Optional<Video> optionalVideo = this.videoRepository.findById(videoEntityId);
        Optional<AppUser> optionalUser = this.userRepository.findById(entityId);

        if(optionalVideo.isPresent() && optionalUser.isPresent()) {
          Video video = optionalVideo.get();
          AppUser appUser = optionalUser.get();
          Like like = this.likeRepository.findLikeByUserIdEqualsAndVideoIdEquals(appUser.getId(), video.getId());

          if (like == null) {
              like = new Like();
              like.setVideo(video);
              like.setUser(appUser);

              this.likeRepository.save(like);

              this.responseService.addFullStatusInfo(DefinedStatusCodes.STATUS_OK.getAllInfo());
          }
        } else {
          this.responseService.addFullStatusInfo(DefinedErrors.NOT_FOUND_ERRORS.getAllInfo());
        }

        return this.responseService;
    }
}
