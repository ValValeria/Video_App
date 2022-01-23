package com.example.rozetka_app.listeners;

import com.example.rozetka_app.services.ResponseService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

@Component
public class HttpRequestListener implements ApplicationListener<RequestHandledEvent> {
    private final ResponseService<Object> responseService;

    public HttpRequestListener(
            ResponseService<Object> responseService
    ) {
        this.responseService = responseService;
    }

    @Override
    public void onApplicationEvent(RequestHandledEvent event) {
        this.responseService.clearData();
    }
}
