package com.example.rozetka_app.models;

import javax.persistence.*;

@Entity
@Table
public class Video {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
