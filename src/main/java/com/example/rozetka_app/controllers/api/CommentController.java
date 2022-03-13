package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.annotations.SecurityPermissionsContext;
import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.models.Comment;
import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.CommentRepository;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.repositories.VideoRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import static com.example.rozetka_app.security.AppSecurityUserRolesList.CAN_CREATE_COMMENT;

@RestController
@RequestMapping(path = "/api/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final ResponseService<Object> responseService;

    @Autowired
    CommentController(
            CommentRepository commentRepository,
            VideoRepository videoRepository,
            UserRepository userRepository,
            ResponseService<Object> responseService
    ){
        this.commentRepository = commentRepository;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.responseService = responseService;
    }

    @GetMapping("/{id}")
    private ResponseService<Object> getComments(
            @PathVariable(name = "id") Long entityId
    ) {
        EnumMap<ResponseDataType, Object> enumMap = new EnumMap<>(ResponseDataType.class);
        enumMap.put(ResponseDataType.RESULT, this.commentRepository.findAllByVideoId(entityId));

        this.responseService.setEnumData(enumMap);

        return this.responseService;
    }

    @DeleteMapping("/{videoId}")
    @PreAuthorize("isAuthenticated()")
    private ResponseService<Object> deleteComments(
            @PathVariable Long videoId,
            HttpServletResponse response
    ){
        Optional<Video> optionalVideo = this.videoRepository.findById(videoId);

        if(optionalVideo.isPresent()){
            Video video = optionalVideo.get();
            List<Comment> commentList = Collections.unmodifiableList(video.getComments());

            for(Comment comment : commentList){
                this.commentRepository.deleteById(comment.getId());
            }

            video.getComments().clear();

            this.videoRepository.deleteVideoById(video.getId());
            this.videoRepository.save(video);

            this.responseService.setStatus("ok");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return this.responseService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{videoId}")
    @SecurityPermissionsContext(
            permission = CAN_CREATE_COMMENT,
            className = AppUser.class
    )
    private Object addComment(
            @PathVariable Long videoId,
            @Valid Comment comment,
            BindingResult bindingResult,
            HttpServletResponse response,
            Principal principal
    ){
        if(!bindingResult.hasErrors()){
            Optional<Video> optionalVideo = this.videoRepository.findById(videoId);

            if(optionalVideo.isPresent()) {
                Video video = optionalVideo.get();
                AppUser user = this.userRepository.findByUsername(principal.getName());

                comment.setUser(user);
                comment.setVideo(video);

                this.commentRepository.save(comment);

                this.responseService.setStatus("ok");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

        return this.responseService;
    }
}
