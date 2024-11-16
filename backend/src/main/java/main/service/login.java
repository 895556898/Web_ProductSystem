package main.java.main.service;

import java.util.Scanner;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class login {
    public static void main(String[] args) {
        // 创建 Javalin 实例
        Javalin app = Javalin.create(config -> {
            // 可以在这里进行其他配置（如启用压缩、配置静态文件等）
        });

        // 设置全局中间件以指定默认响应类型
        app.before(ctx -> ctx.contentType("application/json"));

        // 启动服务器
        app.start(7000);

        // 定义简单路由
        app.get("/", ctx -> ctx.result("{\"message\": \"Hello, Javalin!\"}"));
    }
}
