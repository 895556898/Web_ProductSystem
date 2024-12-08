 package com.ddl;

import com.alibaba.druid.pool.DruidDataSource;
import com.ddl.controller.ProductController;
import com.ddl.controller.ProductLogController;
import com.ddl.controller.UserController;
import com.ddl.interceptor.SessionInterceptor;
import com.ddl.mapper.ProductLogMapper;
import com.ddl.mapper.ProductMapper;
import com.ddl.mapper.UserMapper;
import com.mybatisflex.core.MybatisFlexBootstrap;
import io.javalin.Javalin;


 public class Application {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        }).start(8080);

        app.get("/api/users/currentUser", UserController::getCurrentUser);
        app.post("/api/users/register", UserController::userRegister);
        app.post("/api/users/login", UserController::userLogin);
        app.post("/api/users/logout", UserController::userLogout);
        app.post("/api/products/list", ProductController::listProduct);
        app.post("/api/products/add", ProductController::productAdd);
        app.delete("/api/products/delete/{id}", ProductController::productDelete);
        app.post("/api/products/update",ProductController::productUpdate);
        app.post("/api/productLog/list", ProductLogController::listLog);

        // 拦截器
        SessionInterceptor sessionInterceptor = new SessionInterceptor();
        app.before("/api/*",  sessionInterceptor::handle);

        try (DruidDataSource dataSource = new DruidDataSource()) {
            dataSource.setUrl("jdbc:mysql://112.124.20.183:3306/Web_ProductSystem");
            dataSource.setUsername("root");
            dataSource.setPassword("vKQ8G3eC");
            MybatisFlexBootstrap.getInstance()
                    .setDataSource(dataSource)
                    .addMapper(UserMapper.class)
                    .addMapper(ProductMapper.class)
                    .addMapper(ProductLogMapper.class)
                    .start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Server running at http://localhost:8080/");
    }
}
