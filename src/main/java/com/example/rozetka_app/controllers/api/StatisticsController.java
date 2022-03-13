package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.annotations.AdminOnly;
import com.example.rozetka_app.models.Statistics;
import com.example.rozetka_app.repositories.StatisticsRepository;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class StatisticsController {
    private final StatisticsRepository statisticsRepository;
    private final ResponseService<Object> responseService;

    @Autowired
    StatisticsController(
          StatisticsRepository statisticsRepository,
          ResponseService<Object> responseService
    ) {
       this.statisticsRepository = statisticsRepository;
       this.responseService = responseService;
    }

    @AdminOnly
    @GetMapping(path = "/statistics")
    private Object getStatistics(
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            @RequestParam Integer page,
            @RequestParam(name = "per_page") Integer perPage
    ) {
      LocalDate localDate = LocalDate.parse(date.toString());
      Page<Statistics> statisticsPage = this.statisticsRepository.findAllUpToDay(localDate, PageRequest.of(page, perPage));

      final Map<String, Object> map = new HashMap<>(
              Map.of("data", statisticsPage.get().collect(Collectors.toList()),
                      "pages", statisticsPage.getTotalPages(),
                      "items_count", statisticsPage.getTotalElements()
                      )
      );

      this.responseService.setData(map);

      return this.responseService;
    }
}
