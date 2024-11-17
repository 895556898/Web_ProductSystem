package com.ddl;

import com.ddl.controller.UserController;
import io.javalin.Javalin;

public class Application {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        }).start(8080);

        UserController userController = new UserController();

        app.post("/api/users/register", userController.registerUser);
        app.post("/api/users/login", userController.loginUser);

        System.out.println("Server running at http://localhost:8080/");
    }
}
