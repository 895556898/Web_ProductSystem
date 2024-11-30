package com.ddl.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import java.time.LocalDate;

@Data
@Table("product")
public class Product {
    @Id
    private Long id;                   //商品ID（条形码）

    private String name;
    private double price;
    private String Origin;              //产地（省份）
    private String Category;            //商品类别：日用品，食物，玩具……
    private int stock;                  //库存数量
    private String brand;               //品牌
    private LocalDate productionDate;   //生产日期
    private String shelfLife;           //保质期（n天），日用品 shelfLife为null

}
