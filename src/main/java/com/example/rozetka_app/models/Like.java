package com.example.rozetka_app.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rozetka_likes")
public class Like {
    @Id
    @Column
    private Long id;

    @ManyToOne(targetEntity = AppUser.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    @ManyToOne(targetEntity = Video.class)
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Video video;

    @Column
    private LocalDateTime date;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "video_id", insertable = false, updatable = false)
    private Long videoId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
