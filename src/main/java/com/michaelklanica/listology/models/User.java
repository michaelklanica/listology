package com.michaelklanica.listology.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.*;


@Entity(name = "User")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username can't be empty")
    private String username;

    @Column(nullable = false, unique = true)
    @Email(message = "Invalid email")
    @NotBlank(message = "Email can't be empty")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password can't be empty")
    @JsonIgnore
    private String password;

    @Column(columnDefinition = "TEXT")
    private String aboutMe;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column
    @JsonIgnore
    private boolean isAdmin = false;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonBackReference("postRef")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Follow> followings = new HashSet<>();

    @OneToMany(mappedBy = "friend")
    @JsonIgnore
    private Set<Follow> followers = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Favorite> favorites = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonBackReference
    private List<Comment> comments;

    public User() {
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username.trim();
        this.password = password;
    }

    public User(long id, String email, String username, String password, String aboutMe, Boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.aboutMe = aboutMe;
        this.isAdmin = isAdmin;
    }

    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        password = copy.password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Set<Follow> getFollowings() {
        return followings;
    }

    public void setFollowings(Set<Follow> followings) {
        this.followings = followings;
    }

    public Set<Follow> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Follow> followers) {
        this.followers = followers;
    }

    public Set<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Favorite> favorites) {
        this.favorites = favorites;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
