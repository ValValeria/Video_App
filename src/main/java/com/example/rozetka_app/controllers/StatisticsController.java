package com.example.rozetka_app.controllers;

import com.example.rozetka_app.annotations.AdminOnly;
import com.example.rozetka_app.models.Statistics;
import com.example.rozetka_app.repositories.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;

@Controller
public class StatisticsController {
    private final StatisticsRepository statisticsRepository;

    @Autowired
    StatisticsController(
          StatisticsRepository statisticsRepository
    ) {
       this.statisticsRepository = statisticsRepository;
    }

    @AdminOnly
    @GetMapping(path = "/statistics")
    private ModelAndView getStatistics(
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            @RequestParam Integer page,
            @RequestParam(name = "per_page") Integer perPage
    ) {
      ModelAndView modelAndView = new ModelAndView("statistics");

      LocalDate localDate = LocalDate.parse(date.toString());
      Page<Statistics> statisticsPage = this.statisticsRepository.findAllUpToDay(localDate, PageRequest.of(page, perPage));
      modelAndView.addObject("data", statisticsPage.get().collect(Collectors.toList()));
      modelAndView.addObject("pages", statisticsPage.getTotalPages());
      modelAndView.addObject("items_count", statisticsPage.getTotalElements());

      return modelAndView;
    }
}
