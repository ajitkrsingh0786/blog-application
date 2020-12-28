package com.blog.blogapplication.service.implementation;

import com.blog.blogapplication.dao.UserRepository;
import com.blog.blogapplication.model.Tag;
import com.blog.blogapplication.model.User;
import com.blog.blogapplication.service.declaration.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(User user) {
           user.setRole("ROLE_AUTHOR");
            userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getTagById(int id) {
        User user;
        Optional<User> optional = userRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void deleteUserById(int id) {
         userRepository.deleteById(id);
    }
}
