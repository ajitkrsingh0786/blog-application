package com.blog.blogapplication.service;

import com.blog.blogapplication.dao.TagRepository;
import com.blog.blogapplication.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImplement implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public void saveTag(Tag tag) {
      tagRepository.save(tag);
    }
}
