package com.blog.blogapplication.dao;

import com.blog.blogapplication.model.Comment;
import com.blog.blogapplication.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
     List<Comment> findByPost(Post post);
}
