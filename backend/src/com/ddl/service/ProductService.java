package com.ddl.service;

import com.ddl.common.StatusCode;
import com.ddl.entity.Product;
import com.ddl.mapper.ProductMapper;

public class ProductService {
    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    //添加商品
    public StatusCode addProduct(Product product) {
        try {
            int affectedRows = productMapper.insert(product);
            if (affectedRows > 0) {
                return StatusCode.PRODUCT_ADD_SUCCESS;
            } else {
                return StatusCode.PRODUCT_ADD_FAILED;
            }
        } catch (Exception e) {
            // Log the exception
            return StatusCode.PRODUCT_ADD_FAILED;
        }
    }

    //删除商品
    public StatusCode deleteProduct(Long id) {
        try {
            int affectedRows = productMapper.deleteById(id);
            if (affectedRows > 0) {
                return StatusCode.PRODUCT_DELETE_SUCCESS;
            } else {
                return StatusCode.PRODUCT_DELETE_FAILED;
            }
        } catch (Exception e) {
            // Log the exception
            return StatusCode.PRODUCT_DELETE_FAILED;
        }
    }
}
