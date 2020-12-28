package com.blog.blogapplication.controller;

import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.service.declaration.PostService;
import com.blog.blogapplication.service.declaration.TagService;
import com.blog.blogapplication.service.implementation.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class filterPostController {

    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;

    @RequestMapping("/filter/{pageNo}")
    public List getFilteredPost(@RequestParam("byDay") String byDay, @RequestParam("authors") String authors,
                                @RequestParam("tags") String selectedTags, @PathVariable(value = "pageNo") int pageNo) {
        List<Post> posts = postService.getByFilters(authors, selectedTags, byDay);
        return Resources.getPageableList(posts, pageNo, Resources.PAGE_SIZE).getPageList();
    }
}
