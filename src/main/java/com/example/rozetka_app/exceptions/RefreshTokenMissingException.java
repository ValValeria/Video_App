package com.example.rozetka_app.exceptions;

public class RefreshTokenMissingException extends RuntimeException {
    public RefreshTokenMissingException() {
        super("Refresh token is missing");
    }
}
