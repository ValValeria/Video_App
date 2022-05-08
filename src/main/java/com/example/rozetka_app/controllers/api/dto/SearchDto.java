package com.example.rozetka_app.controllers.api.dto;

public class SearchDto {
    private String search;
    private Integer page;
    private Integer per_page;

    public String getSearch() {
        return search;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setPer_page(Integer per_page) {
        this.per_page = per_page;
    }
}
