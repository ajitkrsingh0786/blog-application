package com.blog.blogapplication.dao;

import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TagRepository extends JpaRepository<Tag, Integer> {

    Tag findByName(String name);

    @Query("SELECT DISTINCT tag.posts FROM Tag tag WHERE tag.id=?1")
    Page<Post> findAllPostsById(int id, Pageable pageable);

    @Query("SELECT DISTINCT tag.posts FROM Tag tag WHERE tag.name=?1")
    List<Post> findAllPostsByName(String name);

}
