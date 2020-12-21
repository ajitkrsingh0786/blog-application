package com.blog.blogapplication.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String name;
    @Column(name = "created_at")
    Date createdAt;
    @Column(name = "updated_at")
    Date updatedAt;
    @ManyToMany(mappedBy = "tags", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    List<Post> posts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
