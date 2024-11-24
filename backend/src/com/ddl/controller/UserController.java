package com.ddl.controller;

import com.ddl.common.StatusCode;
import com.ddl.entity.User;
import com.ddl.service.UserService;
import io.javalin.http.Context;


import java.util.UUID;

public class UserController {
    private final static UserService userService = UserService.getInstance();
    private final static String SESSION_KEY = "SESSION_ID";

    public static void userRegister(Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        ctx.result(userService.register(user.getUsername(), user.getPassword()).getMsg());

    }

    public static void userLogin(Context ctx) {
        User user = ctx.bodyAsClass(User.class);

        if(userService.login(user.getUsername(), user.getPassword()) == StatusCode.LOGIN_SUCCESS){
            String sessionId = UUID.randomUUID().toString();
            ctx.sessionAttribute(SESSION_KEY, sessionId);
            ctx.header("Authorization", "Bearer " + sessionId);
            ctx.result(userService.login(user.getUsername(), user.getPassword()).getMsg());
        }else{
            ctx.result(userService.login(user.getUsername(), user.getPassword()).getMsg());
        }
    }
}