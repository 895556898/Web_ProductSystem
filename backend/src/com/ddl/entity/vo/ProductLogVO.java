package com.ddl.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.RelationManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Shanwer
 * @createDate: 2024/12/8 21:58
 */
@Data
public class ProductLogVO {
    @Id
    private int id;                                //日志ID
    private int itemId;                            //商品ID
    private String action;                          //修改操作
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;                    //创建时间
    private String createdBy;                       //创建者
    @RelationManyToOne(
            targetField = "id",
            targetTable = "product",
            valueField = "name",
            selfField = "itemId")
    private String name;
}
