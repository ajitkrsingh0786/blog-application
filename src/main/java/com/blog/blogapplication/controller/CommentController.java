package com.blog.blogapplication.controller;

import com.blog.blogapplication.model.Comment;
import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.service.CommentService;
import com.blog.blogapplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/addCommentForm/{id}")
    public String addCommentForm(@PathVariable(value = "id") int postId, Model model) {
        Post post = postService.getPostById(postId);
        Comment comment1 = new Comment();
        comment1.setPost(post);
        model.addAttribute("comment1", comment1);
        return "html/addComment";
    }

    @PostMapping("/addComment")
    public String addComment(@ModelAttribute("comment1") Comment comment1) {
        Date date=java.util.Calendar.getInstance().getTime();
        comment1.setCreatedAt(date);
        commentService.saveComment(comment1);
        return "redirect:/";
    }

    @RequestMapping("/viewComments/{id}")
    public String viewComments(@PathVariable(value = "id") int postId, Model model) {
       Post post = postService.getPostById(postId);
       model.addAttribute("comments",commentService.getCommentsByPost(post));
       return "html/viewComments";
    }

    @RequestMapping("/deleteComment/{commentId}")
    public String deleteComment(@PathVariable(value = "commentId") int commentId) {
        commentService.deleteCommentById(commentId);
        return "redirect:/";
    }

    @RequestMapping("/updateComment/{commentId}")
    public String updateComment(@PathVariable(value = "commentId") int commentId, Model model) {
      model.addAttribute("comment1",commentService.getCommentById(commentId));
        return "html/addComment";
    }
}
