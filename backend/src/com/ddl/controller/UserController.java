package com.ddl.controller;

import com.ddl.entity.User;
import com.ddl.controller.UserRepository;
import io.javalin.http.Handler;

public class UserController {
    private final UserRepository userRepository = new UserRepository();

    public Handler registerUser = ctx -> {
        User user = ctx.bodyAsClass(User.class);
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            ctx.status(400).result("用户名已存在");
        } else {
            userRepository.save(user);
            ctx.result("注册成功");
        }
    };

    public Handler loginUser = ctx -> {
        User user = ctx.bodyAsClass(User.class);
        userRepository.findByUsername(user.getUsername()).ifPresentOrElse(
            existingUser -> {
                if (existingUser.getPassword().equals(user.getPassword())) {
                    ctx.result("登录成功");
                } else {
                    ctx.status(400).result("密码错误");
                }
            },
            () -> ctx.status(404).result("用户不存在")
        );
    };
}