package com.blog.blogapplication.service.implementation;

import com.blog.blogapplication.dao.TagRepository;
import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.model.Tag;
import com.blog.blogapplication.service.declaration.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagServiceImp implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public void saveTag(Tag tag) {
        Date date = Calendar.getInstance().getTime();
        String name = tag.getName().trim();
        tag.setName(name);
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
            if (tagRepository.findByName(selectedTag1) == null) {
                Tag tag = new Tag();
                tag.setName(selectedTag);
                saveTag(tag);
            }
            tags.add(tagRepository.findByName(selectedTag1));
        }
        return tags;
    }

    @Override
    public Tag getTagById(int id) {
        Tag tag;
        Optional<Tag> optional = tagRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void deleteTagById(int id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Page<Post> getAllPostsById(int id, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return tagRepository.findAllPostsById(id, pageable);
    }

    @Override
    public List<Post> getAllPostsByName(String name) {
        List<Post> newPostList = new ArrayList<>();
        List<Integer> newPostsId = new ArrayList<>();
        String[] tagsArray = name.split(",");

        for(String tagName: tagsArray){
            List<Post> postList = tagRepository.findAllPostsByName(tagName);
            System.out.println("Author : " +postList.size());
            for(Post post : postList){
                if(!newPostsId.contains(post.getId()) && Resources.resourcePostId.contains(post.getId())){
                    newPostList.add(post);
                    newPostsId.add(post.getId());
                }
            }
        }
        Resources.resourcePostId = newPostsId;
        return  newPostList;
    }
}
