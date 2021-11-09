package com.example.rozetka_app.controllers.pages;

import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
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

@Controller
public class SearchController {
    @GetMapping("/search")
    private ModelAndView search(@RequestParam String search){
        final URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/api/search").queryParam("search", search).build().toUri();
        final ModelAndView modelAndView = new ModelAndView("search");
        final ResponseEntity<ResponseService> response = new RestTemplate().getForEntity(url, ResponseService.class);
        final ResponseService<List<Video>> responseService = response.getBody();
        final String resultsKey = ResponseDataType.RESULTS.name().toLowerCase(Locale.ROOT);

        if(responseService != null && responseService.getData() != null){
           final List<Video> videos = responseService.getData().get(resultsKey);
           modelAndView.addObject(resultsKey, videos);
        } else{
           modelAndView.addObject(resultsKey, Collections.emptyList());
        }

        return modelAndView;
    }
}
