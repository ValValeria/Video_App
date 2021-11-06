package com.example.rozetka_app.controllers;

import com.example.rozetka_app.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("/api")
public class VideoController {
    private final VideoRepository videoRepository;

    @Autowired
    public VideoController(VideoRepository videoRepository){
        this.videoRepository = videoRepository;
    }

    @GetMapping("/videos/{id}")
    public void getVideo(@PathVariable int id){

    }

    @GetMapping("/videos")
    public void getVideos(){}

    @PutMapping("/videos")
    public void uploadVideo(@RequestParam() MultipartFile video){

    }
}
