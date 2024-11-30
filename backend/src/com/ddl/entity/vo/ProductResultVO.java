package com.ddl.entity.vo;

import lombok.Data;

@Data
public class ProductResultVO {
    private String status;
    private String msg;

    public ProductResultVO(String msg, String status){
        this.msg = msg;
        this.status = status;
    }

}
