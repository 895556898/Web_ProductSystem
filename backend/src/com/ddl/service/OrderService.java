package com.ddl.service;

import com.ddl.common.StatusCode;
import com.ddl.entity.dto.CheckoutDTO;
import com.ddl.entity.dto.OrderItemDTO;
import com.ddl.service.ProductService;

import java.util.UUID; // 用于生成模拟的订单ID

// 订单服务类，处理订单相关的业务逻辑
public class OrderService {

    private static volatile OrderService instance;
    private final ProductService productService = ProductService.getInstance();

    // 私有构造函数，防止外部实例化 (单例模式)
    private OrderService() {
        // 初始化代码，例如数据库连接等（如果需要）
    }

    // 获取OrderService的单例
    public static OrderService getInstance() {
        if (instance == null) {
            synchronized (OrderService.class) {
                if (instance == null) {
                    instance = new OrderService();
                }
            }
        }
        return instance;
    }

    /**
     * 处理结账请求，创建订单
     * @param checkoutDTO 包含订单项的结账数据
     * @param username 当前操作的用户名 (用于关联订单和用户)
     * @return 操作结果的状态码，如果成功，其中可能包含订单ID等信息（此处通过StatusCode的msg传递，或修改方法签名返回更复杂的对象）
     */
    public ProcessOrderResult processCheckout(CheckoutDTO checkoutDTO, String username) {
        if (username == null || username.trim().isEmpty()) {
            return new ProcessOrderResult(StatusCode.CHECKOUT_FAILURE_USER_NOT_AUTHENTICATED, null);
        }
        if (checkoutDTO == null || checkoutDTO.getItems() == null || checkoutDTO.getItems().isEmpty()) {
            return new ProcessOrderResult(StatusCode.CHECKOUT_FAILURE_EMPTY_CART, null);
        }

        //验证商品项数据有效性
        for (OrderItemDTO item : checkoutDTO.getItems()) {
            if (item.getProductId() == null || item.getProductId().trim().isEmpty() ||
                item.getQuantity() <= 0 || item.getPrice() < 0) {
                return new ProcessOrderResult(StatusCode.CHECKOUT_FAILURE_INVALID_ITEM, null);
            }
            if ("PRODUCT_OUT_OF_STOCK".equals(item.getProductId())) {
                 return new ProcessOrderResult(StatusCode.CHECKOUT_FAILURE_INSUFFICIENT_STOCK, null);
            }
        }

        //计算订单总金额
        double totalAmount = 0;
        for (OrderItemDTO item : checkoutDTO.getItems()) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
        System.out.println("用户 '" + username + "' 正在结账，商品列表: " + checkoutDTO.getItems() + "，总金额: " + totalAmount);

        //创建订单实体并保存到数据库
        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

//        //扣减库存
//         for (OrderItemDTO item : checkoutDTO.getItems()) {
//             productService.decreaseStock(item.getProductId(), item.getQuantity());
//         }

        return new ProcessOrderResult(StatusCode.CHECKOUT_SUCCESS, orderId);
    }

    /**
     * 辅助类，用于封装 processCheckout 的返回结果，包含StatusCode和可能的额外数据（如订单ID）
     */
    public static class ProcessOrderResult {
        private final StatusCode statusCode;
        private final String orderId;

        public ProcessOrderResult(StatusCode statusCode, String orderId) {
            this.statusCode = statusCode;
            this.orderId = orderId;
        }

        public StatusCode getStatusCode() {
            return statusCode;
        }

        public String getOrderId() {
            return orderId;
        }
    }
}