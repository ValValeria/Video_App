package com.example.rozetka_app.services;

import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResponseService<T> {
    private Map<String, T> data;
    private String[] errors;
    private String status;

    public Map<String, T> getData() {
        return data;
    }

    public void setData(Map<String, T> data) {
        EnumSet<ResponseDataType> enumSet = EnumSet.allOf(ResponseDataType.class);

        boolean hasRequiredKeys = enumSet.stream().map(v -> v.name().toLowerCase(Locale.ROOT))
                .anyMatch(data::containsKey);

        if(!hasRequiredKeys){
            throw new IllegalArgumentException();
        }

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
