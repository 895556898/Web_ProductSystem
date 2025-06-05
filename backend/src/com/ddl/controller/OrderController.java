package com.ddl.controller;

import com.ddl.common.StatusCode;
import com.ddl.entity.dto.CheckoutDTO;
import com.ddl.entity.vo.GenericResultVO; // 使用我们定义的通用VO
import com.ddl.service.OrderService;
import io.javalin.http.Context;

// 订单控制器，处理订单相关的HTTP请求
public class OrderController {

    private final static OrderService orderService = OrderService.getInstance();
    // 从UserController中借鉴的SESSION_KEY，用于获取当前登录用户名
    // 注意：更好的做法可能是将 SESSION_KEY 和 "username" 作为公共常量定义
    private final static String SESSION_USERNAME_KEY = "username";

    /**
     * 处理前端发送的结账请求 (POST /api/checkout)
     * @param ctx Javalin的HTTP上下文对象
     */
    public static void checkout(Context ctx) {
        String username = ctx.sessionAttribute(SESSION_USERNAME_KEY);
        if (username == null) {
            ctx.status(StatusCode.CHECKOUT_FAILURE_USER_NOT_AUTHENTICATED.getCode());
            ctx.json(new GenericResultVO(StatusCode.CHECKOUT_FAILURE_USER_NOT_AUTHENTICATED.getMsg(), "error", null));
            return;
        }

        //获取请求体中的结账数据
        CheckoutDTO checkoutDTO;
        try {
            checkoutDTO = ctx.bodyAsClass(CheckoutDTO.class);
        } catch (Exception e) {
            //请求体解析失败
            ctx.status(StatusCode.INTERNAL_SERVER_ERROR.getCode());
            ctx.json(new GenericResultVO("请求数据格式错误: " + e.getMessage(), "error", null));
            return;
        }

        OrderService.ProcessOrderResult result = orderService.processCheckout(checkoutDTO, username);
        StatusCode statusCode = result.getStatusCode();

        if (statusCode == StatusCode.CHECKOUT_SUCCESS) {
            // 结账成功
            ctx.status(statusCode.getCode());
            ctx.json(new GenericResultVO(statusCode.getMsg(), "ok", result.getOrderId()));
        } else {
            // 结账失败
            ctx.status(statusCode.getCode());
            ctx.json(new GenericResultVO(statusCode.getMsg(), "error", null));
        }
    }

    // 未来可以添加其他订单相关的方法，例如：
    // public static void getOrderDetails(Context ctx) { /* ... */ }
    // public static void listUserOrders(Context ctx) { /* ... */ }
}