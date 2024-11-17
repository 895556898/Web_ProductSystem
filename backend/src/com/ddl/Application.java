package com.ddl;

import com.alibaba.druid.pool.DruidDataSource;
import com.ddl.controller.UserController;
import com.ddl.mapper.UserMapper;
import com.mybatisflex.core.MybatisFlexBootstrap;
import io.javalin.Javalin;

public class Application {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        }).start(8080);


        app.post("/api/users/register", UserController::userRegister);
        app.post("/api/users/login", UserController::userLogin);

        try (DruidDataSource dataSource = new DruidDataSource()) {
            dataSource.setUrl("jdbc:mysql://112.124.20.183:3306/Web_ProductSystem");
            dataSource.setUsername("root");
            dataSource.setPassword("vKQ8G3eC");
            MybatisFlexBootstrap.getInstance()
                    .setDataSource(dataSource)
                    .addMapper(UserMapper.class)
                    .start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Server running at http://localhost:8080/");
    }
}
