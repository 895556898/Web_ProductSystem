package com.ddl.entity.dto;

// 商品项数据传输对象
public class OrderItemDTO {
    private String productId; // 商品ID
    private int quantity;     // 数量
    private double price;     // 购买时单价 (重要：价格应以提交订单时的价格为准)

    // 构造函数、Getter 和 Setter
    public OrderItemDTO() {}

    public OrderItemDTO(String productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}