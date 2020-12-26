package com.blog.blogapplication.service.declaration;

import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.model.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    void savePost(Post post, String authorName);

    List<Post> getAllPost();

    Post getPostById(int id);

    void deletePostById(int id);

    List<Post> searchPosts(String keyword);

    List<Post> getAllPostOrderByPublishedAt(String sortBy);

    List<String> getDistinctAuthor();

    Page<Post> getByAuthor(String author, int pageNo, int pageSize);

    List<Post> getAllWithPublishedAtBefore(String selected);

    Page<Post> getPaginated(int pageNo, int pageSize);

    Page<Post> getPostsByTag(Tag tag, int pageNo, int pageSize);

    List<Post> getByAuthor(String authors);
    List<Post> getByFilters(String authors,String tags,String byDate);
}
