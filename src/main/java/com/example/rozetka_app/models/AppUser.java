package com.example.rozetka_app.models;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "rozetka_app_users")
public class AppUser {
    @Id
    @Column
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column(columnDefinition = "enum(\"user\", \"admin\") default \"user\"")
    private String role;

    @OneToMany(targetEntity = Video.class, mappedBy = "user", fetch = EAGER)
    private List<Video> videoList;

    @OneToMany(targetEntity = Comment.class, mappedBy = "user", fetch = EAGER)
    private List<Comment> commentList;

    @OneToMany(targetEntity = Like.class, mappedBy = "user", fetch = EAGER)
    private List<Like> likeList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Like> getLikeList() {
        return likeList;
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

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
