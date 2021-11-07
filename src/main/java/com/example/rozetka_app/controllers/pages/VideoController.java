package com.example.rozetka_app.controllers.pages;

import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.VideoRepository;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController("/api")
public class VideoController {
    private final VideoRepository videoRepository;

    @Autowired
    public VideoController(VideoRepository videoRepository){
        this.videoRepository = videoRepository;
    }

    @GetMapping("/videos/{id}")
    public void getVideo(@PathVariable int id,
                         HttpServletResponse response
                         ){
       Optional<Video> video = videoRepository.findById(id);

       if(video.isPresent()){

       } else {
           final String redirectUrl = ServletUriComponentsBuilder.fromCurrentRequest()
                   .replacePath("/").toUriString();
           response.encodeRedirectURL(redirectUrl);
       }
    }

    @GetMapping("/videos")
    public void getVideos(){}

    @PutMapping("/videos")
    public void uploadVideo(@RequestParam() MultipartFile video){

    }
}
