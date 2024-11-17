package com.ddl.service;

import com.ddl.entity.User;
import com.ddl.controller.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public String register(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "Username already exists!";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // 在实际应用中需要加密
        userRepository.save(user);
        return "User registered successfully!";
    }

    public String login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> "Login successful!")
                .orElse("Invalid username or password!");
    }
}