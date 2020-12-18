package com.blog.blogapplication.service;

import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.model.Tag;

import java.util.List;

public interface TagService {
    void saveTag(Tag tag);
    List<Tag> getAllTag();
    List<Tag> getSelectedTags(String selectedTags);
    Tag getTagById(int id);
}
