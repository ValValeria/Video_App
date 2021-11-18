package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.models.Comment;
import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.CommentRepository;
import com.example.rozetka_app.repositories.VideoRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    private final CommentRepository commentRepository;
    private final ResponseService<List<Comment>> responseService;
    private final VideoRepository videoRepository;

    @Autowired
    CommentController(
            CommentRepository commentRepository,
            ResponseService<List<Comment>> responseService,
            VideoRepository videoRepository
    ){
        this.commentRepository = commentRepository;
        this.responseService = responseService;
        this.videoRepository = videoRepository;
    }

    @GetMapping("/{videoId}")
    private Object getComments(
            @PathVariable Long videoId,
            HttpServletResponse response
    ){
        Optional<Video> optionalVideo = this.videoRepository.findById(videoId);

        if(optionalVideo.isPresent()){
           Video video = optionalVideo.get();
           List<Comment> commentList = video.getCommentList();
           String key = ResponseDataType.RESULTS.name().toLowerCase(Locale.ROOT);

           this.responseService.setData(Collections.singletonMap(key, commentList));
        } else {
           response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return this.responseService;
    }

    @DeleteMapping("/{videoId}")
    private void deleteComments(
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
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @PostMapping("/{videoId}")
    private void addComment(@PathVariable Long videoId){

    }
}
