package com.ddl.interceptor;

import io.javalin.http.Context;

public class SessionInterceptor {
    private final static String SESSION_KEY = "SESSION_ID";
    public void handle(Context ctx){
        //涉及登录或注册直接放行
        if(ctx.path().equals("/api/users/login") || ctx.path().equals("/api/users/register")){
            return;
        }

        String authHeader = ctx.header("Authorization");
        String sessionId;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sessionId = ctx.cookie(SESSION_KEY);
        }else{
            sessionId = authHeader.substring(7);
        }

        if(sessionId == null){
            ctx.status(401).result("Unauthorized: Missing or invalid token");
            return;
        }

        String storedSessionId = ctx.sessionAttribute(SESSION_KEY);

        if (storedSessionId == null || !storedSessionId.equals(sessionId)) {
            ctx.status(401).result("Unauthorized: Invalid session");
//            ctx.redirect("/api/users/login");
        }
    }
}