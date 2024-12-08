package com.ddl.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProductDTO {
    private int id;
    private String name;
    private BigDecimal price;               //价格
    private BigDecimal cost;                //成本
    private String origin;              //产地（省份）
    private String category;            //商品类别：日用品，食物，玩具……
    private int stock;                  //库存数量
    private String brand;               //品牌
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate productionDate;   //生产日期
    private String shelfLife;           //保质期（n天），日用品 shelfLife为null
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;
}
