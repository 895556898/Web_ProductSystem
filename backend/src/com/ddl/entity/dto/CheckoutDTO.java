package com.ddl.entity.dto;

import java.util.List;

// 结账数据传输对象
public class CheckoutDTO {
    private List<OrderItemDTO> items; // 订单中的商品项列表
    // 可以根据需要添加其他字段，如：
    // private String shippingAddressId;
    // private String couponCode;

    // 构造函数、Getter 和 Setter
    public CheckoutDTO() {}

    public CheckoutDTO(List<OrderItemDTO> items) {
        this.items = items;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CheckoutDTO{" +
                "items=" + items +
                '}';
    }
}