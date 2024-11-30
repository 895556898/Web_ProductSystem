package com.ddl.entity.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private String origin;              //产地（省份）
    private String Category;            //商品类别：日用品，食物，玩具……
    private int stock;                  //库存数量
    private String brand;               //品牌
    private LocalDate productionDate;   //生产日期
    private String shelfLife;           //保质期（n天），日用品 shelfLife为null
}
