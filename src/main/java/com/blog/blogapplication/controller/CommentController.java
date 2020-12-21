package com.blog.blogapplication.controller;

import com.blog.blogapplication.model.Comment;
import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.service.Interface.CommentService;
import com.blog.blogapplication.service.Interface.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @RequestMapping("/addCommentForm/{postId}")
    public String addCommentForm(@PathVariable(value = "postId") int postId, Model model) {
        Comment commentObj = new Comment();
        Post post = postService.getPostById(postId);

        commentObj.setPost(post);
        model.addAttribute("commentObj", commentObj);
        model.addAttribute("postId", postId);
        return "html/addComment";
    }

    @PostMapping("/addComment/{postId}")
    public String addComment(@PathVariable(value = "postId") int postId,
                             @ModelAttribute("commentObj") Comment commentObj) {
        Date date = Calendar.getInstance().getTime();
        commentObj.setCreatedAt(date);
        commentService.saveComment(commentObj);
        return "redirect:/viewPost/" + postId;
    }

    @RequestMapping("/viewComments/{postId}")
    public String viewComments(@PathVariable(value = "postId") int postId, Model model) {
        Post post = postService.getPostById(postId);

        model.addAttribute("comments", commentService.getCommentsByPost(post));
        model.addAttribute("postId",postId);
        return "html/viewComments";
    }

    @RequestMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable(value = "id") int id) {
        commentService.deleteCommentById(id);
        return "redirect:/";
    }

    @RequestMapping("/updateComment/{commentId}/{postId}")
    public String updateComment(@PathVariable(value = "commentId") int commentId, @PathVariable(value = "postId") int postId,Model model) {
        model.addAttribute("commentObj", commentService.getCommentById(commentId));
        model.addAttribute("postId",postId);
        return "html/addComment";
    }
}
