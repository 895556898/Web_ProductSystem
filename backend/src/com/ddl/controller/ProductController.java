package com.ddl.controller;

import com.ddl.common.StatusCode;
import com.ddl.entity.Product;
import com.ddl.entity.dto.PageParamDTO;
import com.ddl.entity.dto.ProductDTO;
import com.ddl.entity.vo.ProductResultVO;
import com.ddl.service.ProductService;
import io.javalin.http.Context;

import java.util.Optional;

public class ProductController {

    private final static ProductService productService = ProductService.getInstance();

    //添加商品new
    public static void productAdd(Context ctx){
        ProductDTO productDTO = ctx.bodyAsClass(ProductDTO.class);
        String username = ctx.sessionAttribute("username");
        StatusCode statusCode = productService.Add(productDTO,username);
        if(statusCode == StatusCode.PRODUCT_ADD_SUCCESS){
            ctx.json(new ProductResultVO(statusCode.getMsg(), "ok"));
        } else
            ctx.json(new ProductResultVO(statusCode.getMsg(), "error"));
    }

    // 删除商品new
    public static void productDelete(Context ctx) {
        ProductDTO productDTO = ctx.bodyAsClass(ProductDTO.class);
        String username = ctx.sessionAttribute("username");
        StatusCode statusCode = productService.Delete(productDTO,username);
        if(statusCode == StatusCode.PRODUCT_DELETE_SUCCESS){
            ctx.json(new ProductResultVO(statusCode.getMsg(), "ok"));
        } else
            ctx.json(new ProductResultVO(statusCode.getMsg(), "error"));
   }

   // 更新商品信息
    public static void productUpdate(Context ctx) {
        ProductDTO productDTO = ctx.bodyAsClass(ProductDTO.class);
        String username = ctx.sessionAttribute("username");
        StatusCode statusCode = productService.Update(productDTO,username);
        if (statusCode == StatusCode.PRODUCT_UPDATE_SUCCESS)
            ctx.json(new ProductResultVO(statusCode.getMsg(), "ok"));
        else
            ctx.json(new ProductResultVO(statusCode.getMsg(), "error"));
    }

    //查询商品信息
    public void searchProduct(Context ctx) {
        String attribute = ctx.queryParam("attribute");
        String value = ctx.queryParam("value");

        Optional<Product> products = productService.searchProducts(attribute, value);
        ctx.json(products);
    }

    public static void listProduct(Context ctx){
        PageParamDTO pageParamDTO = ctx.bodyAsClass(PageParamDTO.class);
        ctx.json(productService.listProducts(pageParamDTO));
    }
}
