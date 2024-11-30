package com.ddl.controller;

import com.ddl.common.StatusCode;
import com.ddl.entity.Product;
import com.ddl.entity.dto.ProductDTO;
import com.ddl.service.ProductService;
import io.javalin.http.Context;

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 添加商品
    public void addProduct(Context ctx) {
        ProductDTO productDTO = ctx.bodyAsClass(ProductDTO.class);
        Product product = new Product();
        // 将 ProductDTO 转换为 Product 实体，设置属性
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setOrigin(productDTO.getOrigin());
        product.setCategory(productDTO.getCategory());
        product.setStock(productDTO.getStock());
        product.setBrand(productDTO.getBrand());
        product.setProductionDate(productDTO.getProductionDate());
        product.setShelfLife(productDTO.getShelfLife());

        StatusCode statusCode = productService.addProduct(product);
        if (statusCode == StatusCode.PRODUCT_ADD_SUCCESS)
            ctx.json(new com.ddl.entity.vo.ProductResultVO(statusCode.getMsg(), "ok"));
        else ctx.json(new com.ddl.entity.vo.ProductResultVO(statusCode.getMsg(), "error"));
    }

    // 删除商品
    public void deleteProduct(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        StatusCode statusCode = productService.deleteProduct(id);
        if (statusCode == StatusCode.PRODUCT_DELETE_SUCCESS)
            ctx.json(new com.ddl.entity.vo.ProductResultVO(statusCode.getMsg(), "ok"));
        else ctx.json(new com.ddl.entity.vo.ProductResultVO(statusCode.getMsg(), "error"));
   }
}
