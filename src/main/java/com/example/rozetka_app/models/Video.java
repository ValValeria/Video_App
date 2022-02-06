package com.example.rozetka_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rozetka_app_videos")
public class Video {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String path;

    @Column
    @Size(min=10, max=40)
    private String title;

    @Column(name = "created_at", columnDefinition = "datetime")
    private LocalDateTime createdAt;

    @Column
    @Size(min=10, max=400)
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonIgnore
    private AppUser user;

    @OneToMany(targetEntity = Comment.class, mappedBy = "video", fetch = FetchType.LAZY)
    private List<Comment> comments;

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime date) {
        this.createdAt = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public BaseUser getAuthor() {
        BaseUser baseUser = new BaseUser();
        baseUser.setId(this.user.getId());
        baseUser.setUsername(this.user.getUsername());

        return baseUser;
    }
}
