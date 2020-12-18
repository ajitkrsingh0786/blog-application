package com.blog.blogapplication.dao;

import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query(value = "SELECT post FROM Post post WHERE  post.title LIKE %?1% " +
            "OR post.content LIKE %?1% OR post.author LIKE %?1% ")
    List<Post> findAll(String keyword);

    List<Post> findByTags(Tag tag);
    @Query("SELECT post FROM Post post ORDER BY createdAt DESC")
    List<Post> findAllOrderByCreatedAtAsc();

    @Query("SELECT DISTINCT post.author FROM Post post")
    List<String> findDistinctAuthor();

    @Query(value = "SELECT post FROM Post post WHERE  post.author LIKE ?1")
    List<Post> findByAuthor(String author);



}
