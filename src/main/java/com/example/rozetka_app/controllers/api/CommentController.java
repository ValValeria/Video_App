package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.models.Comment;
import com.example.rozetka_app.models.User;
import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.CommentRepository;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.repositories.VideoRepository;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final ResponseService<List<User>> responseService;

    @Autowired
    CommentController(
            CommentRepository commentRepository,
            VideoRepository videoRepository,
            UserRepository userRepository,
            ResponseService<List<User>> responseService
    ){
        this.commentRepository = commentRepository;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.responseService = responseService;
    }

    @DeleteMapping("/{videoId}")
    private ResponseService<List<User>> deleteComments(
            @PathVariable Long videoId,
            HttpServletResponse response
    ){
        Optional<Video> optionalVideo = this.videoRepository.findById(videoId);

        if(optionalVideo.isPresent()){
            Video video = optionalVideo.get();
            List<Comment> commentList = Collections.unmodifiableList(video.getCommentList());

            for(Comment comment : commentList){
                this.commentRepository.deleteById(comment.getId());
            }

            video.getCommentList().clear();

            this.videoRepository.deleteVideoById(video.getId());
            this.videoRepository.save(video);

            this.responseService.setStatus("ok");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return this.responseService;
    }

    @PostMapping("/{videoId}")
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
                User user = this.userRepository.findByUsername(principal.getName());

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
