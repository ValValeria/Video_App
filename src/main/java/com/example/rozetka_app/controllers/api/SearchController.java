package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.VideoRepository;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@RestController("/api")
public class SearchController {
    private final VideoRepository videoRepository;
    private final ResponseService<List<Video>> responseService;

    @Autowired
    public SearchController(VideoRepository videoRepository,
                            ResponseService<List<Video>> responseService
                            ){
        this.videoRepository = videoRepository;
        this.responseService = responseService;
    }

    @GetMapping("/search")
    public Object search(@RequestParam() String search){
        String searchText = "%" + search + "%";
        List<Video> videoList = videoRepository.findAllByTitleLikeOrAuthorLike(searchText, searchText);

        if(videoList.size() > 0){
            this.responseService.setData(Map.of("results", videoList));
        }

        return this.responseService;
    }
}
