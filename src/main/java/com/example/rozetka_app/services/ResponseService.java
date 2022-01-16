package com.example.rozetka_app.services;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResponseService<T> {
    private Map<String, T> data;
    private String[] errors;
    private String status;

    private final Map<Long, String> fullErrorsInfo;
    private final Map<Long, String> fullStatusInfo;

    ResponseService() {
        this.fullErrorsInfo = new HashMap<>();
        this.fullStatusInfo = new HashMap<>();

        this.errors = new String[]{};
        this.status = "undefined";
    }

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

    public void clearData(){
        this.status = "";
        this.errors = new String[]{};

        if(data != null){
            this.data.clear();
        }
    }

    public void addFullErrorsInfo(Map<Long, String> map) {
        this.fullErrorsInfo.putAll(map);
    }

    public void addFullStatusInfo(Map<Long, String> map) {
        this.fullStatusInfo.putAll(map);
    }

    public Map<Long, String> getFullErrorsInfo() {
        return fullErrorsInfo;
    }

    public Map<Long, String> getFullStatusInfo() {
        return fullStatusInfo;
    }
}
