package com.blog.blogapplication.service;

import com.blog.blogapplication.dao.PostRepository;
import com.blog.blogapplication.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplement implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Override
    public void savePost(Post post) {
        this.postRepository.save(post);
    }

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(int id) {
        Post post = null;
        Optional<Post> optional = postRepository.findById(id);
        if(optional.isPresent()){
            post = optional.get();
        }else {
             throw new RuntimeException("Post not found");
        }
        return post ;
    }

    @Override
    public void deletePostById(int id) {
           postRepository.deleteById(id);
    }

    @Override
    public List<Post> searchPosts(String keyword) {
        return postRepository.findAll(keyword);
    }

    @Override
    public List<Post> getAllPostOrderByCreatedAt() {
        return postRepository.findAllOrderByCreatedAtAsc();
    }

    @Override
    public List<String> getDistinctAuthor() {
        return postRepository.findDistinctAuthor();
    }

    @Override
    public List<Post> getByAuthor(String author) {
        return postRepository.findByAuthor(author);
    }
}
