package com.ddl.controller;

import io.javalin.http.Context;

/**
 * @author Shanwer
 * @createDate: 2024/12/8 17:38
 */
public class BaseController {
    public static String getUsername(Context ctx){
        String username = ctx.sessionAttribute("username");
        return username != null ? username : ctx.cookie("username");
    }
}
