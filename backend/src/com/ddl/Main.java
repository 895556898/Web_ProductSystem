package com.ddl;

import com.ddl.entity.User;
import com.mybatisflex.core.MybatisFlexBootstrap;
import io.javalin.Javalin;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

public class Main {
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

        //配置Mybatis-Flex ORM，暂时使用H2做测试
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();

        MybatisFlexBootstrap bootstrap = MybatisFlexBootstrap.getInstance()
                .setDataSource(dataSource)
//                .setLogImpl(StdOutImpl.class)
                .addMapper(User.class)
                .start();
    }
}
