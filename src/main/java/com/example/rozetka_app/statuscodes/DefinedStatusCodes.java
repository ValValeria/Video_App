package com.example.rozetka_app.statuscodes;

import java.util.Collections;
import java.util.Map;

public enum DefinedStatusCodes {
    STATUS_OK("Задача выполнена успешно", 200L);

    private final String message;
    private final Long statusCode;

    DefinedStatusCodes(
            String message,
            Long statusCode
    ){
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return message;
    }

    public Long getStatusCode() {
        return statusCode;
    }

    public Map<Long, String> getAllInfo() {
        return Collections.singletonMap(this.statusCode, this.message);
    }
}
