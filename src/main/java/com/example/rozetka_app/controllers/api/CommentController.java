package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.models.Comment;
import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.CommentRepository;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    private final CommentRepository commentRepository;
    private final ResponseService<List<Comment>> responseService;

    @Autowired
    CommentController(
            CommentRepository commentRepository,
            ResponseService<List<Comment>> responseService
    ){
        this.commentRepository = commentRepository;
        this.responseService = responseService;
    }

    @GetMapping("/{videoId}")
    private String getComments(@PathVariable Long videoId){
       return null;
    }
}
