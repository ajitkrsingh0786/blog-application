package com.blog.blogapplication.service.implementation;

import com.blog.blogapplication.model.Post;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

public class Resources {

    public static final int PAGE_SIZE = 2;

    static List<Integer> resourcePostId;

    public static void getPageableModel(int pageNo, Page<Post> page, Model model, String keyword, String pathUrl) {


        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalPosts", page.getTotalElements());
        model.addAttribute("postList", page.getContent());
        model.addAttribute("pathUrl",pathUrl);
        model.addAttribute("keyword", keyword);

    }

    public  static PagedListHolder  getPageableList(List<Post> posts, int pageNo, int pageSize){
        PagedListHolder page = new PagedListHolder(posts);
        page.setPageSize(pageSize);
        page.setPage(pageNo-1);
        List<Post> postsLi = page.getPageList();
        return page;
    }

    public static void getPageableModelListHolder(int pageNo, PagedListHolder page, Model model, String keyword, String pathUrl, int totalPosts) {

         List<Post> posts = page.getPageList();

        System.out.println("4 : "+ posts.size());
        System.out.println("5 : "+ resourcePostId.size());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getPageCount());
        model.addAttribute("totalPosts", totalPosts);
        model.addAttribute("postList", posts);
        model.addAttribute("pathUrl",pathUrl);
        model.addAttribute("keyword", keyword);
    }

    public static List<Integer> getResourcePostId() {
        return resourcePostId;
    }

    public static void setResourcePostId(List<Integer> resourcePostId) {
        Resources.resourcePostId = resourcePostId;
    }
}
