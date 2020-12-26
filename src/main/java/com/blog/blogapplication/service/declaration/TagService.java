package com.blog.blogapplication.service.declaration;

import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.model.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TagService {

    void saveTag(Tag tag);

    List<Tag> getAllTag();

    List<Tag> getSelectedTags(String selectedTags);

    Tag getTagById(int id);

    void deleteTagById(int id);

    Page<Post> getAllPostsById(int id, int pageNo, int pageSize);

    List<Post> getAllPostsByName(String name);
}
