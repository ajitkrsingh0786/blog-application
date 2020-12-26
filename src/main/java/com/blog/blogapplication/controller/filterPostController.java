package com.blog.blogapplication.controller;

import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.service.declaration.PostService;
import com.blog.blogapplication.service.declaration.TagService;
import com.blog.blogapplication.service.implementation.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class filterPostController {

    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;

    @RequestMapping("/getFilteredPosts/{pageNo}")
    public String getFilteredPostByAuthor(@RequestParam("byDay") String byDay,
                                          @RequestParam("authors") String authors, @RequestParam("tags") String selectedTags,
                                          @PathVariable(value = "pageNo") int pageNo, Model model) {
        String pathUrl = "getFilteredPostByAuthor";
        List<Post> posts = postService.getByFilters(authors,selectedTags,byDay);
        PagedListHolder page = Resources.getPageableList(posts,pageNo,Resources.PAGE_SIZE);
        Resources.getPageableModelListHolder(pageNo,page,model,authors,pathUrl,posts.size());
        model.addAttribute("tags", tagService.getAllTag());
        model.addAttribute("authorsName", postService.getDistinctAuthor());
        model.addAttribute("byDay", byDay);
        model.addAttribute("authors",authors);
        model.addAttribute("selectedTags",selectedTags);
        return "html/index";
    }
}
