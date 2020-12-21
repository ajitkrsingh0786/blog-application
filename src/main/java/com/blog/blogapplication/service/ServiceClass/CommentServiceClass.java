package com.blog.blogapplication.service.ServiceClass;

import com.blog.blogapplication.dao.CommentRepository;
import com.blog.blogapplication.model.Comment;
import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.service.Interface.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceClass implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void saveComment(Comment comment) {
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
        Comment comment;

        Optional<Comment> optional = commentRepository.findById(id);
        if(optional.isPresent()){
            comment = optional.get();
        }else {
            throw new RuntimeException("Comment not found");
        }

        return comment ;
    }
}
