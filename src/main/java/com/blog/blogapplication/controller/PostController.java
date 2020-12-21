package com.blog.blogapplication.controller;

import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.service.Interface.PostService;
import com.blog.blogapplication.service.Interface.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;

    @RequestMapping("/")
    public String allPosts(Model model) {
        return findPaginated(1, model);
    }

    @RequestMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 2;
        Page<Post> page = postService.findPaginated(pageNo, pageSize);
        List<Post> listPosts = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalPosts", page.getTotalElements());
        model.addAttribute("postList", listPosts);
        return "html/index";
    }

    @RequestMapping("/writePost")
    public String writePost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        model.addAttribute("tags", tagService.getAllTag());
        return "html/writePost";
    }

    @PostMapping("/savePost")
    public String savePost(@ModelAttribute("post") Post post, @Param("selectedTag") String selectedTag) {
        post.setTags(tagService.getSelectedTags(selectedTag));
        System.out.println("Hello");
        postService.savePost(post);

        return "redirect:/";
    }

    @RequestMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        Post post = postService.getPostById(id);
        Date createdAt = new Date(post.getCreatedAt().getTime());
        post.setCreatedAt(createdAt);
        System.out.println("Ajit Kumar Singh");
        model.addAttribute("tags", tagService.getAllTag());
        model.addAttribute("post", post);
        return "html/writePost";
    }

    @RequestMapping("/deletePost/{id}")
    public String deletePost(@PathVariable(value = "id") int id) {
        postService.deletePostById(id);
        return "redirect:/";
    }

    @RequestMapping("/viewPost/{id}")
    public String viewPost(@PathVariable(value = "id") int id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "html/viewPost";
    }

    @RequestMapping("/searchPosts/{pageNo}")
    public String searchPosts(@PathVariable(value = "pageNo") int pageNo, @Param("keyword") String keyword,
                              Model model) {
        int pageSize = 2;
        Page<Post> page = postService.searchPosts(keyword, pageNo, pageSize);
        List<Post> listPosts = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalPosts", page.getTotalElements());
        model.addAttribute("postList", listPosts);
        model.addAttribute("keyword", keyword);
        //return "html/sortPost";
        //model.addAttribute("postList", postService.searchPosts(keyword));
        return "html/searchResult";
    }

    @RequestMapping("/sortByDate/{pageNo}")
    public String sortByDate(@PathVariable(value = "pageNo") int pageNo, @Param("sortBy") String sortBy, Model model) {
        int pageSize = 2;
        Page<Post> page = postService.getAllPostOrderByCreatedAt(sortBy, pageNo, pageSize);
        List<Post> listPosts = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalPosts", page.getTotalElements());
        model.addAttribute("postList", listPosts);
        model.addAttribute("sortBy", sortBy);
        return "html/sortPost";
        //  model.addAttribute("postList", postService.getAllPostOrderByCreatedAt());
        //return "html/index";
    }
}