package com.ddl.controller;

import com.ddl.common.StatusCode;
import com.ddl.entity.Product;
import com.ddl.entity.dto.ProductDTO;
import com.ddl.service.ProductService;
import io.javalin.http.Context;

import java.util.Optional;

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 添加商品
    public void addProduct(Context ctx) {
        ProductDTO productDTO = ctx.bodyAsClass(ProductDTO.class);
        Product product = new Product();

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

   // 更新商品信息
    public void updateProduct(Context ctx) {
        ProductDTO productDTO = ctx.bodyAsClass(ProductDTO.class);
        StatusCode statusCode = productService.updateProduct(productDTO);
        if (statusCode == StatusCode.PRODUCT_UPDATE_SUCCESS)
            ctx.json(new com.ddl.entity.vo.ProductResultVO(statusCode.getMsg(), "ok"));
        else ctx.json(new com.ddl.entity.vo.ProductResultVO(statusCode.getMsg(), "error"));
    }

    //查询商品信息
    public void searchProduct(Context ctx) {
        String attribute = ctx.queryParam("attribute");
        String value = ctx.queryParam("value");

        Optional<Product> products = productService.searchProducts(attribute, value);
        ctx.json(products);
    }
}
