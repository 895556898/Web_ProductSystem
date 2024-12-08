package com.ddl.service;

import com.ddl.common.StatusCode;
import com.ddl.entity.Product;
import com.ddl.entity.ProductLog;
import com.ddl.entity.dto.PageParamDTO;
import com.ddl.entity.dto.ProductDTO;
import com.ddl.mapper.ProductLogMapper;
import com.ddl.mapper.ProductMapper;
import com.mybatisflex.core.MybatisFlexBootstrap;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.paginate.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductService {

    ProductMapper productMapper = MybatisFlexBootstrap.getInstance().getMapper(ProductMapper.class);
    ProductLogMapper productLogMapper = MybatisFlexBootstrap.getInstance().getMapper(ProductLogMapper.class);

    public static ProductService getInstance() {
        return new ProductService();
    }

    //添加商品new
    public StatusCode addProduct(ProductDTO productDTO, String username) {
        Product product = new Product();

        AtomicInteger affectedRows1 = new AtomicInteger();           //商品表中受影响的行数
        AtomicInteger affectedRows2 = new AtomicInteger();

        Optional.ofNullable(productDTO.getName()).ifPresent(product::setName);
        Optional.ofNullable(productDTO.getPrice()).ifPresent(product::setPrice);
        Optional.ofNullable(productDTO.getCost()).ifPresent(product::setCost);
        Optional.ofNullable(productDTO.getOrigin()).ifPresent(product::setOrigin);
        Optional.ofNullable(productDTO.getCategory()).ifPresent(product::setCategory);
        Optional.of(productDTO.getStock()).ifPresent(product::setStock);
        Optional.ofNullable(productDTO.getBrand()).ifPresent(product::setBrand);
        Optional.ofNullable(productDTO.getProductionDate()).ifPresent(product::setProductionDate);
        Optional.ofNullable(productDTO.getShelfLife()).ifPresent(product::setShelfLife);

        Db.tx(() -> {
            //进行事务操作
            Db.tx(() -> {
                affectedRows1.set(productMapper.insertSelective(product));//这样可以使用created_at默认值
                return affectedRows1.get() > 0;
            });

            ProductLog productLog = new ProductLog();
            productLog.setItemId(product.getId());
            productLog.setItemName(product.getName());
            productLog.setAction("添加商品");
            productLog.setCreatedBy(username);
            affectedRows2.set(productLogMapper.insertSelective(productLog));
            return affectedRows1.get() > 0 && affectedRows2.get() > 0;
        });

        if (affectedRows1.get() > 0 && affectedRows2.get() > 0) {
            return StatusCode.PRODUCT_ADD_SUCCESS;
        } else {
            return StatusCode.PRODUCT_ADD_FAILED;
        }
    }

    //删除商品new
    public StatusCode delete(int id , String username) {
        Product product = productMapper.selectOneById(id);
        if(product == null){
            return StatusCode.PRODUCT_NOT_FOUND;
        }

        String name = product.getName();
        AtomicInteger affectedRows1 = new AtomicInteger();           //商品表中受影响的行数
        AtomicInteger affectedRows2 = new AtomicInteger();

        ProductLog productLog = new ProductLog();
        productLog.setItemId(id);
        productLog.setItemName(name);
        productLog.setAction("删除商品");
        productLog.setCreatedBy(username);

        Db.tx(() -> {
            affectedRows1.set(productMapper.deleteById(id));
            affectedRows2.set(productLogMapper.insertSelective(productLog));
            return affectedRows1.get() > 0 && affectedRows2.get() > 0;
        });

        if (affectedRows1.get() > 0 && affectedRows2.get() > 0) {
            return StatusCode.PRODUCT_DELETE_SUCCESS;
        } else {
            return StatusCode.PRODUCT_DELETE_FAILED;
        }
    }

    //更新商品new
    public StatusCode Update(ProductDTO productDTO , String username) {
        int id = productDTO.getId();
        String name = productDTO.getName();
        BigDecimal price = productDTO.getPrice();
        BigDecimal cost = productDTO.getCost();
        String origin = productDTO.getOrigin();
        String category = productDTO.getCategory();
        int stock = productDTO.getStock();
        String brand = productDTO.getBrand();
        LocalDate productionDate = productDTO.getProductionDate();
        String shelfLife = productDTO.getShelfLife();

        AtomicInteger affectedRows1 = new AtomicInteger();           //商品表中受影响的行数
        AtomicInteger affectedRows2 = new AtomicInteger();

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setCost(cost);
        product.setOrigin(origin);
        product.setCategory(category);
        product.setStock(stock);
        product.setBrand(brand);
        product.setProductionDate(productionDate);
        product.setShelfLife(shelfLife);

        ProductLog productLog = new ProductLog();
        productLog.setItemId(id);
        productLog.setItemName(name);
        productLog.setAction("更新商品信息");
        productLog.setCreatedBy(username);

        Db.tx(() -> {
            affectedRows1.set(productMapper.update(product));
            affectedRows2.set(productLogMapper.insertSelective(productLog));
            return affectedRows1.get() > 0 && affectedRows2.get() > 0;
        });

        if (affectedRows1.get() > 0 && affectedRows2.get() > 0) {
            return StatusCode.PRODUCT_UPDATE_SUCCESS;
        } else {
            return StatusCode.PRODUCT_UPDATE_FAILED;
        }
    }

    //查询商品信息
    public Optional<Product> searchProducts(String attribute, String value) {
        return productMapper.selectByAttribute(attribute, value);
    }

    public Page<Product> listProducts(PageParamDTO pageParam){
        QueryWrapper queryWrapper = new QueryWrapper();
        Optional.ofNullable(pageParam)
                .ifPresent(param -> {
                    Optional.ofNullable(param.getName())
                            .ifPresent(name -> queryWrapper.like("name", name));
                    Optional.ofNullable(param.getOrigin())
                            .ifPresent(origin -> queryWrapper.like("origin", origin));
                });

        return productMapper.paginate(pageParam.getCurrent(), pageParam.getPageSize(), queryWrapper);
    }
}
