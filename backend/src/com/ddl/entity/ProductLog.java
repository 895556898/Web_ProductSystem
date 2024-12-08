package com.ddl.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Table("product_log")
public class ProductLog {
    @Id
    private int id;                                //日志ID
    private int itemId;                            //商品ID
    private String action;                          //修改操作
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDate createdAt;                    //创建时间
    private String createdBy;                       //创建者
}
