package com.example.rozetka_app.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rozetka_comments")
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

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
