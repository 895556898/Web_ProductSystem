package com.ddl.service;

import com.ddl.common.StatusCode;
import com.ddl.entity.dto.CheckoutDTO;
import com.ddl.entity.dto.OrderItemDTO;

import java.util.UUID; // 用于生成模拟的订单ID

// 订单服务类，处理订单相关的业务逻辑
public class OrderService {

    private static volatile OrderService instance;

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
        // 1. 基础验证
        if (username == null || username.trim().isEmpty()) {
            // 理论上Controller层应该已经校验过，但Service层也可以再做一层保障
            return new ProcessOrderResult(StatusCode.CHECKOUT_FAILURE_USER_NOT_AUTHENTICATED, null);
        }
        if (checkoutDTO == null || checkoutDTO.getItems() == null || checkoutDTO.getItems().isEmpty()) {
            return new ProcessOrderResult(StatusCode.CHECKOUT_FAILURE_EMPTY_CART, null);
        }

        // 2. 验证商品项数据有效性
        for (OrderItemDTO item : checkoutDTO.getItems()) {
            if (item.getProductId() == null || item.getProductId().trim().isEmpty() ||
                item.getQuantity() <= 0 || item.getPrice() < 0) {
                return new ProcessOrderResult(StatusCode.CHECKOUT_FAILURE_INVALID_ITEM, null);
            }
            // 模拟库存检查：这里可以根据 productId 查询库存
            // 例如: if (!productService.hasEnoughStock(item.getProductId(), item.getQuantity())) {
            //           return new ProcessOrderResult(StatusCode.CHECKOUT_FAILURE_INSUFFICIENT_STOCK, null);
            //       }
            // 此处简化，假设库存总是充足，除非是特定测试商品
            if ("PRODUCT_OUT_OF_STOCK".equals(item.getProductId())) {
                 return new ProcessOrderResult(StatusCode.CHECKOUT_FAILURE_INSUFFICIENT_STOCK, null);
            }
        }

        // 3. 计算订单总金额 (实际项目中可能更复杂，涉及优惠、税费等)
        double totalAmount = 0;
        for (OrderItemDTO item : checkoutDTO.getItems()) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
        System.out.println("用户 '" + username + "' 正在结账，商品列表: " + checkoutDTO.getItems() + "，总金额: " + totalAmount);

        // 4. 创建订单实体并保存到数据库 (模拟)
        // 在实际应用中，这里会有 Order order = new Order(); 设置其属性，然后 orderRepository.save(order);
        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(); // 生成一个模拟的订单ID
        System.out.println("为用户 '" + username + "' 创建订单成功，订单ID: " + orderId);

        // 5. (可选) 扣减库存
        // for (OrderItemDTO item : checkoutDTO.getItems()) {
        //     productService.decreaseStock(item.getProductId(), item.getQuantity());
        // }

        // 6. (可选) 创建支付记录、发送通知等

        // 7. 返回成功状态和订单ID
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