package com.blog.blogapplication.service;

import com.blog.blogapplication.model.Post;

import java.util.List;

public interface PostService {
    void savePost(Post post);
    List<Post> getAllPost();
    Post getPostById(int id);
    void deletePostById(int id);
}
