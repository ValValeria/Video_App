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
public class AppUser extends BaseUser {
    @OneToMany(targetEntity = Video.class, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Video> videos;

    @OneToMany(targetEntity = Comment.class, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(targetEntity = Like.class, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Like> likes;

    @Column
    @Size(min = 10, max = 20)
    @NotNull
    protected String password;

    @Column(columnDefinition = "enum(\"user\", \"admin\") default \"user\"")
    protected String role;

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

    public void addToVideoList(Video video){
        this.videos.add(video);
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
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
}
