package com.ddl.service;

import com.ddl.common.StatusCode;
import com.ddl.entity.Product;
import com.ddl.entity.dto.ProductDTO;
import com.ddl.mapper.ProductMapper;

import java.util.Optional;

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
            return StatusCode.PRODUCT_DELETE_FAILED;
        }
    }

    //更新商品信息
    public StatusCode updateProduct(ProductDTO productDTO) {
        Product product = new Product();

        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setCost(productDTO.getCost());
        product.setOrigin(productDTO.getOrigin());
        product.setCategory(productDTO.getCategory());
        product.setStock(productDTO.getStock());
        product.setBrand(productDTO.getBrand());
        product.setProductionDate(productDTO.getProductionDate());
        product.setShelfLife(productDTO.getShelfLife());

        long id = product.getId();
        try {
            int affectedRows = productMapper.updateById(id);
            if (affectedRows > 0) {
                return StatusCode.PRODUCT_UPDATE_SUCCESS;
            } else {
                return StatusCode.PRODUCT_UPDATE_FAILED;
            }
        } catch (Exception e) {
            return StatusCode.PRODUCT_UPDATE_FAILED;
        }
    }

    //查询商品信息
    public Optional<Product> searchProducts(String attribute, String value) {
        return productMapper.selectByAttribute(attribute, value);
    }
}
