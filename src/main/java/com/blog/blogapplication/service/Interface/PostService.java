package com.blog.blogapplication.service.Interface;

import com.blog.blogapplication.model.Post;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface PostService {
    void savePost(Post post);
    List<Post> getAllPost();
    Post getPostById(int id);
    void deletePostById(int id);
    Page<Post> searchPosts(String keyword,int pageNo, int pageSize);
    Page<Post> getAllPostOrderByCreatedAt(String sortBy, int pageNo, int pageSize);
    List<String> getDistinctAuthor();
    Page<Post> getByAuthor(String author, int pageNo, int pageSize);
    Page<Post> findAllWithCreatedAtBefore(String selected, int pageNo, int pageSize);
    Page<Post> findPaginated(int pageNo, int pageSize);
}
