package com.example.rozetka_app.statuscodes;

import java.util.Collections;
import java.util.Map;

public enum DefinedErrors {
    AUTH_USER_EXISTS("Пользователь с таким именем уже существует", 400L),
    AUTH_USER_NOT_EXISTS("Пользователя с таким именем нет", 401L),
    INPUT_FIELD_ERRORS("Проверьте правильность поля", 402L),
    NOT_FOUND_ERRORS("Данной сущности не существует", 403L),
    INVALID_QUERY("Неправильные входные данные", 404L);

    private final String errorMessage;
    private final Long statusCode;

    DefinedErrors(
        String errorMessage,
        Long statusCode
    ){
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Long getStatusCode() {
        return statusCode;
    }

    public Map<Long, String> getAllInfo() {
        return Collections.singletonMap(this.statusCode, this.errorMessage);
    }
}
