package com.ddl.controller;

import com.ddl.common.StatusCode;
import com.ddl.entity.dto.PageParamDTO;
import com.ddl.entity.dto.ProductDTO;
import com.ddl.entity.vo.ProductResultVO;
import com.ddl.service.ProductService;
import io.javalin.http.Context;

public class ProductController extends BaseController{

    private final static ProductService productService = ProductService.getInstance();

    //添加商品
    public static void productAdd(Context ctx){
        ProductDTO productDTO = ctx.bodyAsClass(ProductDTO.class);
        String username = getUsername(ctx);
        StatusCode statusCode = productService.addProduct(productDTO,username);
        if(statusCode == StatusCode.PRODUCT_ADD_SUCCESS){
            ctx.json(new ProductResultVO(statusCode.getMsg(), "ok"));
        } else
            ctx.json(new ProductResultVO(statusCode.getMsg(), "error"));
    }

    // 删除商品
    public static void productDelete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        String username = getUsername(ctx);
        StatusCode statusCode = productService.delete(id, username);
        if (statusCode == StatusCode.PRODUCT_DELETE_SUCCESS) {
            ctx.json(new ProductResultVO(statusCode.getMsg(), "ok"));
        } else
            ctx.json(new ProductResultVO(statusCode.getMsg(), "error"));
    }

   // 更新商品信息
    public static void productUpdate(Context ctx) {
        ProductDTO productDTO = ctx.bodyAsClass(ProductDTO.class);
        String username = getUsername(ctx);
        StatusCode statusCode = productService.Update(productDTO,username);
        if (statusCode == StatusCode.PRODUCT_UPDATE_SUCCESS)
            ctx.json(new ProductResultVO(statusCode.getMsg(), "ok"));
        else
            ctx.json(new ProductResultVO(statusCode.getMsg(), "error"));
    }

    //查询商品列表
    public static void listProduct(Context ctx){
        PageParamDTO pageParamDTO = ctx.bodyAsClass(PageParamDTO.class);
        ctx.json(productService.listProducts(pageParamDTO));
    }
}
