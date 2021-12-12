package com.example.rozetka_app.aop;

import com.example.rozetka_app.models.Statistics;
import com.example.rozetka_app.repositories.StatisticsRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

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


    @Before("getMethodPointcut()")
    private void updateStatics() {
        LocalDate localDate = LocalDate.now();
        List<Statistics> statisticsList = statisticsRepository.findAll(Sort.by("day").ascending());
        Statistics statistics = statisticsList.get(statisticsList.size() - 1);

        if(statisticsList.size() == 0){
            statistics = new Statistics();
            statistics.setDay(localDate);
        } else {
            statisticsRepository.delete(statistics);
        }

        statistics.addIpAddress(request.getRemoteUser());
        statisticsRepository.save(statistics);
    }
}
