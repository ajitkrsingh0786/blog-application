package com.blog.blogapplication.dao;

import com.blog.blogapplication.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TagRepository extends JpaRepository<Tag,Integer> {
    Tag findByName(String name);
}
