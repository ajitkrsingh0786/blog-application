package com.blog.blogapplication.service.implementation;

import com.blog.blogapplication.dao.CommentRepository;
import com.blog.blogapplication.model.Comment;
import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.service.declaration.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void saveComment(Comment comment) {
        Date date = Calendar.getInstance().getTime();
        comment.setUpdatedAt(date);
        if (comment.getComment() == null) {
            comment.setCreatedAt(date);
        }
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    @Override
    public void deleteCommentById(int id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment getCommentById(int id) {
        Optional<Comment> optional = commentRepository.findById(id);
        return optional.orElse(null);
    }
}
