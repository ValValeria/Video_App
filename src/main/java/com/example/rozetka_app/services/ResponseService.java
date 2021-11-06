package com.example.rozetka_app.services;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ResponseService<T> {
    private Map<String, T> data;
    private String[] errors;
    private String status;

    public Map<String, T> getData() {
        return data;
    }

    public void setData(Map<String, T> data) {
        this.data = data;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
