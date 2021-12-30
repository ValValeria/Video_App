package com.example.rozetka_app.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rozetka_app_users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @OneToMany(targetEntity = Video.class, mappedBy = "user")
    private List<Video> videoList;

    @OneToMany(targetEntity = Comment.class, mappedBy = "user")
    private List<Comment> commentList;

    @Column(columnDefinition = "enum(\"user\", \"admin\") default \"user\"")
    private String role;

    @Column(columnDefinition = "likes smallint default 0")
    private Integer likes;

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

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void addToVideoList(Video video){
        this.videoList.add(video);
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
