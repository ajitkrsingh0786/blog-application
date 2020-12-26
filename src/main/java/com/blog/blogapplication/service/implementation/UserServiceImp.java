package com.blog.blogapplication.service.implementation;

import com.blog.blogapplication.dao.UserRepository;
import com.blog.blogapplication.model.User;
import com.blog.blogapplication.service.declaration.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;


    @Override
    public void registerUser(User user) {
           user.setRole("ROLE_AUTHOR");
            userRepository.save(user);
    }
}
