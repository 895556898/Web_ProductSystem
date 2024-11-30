package com.ddl.controller;

import com.ddl.common.StatusCode;
import com.ddl.entity.User;
import com.ddl.entity.dto.UpdatePasswordDTO;
import com.ddl.entity.dto.UserDTO;
import com.ddl.entity.vo.LoginResultVO;
import com.ddl.service.UserService;
import io.javalin.http.Context;


import java.util.UUID;

public class UserController {
    private final static UserService userService = UserService.getInstance();
    private final static String SESSION_KEY = "SESSION_ID";

    public static void userRegister(Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        ctx.result(userService.register(user.getUsername(), user.getHashedPassword()).getMsg());
    }

    public static void userLogin(Context ctx) {
        UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);
        StatusCode statusCode = userService.login(userDTO);
        if(statusCode == StatusCode.LOGIN_SUCCESS){
            String sessionId = UUID.randomUUID().toString();
            ctx.sessionAttribute(SESSION_KEY, sessionId);
            ctx.sessionAttribute("username", userDTO.getUsername());
            ctx.header("Authorization", "Bearer " + sessionId);
            if(userDTO.isAutoLogin()){
                ctx.cookie(SESSION_KEY, sessionId, 60 * 60 * 24 * 7);//硬编码1下，生命周期7天
            }
            ctx.json(new LoginResultVO(statusCode.getMsg(), "ok", sessionId));
        }else{
            ctx.json(new LoginResultVO(statusCode.getMsg(), "error", null));
        }
    }

    //用户登出
    public static void userLogout(Context ctx) {
        ctx.sessionAttribute(SESSION_KEY, null);
        ctx.result("logout success");
    }

    //获取当前用户信息
    public static void getCurrentUser(Context ctx) {
        ctx.json(userService.getCurrentUser(ctx.sessionAttribute("username")));
    }

    //用户修改密码
    public static void updatePassword(Context ctx){
        UpdatePasswordDTO updatePasswordDTO = ctx.bodyAsClass(UpdatePasswordDTO.class);
        StatusCode statusCode = userService.updatePassword(updatePasswordDTO);
        if (statusCode == StatusCode.PASSWORD_UPDATE_SUCCESS)
            ctx.json(new LoginResultVO(statusCode.getMsg(), "ok", null));
        else ctx.json(new LoginResultVO(statusCode.getMsg(), "error", null));
    }

    //用户注销账户
    public static void deleteUserAccount(Context ctx) {
        String username = ctx.sessionAttribute("username");
        if (username == null) {
            ctx.status(401).result("Unauthorized");
            return;
        }
        StatusCode statusCode = userService.deleteUserByUsername(username);
        ctx.json(new LoginResultVO(statusCode.getMsg(), statusCode == StatusCode.ACCOUNT_DELETION_SUCCESS ? "ok" : "error", null));
    }

}