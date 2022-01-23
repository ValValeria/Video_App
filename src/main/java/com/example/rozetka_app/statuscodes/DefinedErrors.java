package com.example.rozetka_app.statuscodes;

import java.util.Collections;
import java.util.Map;

public enum DefinedErrors {
    AUTH_USER_EXISTS("The user with such username has been in our system", 400L),
    AUTH_USER_NOT_EXISTS("The user with such username has not been in our system", 401L),
    INPUT_FIELD_ERRORS("Invalid input fields", 402L),
    ENTITY_NOT_FOUND("The specified entity doesn't exist", 403L),
    REFRESH_TOKEN_MISSING("Refresh token is missing", 404L),
    INVALID_QUERY("Invalid input query", 405L);

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
