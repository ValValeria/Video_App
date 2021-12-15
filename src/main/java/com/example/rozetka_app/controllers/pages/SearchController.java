package com.example.rozetka_app.controllers.pages;

import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.VideoRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
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
    private ModelAndView search(@RequestParam String search){
        this.getVideosByQuery(search);

        ModelAndView modelAndView = new ModelAndView("simple-search");
        final String resultsKey = ResponseDataType.RESULTS.name().toLowerCase(Locale.ROOT);

        if(responseService != null && responseService.getData() != null){
           final List<Video> videos = responseService.getData().get(resultsKey);
           modelAndView.addObject(resultsKey, videos);
        } else{
           modelAndView.addObject(resultsKey, Collections.emptyList());
        }

        return modelAndView;
    }

    private void getVideosByQuery(String search) {
        String searchText = "%" + search + "%";
        List<Video> videoList = videoRepository.findAllByTitleLikeOrAuthorLike(searchText, searchText);

        if(videoList.size() > 0){
            this.responseService.setData(Map.of("results", videoList));
        }
    }
}
