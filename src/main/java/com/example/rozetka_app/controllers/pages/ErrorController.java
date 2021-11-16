package com.example.rozetka_app.controllers.pages;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller()
@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(Throwable.class)
    private ResponseEntity<String> error(Exception ex){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
