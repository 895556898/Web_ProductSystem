package com.ddl.controller;

import com.ddl.common.StatusCode;
import com.ddl.entity.dto.ProductLogDTO;
import com.ddl.entity.vo.ProductLogResultVO;
import com.ddl.service.ProductLogService;
import io.javalin.http.Context;

public class ProductLogController {
    private final static ProductLogService productLogService = ProductLogService.getInstance();

//    //商品日志查找
//    public static void productLodFind(Context ctx){
//        ProductLogDTO productLogDTO = ctx.bodyAsClass(ProductLogDTO.class);
//        StatusCode statusCode = productLogService.find(productLogDTO);
//        if(statusCode == StatusCode.PRODUCT_LOG_FOUND_SUCCESS){
//            ctx.json(new ProductLogResultVO(statusCode.getMsg(),"ok"));
//        } else {
//            ctx.json(new ProductLogResultVO(statusCode.getMsg(),"error"));
//        }
//    }
}
