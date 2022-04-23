package com.example.rozetka_app.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "rozetka_app_users")
public class BaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    protected Long id;

    @Column
    @Size(min = 10, max = 25)
    @NotNull
    protected String username;

    @OneToMany(targetEntity = Video.class, mappedBy = "user", fetch = FetchType.LAZY)
    protected List<Video> videos;

    public void addToVideoList(Video video){
        this.videos.add(video);
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
