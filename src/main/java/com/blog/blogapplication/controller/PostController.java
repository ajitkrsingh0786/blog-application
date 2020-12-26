package com.blog.blogapplication.controller;

import com.blog.blogapplication.dao.UserRepository;
import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.model.Tag;
import com.blog.blogapplication.service.declaration.CommentService;
import com.blog.blogapplication.service.declaration.PostService;
import com.blog.blogapplication.service.declaration.TagService;
import com.blog.blogapplication.service.implementation.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    TagService tagService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/")
    public String allPosts(Model model) {
        return findPaginated(1, model);
    }

    @RequestMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        String pathUrl = "page";
        List<Post> posts = postService.getAllPost();
        PagedListHolder page = Resources.getPageableList(posts, pageNo, Resources.PAGE_SIZE);
        Resources.getPageableModelListHolder(pageNo, page, model, "", pathUrl, posts.size());
        model.addAttribute("tags", tagService.getAllTag());
        model.addAttribute("authorsName", postService.getDistinctAuthor());
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
    public String savePost(@ModelAttribute("post") Post post, @Param("selectedTag") String selectedTag, @Param(
            "selectedTag") String authorName) {
        post.setTags(tagService.getSelectedTags(selectedTag));
        postService.savePost(post, authorName);
        return "redirect:/";
    }

    @RequestMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        Post post = postService.getPostById(id);
        Date createdAt = new Date(post.getCreatedAt().getTime());
        List<Tag> tags = post.getTags();
        String tagsString = "";
        for (Tag tag : tags) {
            tagsString += tag.getName() + ",";
        }
        post.setCreatedAt(createdAt);
        model.addAttribute("tagsString", tagsString);
        model.addAttribute("post", post);
        model.addAttribute("tags", tagService.getAllTag());
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
        model.addAttribute("comments", commentService.getCommentsByPost(post));
        return "html/viewPost";
    }

    @RequestMapping("/searchPosts/{pageNo}")
    public String searchPosts(@PathVariable(value = "pageNo") int pageNo, @Param("keyword") String keyword,
                              Model model) {
        String pathUrl = "searchPosts";
        List<Post> posts = postService.searchPosts(keyword);
        PagedListHolder page = Resources.getPageableList(posts, pageNo, Resources.PAGE_SIZE);
        Resources.getPageableModelListHolder(pageNo, page, model, keyword, pathUrl, posts.size());
        model.addAttribute("tags", tagService.getAllTag());
        model.addAttribute("authorsName", postService.getDistinctAuthor());
        return "html/index";
    }

    @RequestMapping("/sortByDate/{pageNo}")
    public String sortByDate(@PathVariable(value = "pageNo") int pageNo, @RequestParam("keyword") String keyword,
                             Model model) {
        String pathUrl = "sortByDate";
        List<Post> posts = postService.getAllPostOrderByPublishedAt(keyword);
        PagedListHolder page = Resources.getPageableList(posts, pageNo, Resources.PAGE_SIZE);
        Resources.getPageableModelListHolder(pageNo, page, model, keyword, pathUrl, posts.size());
        model.addAttribute("tags", tagService.getAllTag());
        model.addAttribute("authorsName", postService.getDistinctAuthor());
        return "html/index";
    }
}