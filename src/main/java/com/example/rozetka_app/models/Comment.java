package com.example.rozetka_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rozetka_app_comments")
public class Comment {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String content;

    @Column(name = "created_at", columnDefinition = "datetime")
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = Video.class)
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Video video;

    @ManyToOne(targetEntity = AppUser.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private AppUser user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public BaseUser getAuthor() {
        BaseUser baseUser = new BaseUser();
        baseUser.setId(this.user.getId());
        baseUser.setUsername(this.user.getUsername());

        return baseUser;
    }
}
