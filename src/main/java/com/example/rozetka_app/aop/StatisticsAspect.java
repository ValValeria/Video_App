package com.example.rozetka_app.aop;

import com.example.rozetka_app.models.Statistics;
import com.example.rozetka_app.repositories.StatisticsRepository;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class StatisticsAspect {
    private final HttpServletRequest request;
    private final StatisticsRepository statisticsRepository;

    @Autowired
    public StatisticsAspect(
            HttpServletRequest httpServletRequest,
            StatisticsRepository statisticsRepository
    ) {
        this.request = httpServletRequest;
        this.statisticsRepository = statisticsRepository;
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) OR @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void getMethodPointcut() {}


    @After("getMethodPointcut()")
    private void updateStatics() {
        LocalDate localDate = LocalDate.now();
        Optional<Statistics> optional = statisticsRepository.findByDayBefore(LocalDate.now());
        Statistics statistics;

        if(optional.isPresent()){
            statistics = optional.get();
            statisticsRepository.deleteById(statistics.getId());
        } else {
            statistics = new Statistics();
            statistics.addIpAddress(request.getRemoteAddr());
            statistics.setIpsAsString(statistics.findIpsAsMap());
        }

        List<Statistics> statisticsList = this.statisticsRepository.findAll(Sort.by("id").descending());
        Long id = 0L;

        if (statisticsList.size() > 0) {
            id = statisticsList.get(statisticsList.size() - 1).getId() + 1;
        }

        statistics.setId(id);
        statistics.setDay(localDate);
        statisticsRepository.save(statistics);
    }
}
