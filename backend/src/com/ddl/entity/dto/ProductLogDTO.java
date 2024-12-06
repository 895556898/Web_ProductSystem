package com.ddl.entity.dto;

import com.mybatisflex.annotation.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductLogDTO {
    @Id
    private long id;                                //日志ID

    private long itemId;                            //商品ID
    private String itemName;                        //商品名称
    private String action;                          //修改操作
    private LocalDate createdAt;                    //创建时间
    private String createdBy;                       //创建者
}
