package com.example.rozetka_app.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table
public class Video {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column
    private String path;

    @Column
    @Size(min=10, max=40)
    private String title;

    @Column(columnDefinition = "date")
    private String date;

    @Column
    @Size(min=10, max=400)
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
