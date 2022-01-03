package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.VideoRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
public class SearchController {
    private final VideoRepository videoRepository;
    private final ResponseService<Object> responseService;

    @Autowired
    public SearchController(
            VideoRepository videoRepository,
            ResponseService<Object> responseService
    ){
        this.videoRepository = videoRepository;
        this.responseService = responseService;
    }

    @GetMapping("/api/search")
    private Object search(
            @RequestParam String search,
            @RequestParam Integer page,
            @RequestParam Integer per_page
    ){
        Pattern pattern = Pattern.compile("[aA-zZ]{1,20}");
        Matcher matcher = pattern.matcher(search);

        final PageRequest pageRequest = PageRequest.of(page, per_page, Sort.by("id"));
        final String resultsKey = ResponseDataType.RESULTS.name().toLowerCase(Locale.ROOT);
        Page<Video> videoPage;

        if(matcher.find()) {
            search = "%" + search + "%";
            videoPage = videoRepository.findAllByText(search, Pageable.ofSize(per_page));
        } else {
            videoPage = videoRepository.findAll(pageRequest);
        }

        final Map<String, Object> pageDataMap = new LinkedHashMap<>(Map.of(
                resultsKey, videoPage.get().collect(Collectors.toList()),
                "page", videoPage.getTotalPages()
        ));

        this.responseService.setData(pageDataMap);

        return this.responseService;
    }
}
