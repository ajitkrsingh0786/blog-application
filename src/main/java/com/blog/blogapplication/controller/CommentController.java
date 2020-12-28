package com.blog.blogapplication.controller;

import com.blog.blogapplication.model.Comment;
import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.service.declaration.CommentService;
import com.blog.blogapplication.service.declaration.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    /*
    @RequestMapping("/addCommentForm/{postId}")
    public String addCommentForm(@PathVariable(value = "postId") int postId, Model model) {
        Comment comment = new Comment();
        Post post = postService.getPostById(postId);

        comment.setPost(post);
        model.addAttribute("commentObj", comment);
        model.addAttribute("postId", postId);
        return "html/addComment";
    }

    @PostMapping("/addComment/{postId}")
    public String addComment(@PathVariable(value = "postId") int postId,
                             @ModelAttribute("commentObj") Comment commentObj) {

        commentService.saveComment(commentObj);
        return "redirect:/viewPost/" + postId;
    }

    @RequestMapping("/viewComments/{postId}")
    public String viewComments(@PathVariable(value = "postId") int postId, Model model) {
        Post post = postService.getPostById(postId);

        model.addAttribute("comments", commentService.getCommentsByPost(post));
        model.addAttribute("postId", postId);
        return "html/viewComments";
    }

    @RequestMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable(value = "id") int id) {
        commentService.deleteCommentById(id);
        return "redirect:/";
    }

    @RequestMapping("/updateComment/{commentId}/{postId}")
    public String updateComment(@PathVariable(value = "commentId") int commentId,
                                @PathVariable(value = "postId") int postId, Model model) {
        model.addAttribute("commentObj", commentService.getCommentById(commentId));
        model.addAttribute("postId", postId);
        return "html/addComment";
    }
     */
    @RequestMapping("/comments/{postId}")
    public List<Comment> getAllComments(@PathVariable int postId) {
        return postService.getPostById(postId).getComments();
    }

    @RequestMapping("/comments/{id}")
    public Comment getComment(@PathVariable int id) {
        return commentService.getCommentById(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/comments/{postId}")
    public void addComment(@PathVariable int postId, @RequestBody Comment comment) {
        comment.setPost(postService.getPostById(postId));
        commentService.saveComment(comment);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/comments")
    public void updateComment( @RequestBody Comment comment) {
        commentService.saveComment(comment);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/comments/{id}")
    public void deleteComment(@PathVariable int id) {
        commentService.deleteCommentById(id);
    }
}
