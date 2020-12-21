package com.blog.blogapplication.service.ServiceClass;

import com.blog.blogapplication.dao.TagRepository;
import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.model.Tag;
import com.blog.blogapplication.service.Interface.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagServiceClass implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public void saveTag(Tag tag) {
        Date date = Calendar.getInstance().getTime();

        tag.setCreatedAt(date);
        tagRepository.save(tag);
    }

    @Override
    public List<Tag> getAllTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> getSelectedTags(String selectedTags) {
        String selectedTagsArray[] = selectedTags.split(",");
        List<Tag> tags = new ArrayList<>();
        for (String selectedTag : selectedTagsArray) {
            String selectedTag1 = selectedTag.trim();
            tags.add(tagRepository.findByName(selectedTag1));
        }
        return tags;
    }

    @Override
    public Tag getTagById(int id) {
        Tag tag;

        Optional<Tag> optional = tagRepository.findById(id);
        if (optional.isPresent()) {
            tag = optional.get();
        } else {
            throw new RuntimeException("tag not found");
        }
        return tag;
    }

    @Override
    public void deleteTagById(int id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Page<Post> getAllPostsById(int id, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return tagRepository.findAllPostsById(id,pageable);
    }
}
