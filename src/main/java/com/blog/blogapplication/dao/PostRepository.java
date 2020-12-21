package com.blog.blogapplication.dao;

import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query(value = "SELECT post FROM Post post JOIN post.tags t WHERE  post.title LIKE %?1% " +
            "OR post.content LIKE %?1% OR post.author LIKE %?1% OR t.name LIKE %?1%")
    Page<Post> findAll(String keyword, Pageable pageable);

    List<Post> findByTags(Tag tag);
    @Query("SELECT post FROM Post post ORDER BY createdAt DESC")
    Page<Post> findAllOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT post FROM Post post ORDER BY createdAt ASC")
    Page<Post> findAllOrderByCreatedAtAsc(Pageable pageable);

    @Query("SELECT DISTINCT post.author FROM Post post")
    List<String> findDistinctAuthor();

    @Query(value = "SELECT post FROM Post post WHERE  post.author LIKE ?1")
    Page<Post> findByAuthor(String author, Pageable pageable);

    @Query("select post from Post post where post.createdAt >= ?1")
    Page<Post> findAllWithCreatedAtBefore(Date createdDate,Pageable pageable );
}
