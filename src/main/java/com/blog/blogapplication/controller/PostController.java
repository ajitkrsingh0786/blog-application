package com.blog.blogapplication.controller;

import com.blog.blogapplication.dao.TagRepository;
import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.model.Tag;
import com.blog.blogapplication.service.PostService;
import com.blog.blogapplication.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        model.addAttribute("postList", postService.getAllPost());
        return "html/index";
    }

    @RequestMapping("/writePost")
    public String writePost(Model model) {
        Post post = new Post();
       // post.setTags(tagService.getAllTag());
        model.addAttribute("post", post);
        model.addAttribute("tags", tagService.getAllTag());
        return "html/writePost";
    }

    @PostMapping("/savePost")
    public String savePost(@ModelAttribute("post") Post post, @Param("selectedTag") String selectedTag){
        Date date=java.util.Calendar.getInstance().getTime();
        post.setCreatedAt(date);
        post.setUpdatedAt(date);
        post.setPublished(false);
        post.setTags(tagService.getSelectedTags(selectedTag));
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

    @RequestMapping("/searchPosts")
    public String searchPosts(@Param("keyword") String keyword, Model model) {
        model.addAttribute("postList", postService.searchPosts(keyword) );
        return "html/index";
    }

   @RequestMapping("/filterPost")
    public String filterPost(@Param("filter") String filter, Model model) {
        if(filter.equals("tags")) {
            model.addAttribute("tags", tagService.getAllTag());
        }

        if(filter.equals("author")){
            model.addAttribute("authorsName",postService.getDistinctAuthor());
        }
        return "html/selectFilter";
   }

   @RequestMapping("/getFilteredPost")
    public String getFilteredPost(@Param("selected") String selected, Model model) {
        Tag tag =tagService.getTagById(Integer.parseInt(selected));
        List<Post> postList = tag.getPosts();
        model.addAttribute("postList", postList);
       return "html/index";
   }

   @RequestMapping("/getFilteredPostByAuthor")
   public String getFilteredPostByAuthor(@Param("selected") String selected, Model model) {

    model.addAttribute("postList", postService.getByAuthor(selected));
       return "html/index";
   }

   @RequestMapping("/sortByDate")
    public String sortByDate(Model model) {
      model.addAttribute("postList",postService.getAllPostOrderByCreatedAt());
      return "html/index";
   }


}