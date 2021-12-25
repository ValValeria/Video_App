package com.example.rozetka_app.controllers;

import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.VideoRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
public class SearchController {
    private final VideoRepository videoRepository;

    @Autowired
    public SearchController(VideoRepository videoRepository){
        this.videoRepository = videoRepository;
    }

    @GetMapping("/search")
    private ModelAndView search(
            @RequestParam String search,
            @RequestParam Integer page,
            @RequestParam Integer per_page
    ){
        Pattern pattern = Pattern.compile("[aA-zZ]{1,20}");
        Matcher matcher = pattern.matcher(search);

        final PageRequest pageRequest = PageRequest.of(page, per_page, Sort.by("id"));
        final String resultsKey = ResponseDataType.RESULTS.name().toLowerCase(Locale.ROOT);
        final Map<String, Object> pageDataMap = new LinkedHashMap<>();
        Page<Video> videoPage;

        if(matcher.find()) {
            search = "%" + search + "%";
            videoPage = videoRepository.findAllByText(search, Pageable.ofSize(per_page));
        } else {
            videoPage = videoRepository.findAll(pageRequest);
        }

        pageDataMap.put(resultsKey, videoPage.get().collect(Collectors.toList()));
        pageDataMap.put("page", videoPage.getTotalPages());

        ModelAndView modelAndView = new ModelAndView("simple-search");
        modelAndView.addAllObjects(pageDataMap);

        return modelAndView;
    }
}
