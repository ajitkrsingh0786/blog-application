package com.blog.blogapplication.service;

import com.blog.blogapplication.dao.TagRepository;
import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImplement implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public void saveTag(Tag tag) {
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
        for(String selectedTag: selectedTagsArray){
             String selectedTag1 =  selectedTag.trim();
             tags.add(tagRepository.findByName(selectedTag1));
        }
        return tags;
    }

    @Override
    public Tag getTagById(int id) {
        Tag tag = null;
        Optional<Tag> optional = tagRepository.findById(id);
        if(optional.isPresent()){
            tag = optional.get();
        }else {
            throw new RuntimeException("tag not found");
        }
        return tag;
    }
}
