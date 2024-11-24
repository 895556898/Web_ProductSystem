package com.ddl.interceptor;

import io.javalin.http.Context;

public class SessionInterceptor {
    private final static String SESSION_KEY = "SESSION_ID";
    public void handle(Context ctx){
        String authHeader = ctx.header("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ctx.status(401).result("Unauthorized: Missing or invalid token");
            ctx.redirect("/api/users/login"); // 重定向到登录页面
            return;
        }

        String sessionId = authHeader.substring(7);
        String storedSessionId = ctx.sessionAttribute(SESSION_KEY);

        if (storedSessionId == null || !storedSessionId.equals(sessionId)) {
            ctx.status(401).result("Unauthorized: Invalid session");
            ctx.redirect("/api/users/login");
            return;
        }
    }
}