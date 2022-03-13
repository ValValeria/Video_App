package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.annotations.EntityMustExists;
import com.example.rozetka_app.annotations.SecurityPermissionsContext;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.example.rozetka_app.security.AppSecurityUserRolesList.CAN_ADD_LIKES;
import static com.example.rozetka_app.security.AppSecurityUserRolesList.CAN_VIEW_PROFILE;

@RestController
@RequestMapping(value = "/api/profile")
@PreAuthorize("isAuthenticated()")
public class ProfileController {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final ResponseService<Object> responseService;
    private final LikeRepository likeRepository;

    @Autowired
    public ProfileController(
            UserRepository userRepository,
            VideoRepository videoRepository,
            ResponseService<Object> responseService,
            LikeRepository likeRepository
    ) {
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
        this.responseService = responseService;
        this.likeRepository = likeRepository;
    }

    @GetMapping("/{id}")
    @SecurityPermissionsContext(
            permission = CAN_VIEW_PROFILE,
            className = AppUser.class
    )
    @EntityMustExists(classType = AppUser.class)
    private Object viewProfile(@PathVariable(name = "id") Long entityId) {
        AppUser user = this.userRepository.findUserById(entityId);

        Map<String, Object> objectMap = new java.util.HashMap<>();
        objectMap.put("user", this.userRepository.findUserById(entityId));
        objectMap.put("video", user.getVideos());

        this.responseService.setData(objectMap);

        return this.responseService;
    }

    @PutMapping("/{id}/videos/{videoId}")
    @SecurityPermissionsContext(
            permission = CAN_ADD_LIKES,
            className = AppUser.class
    )
    @EntityMustExists(classType = AppUser.class)
    private Object addToLikedVideo(@PathVariable(name = "id") Long entityId,
                                   @PathVariable Long videoId) {
        AppUser user = this.userRepository.findUserById(entityId);
        Optional<Video> optionalVideo = this.videoRepository.findById(videoId);

        if (Objects.nonNull(user) && optionalVideo.isPresent()) {
            Video video = optionalVideo.get();

            user.addToVideoList(video);
            this.userRepository.deleteById(user.getId());
            this.userRepository.save(user);

            this.responseService.setStatus("ok");
        }

        return this.responseService;
    }


    @PostMapping("/{id}/{videoId}")
    @EntityMustExists(classType = AppUser.class)
    private Object addLikes(
            @PathVariable(name = "id") Long entityId,
            @PathVariable(name = "videoId") Long videoEntityId
    ) {
        Optional<Video> optionalVideo = this.videoRepository.findById(videoEntityId);
        AppUser appUser = this.userRepository.findUserById(entityId);

        if (optionalVideo.isPresent() && Objects.nonNull(appUser)) {
            Video video = optionalVideo.get();
            Like like = this.likeRepository.findLikeByUserIdEqualsAndVideoIdEquals(appUser.getId(), video.getId());

            if (like == null) {
                like = new Like();
                like.setVideo(video);
                like.setUser(appUser);

                this.likeRepository.save(like);

                this.responseService.addFullStatusInfo(DefinedStatusCodes.STATUS_OK.getAllInfo());
            }
        } else {
            this.responseService.addFullStatusInfo(DefinedErrors.ENTITY_NOT_FOUND.getAllInfo());
        }

        return this.responseService;
    }
}
