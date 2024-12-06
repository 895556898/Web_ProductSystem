package com.ddl.entity.vo;

import lombok.Data;

@Data
public class ProductLogResultVO {
    private String status;
    private String msg;

    public ProductLogResultVO(String msg, String ok) {
        this.msg = msg;
        this.status = ok;
    }
}
