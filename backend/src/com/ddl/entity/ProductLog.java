package com.ddl.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Table("product_log")
public class ProductLog {
    @Id
    private long id;                                //日志ID

    private long itemId;                            //商品ID
    private String itemName;                        //商品名称
    private String action;                          //修改操作
    private LocalDate createdAt;                    //创建时间
    private String createdBy;                       //创建者
}
