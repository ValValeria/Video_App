package com.example.rozetka_app.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "rozetka_app_users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;

    @Column
    @Size(min = 10, max = 25)
    @NotNull
    private String username;

    @Column
    @Size(min = 10, max = 20)
    @NotNull
    @JsonIgnore
    private String password;

    @Column(columnDefinition = "enum(\"user\", \"admin\") default \"user\"")
    @JsonIgnore
    private String role;

    @OneToMany(targetEntity = Video.class, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Video> videoList = new ArrayList<>();

    @OneToMany(targetEntity = Comment.class, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(targetEntity = Like.class, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Like> likeList = new ArrayList<>();

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
