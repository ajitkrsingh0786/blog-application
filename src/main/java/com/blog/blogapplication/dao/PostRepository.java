package com.blog.blogapplication.dao;

import com.blog.blogapplication.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.Table;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
}
