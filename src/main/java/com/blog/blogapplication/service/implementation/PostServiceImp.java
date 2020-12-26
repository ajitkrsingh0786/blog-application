package com.blog.blogapplication.service.implementation;

import com.blog.blogapplication.dao.PostRepository;
import com.blog.blogapplication.dao.TagRepository;
import com.blog.blogapplication.dao.UserRepository;
import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.model.Tag;
import com.blog.blogapplication.service.declaration.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImp implements PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void savePost(Post post, String authorName) {
        String excerpt;
        Date date = Calendar.getInstance().getTime();
        post.setUpdatedAt(date);
        post.setUser(userRepository.findByName(authorName).get());

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
        List<Post> posts = postRepository.findAll();
        List<Integer> postsId = new ArrayList<>();
        for (Post post : posts) {
            postsId.add(post.getId());
        }
        Resources.resourcePostId = postsId;
        return posts;
    }

    @Override
    public Post getPostById(int id) {
        Optional<Post> optional = postRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void deletePostById(int id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> searchPosts(String keyword) {
        List<Integer> newPostsId = new ArrayList<>();
        List<Post> posts = postRepository.findAll(keyword);
        for (Post post : posts) {
            newPostsId.add(post.getId());
        }
        Resources.resourcePostId = newPostsId;
        return posts;
    }

    @Override
    public List<Post> getAllPostOrderByPublishedAt(String sortBy) {
        List<Post> newPostList = new ArrayList<>();
        List<Integer> newPostsId = new ArrayList<>();
        List<Post> posts = new ArrayList<>();

        if (sortBy.equals("asc")) {
            posts = postRepository.findAllOrderByPublishedAtAsc();
        } else {
            posts = postRepository.findAllOrderByPublishedAtDesc();
        }

        for (Post post : posts) {
            if (Resources.resourcePostId.contains(post.getId())) {
                newPostList.add(post);
                newPostsId.add(post.getId());
            }
        }
        Resources.resourcePostId = newPostsId;
        return newPostList;
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
    public List<Post> getAllWithPublishedAtBefore(String selected) {
        return null;
    }

    @Override
    public Page<Post> getPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> getPostsByTag(Tag tag, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.postRepository.findByTags(tag, pageable);
    }

    @Override
    public List<Post> getByAuthor(String authors) {
        List<Post> newPostList = new ArrayList<>();
        List<Integer> newPostsId = new ArrayList<>();
        String authorsArray[] = authors.split(",");

        for (String author : authorsArray) {
            List<Post> postList = postRepository.findByAuthor(author);
            for (Post post : postList) {
                if (!newPostsId.contains(post.getId()) && Resources.resourcePostId.contains(post.getId())) {
                    newPostList.add(post);
                    newPostsId.add(post.getId());
                }
            }
        }
        Resources.resourcePostId = newPostsId;
        return newPostList;
    }

    @Override
    public List<Post> getByFilters(String authors, String tags, String sortBy) {
        System.out.println("authors : " + authors);
        System.out.println("tags : " + tags);
        Set<Integer> postsId = new HashSet<>();
        List<Post> postList = new ArrayList<>();

        if (authors.length() >= 1) {
            String[] authorsName = authors.split(",");
            for (String author : authorsName) {
                if (author.length() > 0) {
                    for (Post post : postRepository.findByAuthor(author)) {
                        postsId.add(post.getId());
                    }
                }
            }
        }

        if (tags.length() >= 1) {
            String[] tagsName = tags.split(",");
            for (String tagName : tagsName) {
                if (tagName.length() > 0) {
                    for (Post post : tagRepository.findAllPostsByName(tagName)) {
                        postsId.add(post.getId());
                    }
                }
            }
        }

        if (!sortBy.equals("")) {
            Calendar instantDate = Calendar.getInstance();
            ArrayList<Integer> postIdSortBy = new ArrayList<>();
            if (sortBy.equals("lastDay")) {
                instantDate.add(Calendar.DAY_OF_YEAR, -1);
            }
            if (sortBy.equals("lastWeek")) {
                instantDate.add(Calendar.WEEK_OF_YEAR, -1);
            }
            Date publishedDate = instantDate.getTime();
            for (Post post : postRepository.findAllWithPublishedAtBefore(publishedDate)) {
                if (postsId.size() > 0 && postsId.contains(post.getId()) && Resources.resourcePostId.contains(post.getId())) {
                    postIdSortBy.add(post.getId());
                } else {
                    if (postsId.size() == 0 && Resources.resourcePostId.contains(post.getId())) {
                        postIdSortBy.add(post.getId());
                    }
                }

            }

            for (int postId : postIdSortBy) {
                Optional<Post> optional = postRepository.findById(postId);
                optional.ifPresent(postList::add);
            }
            Resources.resourcePostId = postIdSortBy;
            return postList;
        }

        for (int postId : postsId) {
            if (Resources.resourcePostId.contains(postId)) {
                Optional<Post> optional = postRepository.findById(postId);
                optional.ifPresent(postList::add);
            }
        }
        Resources.resourcePostId = new ArrayList<>(postsId);
        return postList;
    }
}