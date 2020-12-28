package com.blog.blogapplication.service.declaration;

import com.blog.blogapplication.model.Tag;
import com.blog.blogapplication.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);
    List<User> getAllUsers();
    User getTagById(int id);
    void deleteUserById(int id);
}
