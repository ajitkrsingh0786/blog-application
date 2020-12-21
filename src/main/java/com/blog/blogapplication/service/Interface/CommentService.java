package com.blog.blogapplication.service.Interface;

import com.blog.blogapplication.model.Comment;
import com.blog.blogapplication.model.Post;

import java.util.List;


public interface CommentService {

    void saveComment(Comment comment);
    List<Comment> getCommentsByPost(Post post);
    void deleteCommentById(int id);
    Comment getCommentById(int id);
}
