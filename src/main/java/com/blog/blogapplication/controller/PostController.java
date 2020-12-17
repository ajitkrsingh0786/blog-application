package com.blog.blogapplication.controller;

import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping("/")
    public String allPosts(Model model) {
        model.addAttribute("postList", postService.getAllPost());
        return "html/index";
    }

    @RequestMapping("/writePost")
    public String writePost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "html/writePost";
    }

    @PostMapping("/savePost")
    public String savePost(@ModelAttribute("post") Post post){
        Date date=java.util.Calendar.getInstance().getTime();
        post.setCreatedAt(date);
        post.setUpdatedAt(date);
        post.setPublished(false);
        postService.savePost(post);
        return "redirect:/";
    }

    @RequestMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post",post);
        return "html/postUpdate";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute("post") Post post){
        Date date=java.util.Calendar.getInstance().getTime();
        post.setUpdatedAt(date);
        postService.savePost(post);
        return "redirect:/";
    }

    @RequestMapping("/deletePost/{id}")
    public String deletePost(@PathVariable(value = "id") int id){
        postService.deletePostById(id);
        return "redirect:/";
    }

    @RequestMapping("/viewPost/{id}")
    public String viewPost(@PathVariable(value = "id") int id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post",post);
        return "html/viewPost";
    }
}