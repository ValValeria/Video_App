package com.example.rozetka_app.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "rozetka_app_users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column
    @Size(min = 10, max = 25)
    protected String username;

    @Column
    @Size(min = 10, max = 20)
    protected String password;

    @OneToMany(targetEntity = Comment.class, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(targetEntity = Like.class, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Like> likes;

    @Column(columnDefinition = "enum(\"user\", \"admin\") default \"user\"")
    @NotNull
    protected String role;

    @OneToMany(targetEntity = Video.class, mappedBy = "user", fetch = FetchType.LAZY)
    protected List<Video> videos;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Video> getVideos() {
        return this.videos;
    }

    public void addToVideoList(Video video){
        this.videos.add(video);
    }
}
