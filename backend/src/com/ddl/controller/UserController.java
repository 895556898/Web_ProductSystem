package com.ddl.controller;

import com.ddl.entity.User;
import com.ddl.service.UserService;
import io.javalin.http.Context;

public class UserController {
    private final static UserService userService = UserService.getInstance();

    public static void userRegister(Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        ctx.result(userService.register(user.getUsername(), user.getPassword()).getMsg());

    }

    public static void userLogin(Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        ctx.result(userService.login(user.getUsername(), user.getPassword()).getMsg());
    };
}