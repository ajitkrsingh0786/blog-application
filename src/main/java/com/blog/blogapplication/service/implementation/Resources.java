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

    public  static PagedListHolder  getPageableList(List<Post> posts, int pageNo, int pageSize){
        PagedListHolder page = new PagedListHolder(posts);
        page.setPageSize(pageSize);
        page.setPage(pageNo-1);
        return page;
    }

    public static List<Integer> getResourcePostId() {
        return resourcePostId;
    }

    public static void setResourcePostId(List<Integer> resourcePostId) {
        Resources.resourcePostId = resourcePostId;
    }
}
