package com.michaelklanica.listology.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity(name = "Post")
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 0, message = "Your title should be a longer.")
    @Size(max = 1000, message = "Your title is too long.")
    @NotBlank(message = "Your post needs a title.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message= "Your post needs a body.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @NotBlank(message = "Your post needs a category.")
    @Column(nullable = false)
    private String category;

    @ManyToOne
    @JsonManagedReference(value="postRef")
    private User author;

    @OneToMany(mappedBy = "post")
    private Set<Favorite> favoritedBy = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @JsonBackReference
    private List<Comment> comments;

    public Post() {
    }

    public Post(User author, String title, String body, List<Comment> comments, String category) {
        this.author = author;
        this.title = title;
        this.body = body;
        this.category = category;
        this.comments = comments;
    }

    public Post(Long id, User author, String title, String body, String category, List<Comment> comments) {
        this.author = author;
        this.id = id;
        this.title = title;
        this.body = body;
        this.category = category;
        this.comments = comments;
    }

    public Post(String title, String body, String category) {
        this.title = title;
        this.body = body;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Favorite> getFavoritedBy() {
        return favoritedBy;
    }

    public void setFavoritedBy(Set<Favorite> favoritedBy) {
        this.favoritedBy = favoritedBy;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setUser(User randomUser) {
    }
}
