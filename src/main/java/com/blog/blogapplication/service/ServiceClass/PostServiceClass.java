package com.blog.blogapplication.service.ServiceClass;

import com.blog.blogapplication.dao.PostRepository;
import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.service.Interface.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceClass implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public void savePost(Post post) {
        String excerpt;
        Date date = Calendar.getInstance().getTime();

        post.setUpdatedAt(date);
        post.setPublished(true);

        if (post.getCreatedAt() == null) {
            post.setCreatedAt(date);
            post.setPublishedAt(date);
        }

        if (post.getContent().length() > 200) {
            excerpt = post.getContent().substring(0, 200) + "...";
        } else {
            excerpt = post.getContent();
        }

        post.setExcerpt(excerpt);
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
        if (optional.isPresent()) {
            post = optional.get();
        } else {
            throw new RuntimeException("Post not found");
        }
        return post;
    }

    @Override
    public void deletePostById(int id) {
        postRepository.deleteById(id);
    }

    @Override
    public Page<Post> searchPosts(String keyword, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return postRepository.findAll(keyword, pageable);
    }

    @Override
    public Page<Post> getAllPostOrderByCreatedAt(String sortBy, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        if (sortBy.equals("asc")) {
            return postRepository.findAllOrderByCreatedAtAsc(pageable);
        }

        return postRepository.findAllOrderByCreatedAtDesc(pageable);
    }

    @Override
    public List<String> getDistinctAuthor() {
        return postRepository.findDistinctAuthor();
    }

    @Override
    public Page<Post> getByAuthor(String author, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return postRepository.findByAuthor(author, pageable);
    }

    @Override
    public Page<Post> findAllWithCreatedAtBefore(String selected, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Calendar instantDate = Calendar.getInstance();

        if (selected.equals("lastDay")) {
            instantDate.add(Calendar.DAY_OF_YEAR, -1);
        } else {
            instantDate.add(Calendar.WEEK_OF_YEAR, -1);
        }

        Date createdDate = instantDate.getTime();
        return postRepository.findAllWithCreatedAtBefore(createdDate, pageable);
    }

    @Override
    public Page<Post> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.postRepository.findAll(pageable);
    }


}
