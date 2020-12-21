package com.blog.blogapplication.controller;

import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.model.Tag;
import com.blog.blogapplication.service.Interface.PostService;
import com.blog.blogapplication.service.Interface.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class filterPostController {

    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;

    @RequestMapping("/filterPost")
    public String filterPost(@Param("filter") String filter, Model model) {
        if (filter.equals("tags")) {
            model.addAttribute("tags", tagService.getAllTag());
        }
        if (filter.equals("author")) {
            model.addAttribute("authorsName", postService.getDistinctAuthor());
        }
        return "html/selectFilter";
    }

    @RequestMapping("/getFilteredPost/{pageNo}")
    public String getFilteredPost(@Param("selected") String selected, @PathVariable(value = "pageNo") int pageNo,
                                  Model model) {
        int pageSize = 2;
        Tag tag = tagService.getTagById(Integer.parseInt(selected));
        List<Post> posts = tag.getPosts();
        PagedListHolder page = new PagedListHolder(posts);
        page.setPageSize(pageSize);
        page.setPage(pageNo - 1);
        page.getPageCount();
        List<Post> listPosts = page.getPageList();

        // PageRequest page = new PageRequest(pageNumber, pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getPageCount());
        model.addAttribute("totalPosts",  posts.size());
        model.addAttribute("postList", listPosts);
        model.addAttribute("selected", selected);
        return "html/filterByTag";
    }

    @RequestMapping("/getFilteredPostByAuthor/{pageNo}")
    public String getFilteredPostByAuthor(@Param("selected") String selected,
                                          @PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 2;
        Page<Post> page = postService.getByAuthor(selected, pageNo, pageSize);
        List<Post> listPosts = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalPosts", page.getTotalElements());
        model.addAttribute("postList", listPosts);
        model.addAttribute("selected", selected);
        return "html/authorFilter";
    }

    @RequestMapping("/getFilteredPostByDate/{pageNo}")
    public String getFilteredPostByDate(@Param("selected") String selected,
                                        @PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 2;

        Page<Post> page = postService.findAllWithCreatedAtBefore(selected, pageNo, pageSize);
        List<Post> listPosts = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalPosts", page.getTotalElements());
        model.addAttribute("postList", listPosts);
        model.addAttribute("selected", selected);
        return "html/filterByDate";
    }
}
