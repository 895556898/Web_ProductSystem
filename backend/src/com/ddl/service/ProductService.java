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
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductService {

    ProductMapper productMapper = MybatisFlexBootstrap.getInstance().getMapper(ProductMapper.class);
    ProductLogMapper productLogMapper = MybatisFlexBootstrap.getInstance().getMapper(ProductLogMapper.class);

    public static ProductService getInstance() {
        return new ProductService();
    }

    //添加商品new
    public StatusCode Add(ProductDTO productDTO , String username) {
        Product product = new Product();
        Product oldProduct = productMapper.selectOneByQuery(new QueryWrapper().eq("name", productDTO.getName()));

        AtomicInteger affectedRows1 = new AtomicInteger();           //商品表中受影响的行数
        AtomicInteger affectedRows2 = new AtomicInteger();

        //如果表中已有同名的商品
        if (productMapper.selectCountByQuery(new QueryWrapper().eq("name", productDTO.getName())) != 0) {

            //构建商品对象
            int stock = product.getStock() + oldProduct.getStock();
            product.setName(productDTO.getName());
            product.setPrice(product.getPrice());
            product.setCost(product.getCost());
            product.setOrigin(product.getOrigin());
            product.setCategory(product.getCategory());
            product.setStock(stock);                    //更新库存数量
            product.setBrand(product.getBrand());
            product.setProductionDate(product.getProductionDate());
            product.setShelfLife(product.getShelfLife());

            //构建商品操作日志对象
            ProductLog productLog = new ProductLog();
            productLog.setItemId(productDTO.getId());
            productLog.setItemName(productDTO.getName());
            productLog.setAction("更新商品信息");
            productLog.setCreatedBy(username);

            Db.tx(() -> {
                //进行事务操作
                affectedRows1.set(productMapper.updateByName(productDTO.getName()));
                affectedRows2.set(productLogMapper.insert(productLog));
                if (affectedRows1.get() > 0 && affectedRows2.get() > 0){
                    return true;
                } else {
                    return false;
                }
            });

        }
        //若表中无同名商品
        else {
            product.setName(product.getName());
            product.setPrice(product.getPrice());
            product.setCost(product.getCost());
            product.setOrigin(product.getOrigin());
            product.setCategory(product.getCategory());
            product.setStock(product.getStock());
            product.setBrand(product.getBrand());
            product.setProductionDate(product.getProductionDate());
            product.setShelfLife(product.getShelfLife());

            //构建商品操作日志对象
            ProductLog productLog = new ProductLog();
            productLog.setItemId(productDTO.getId());
            productLog.setAction("添加商品");
            productLog.setCreatedBy(username);

            Db.tx(() -> {
                //进行事务操作
                affectedRows1.set(productMapper.insert(product));
                affectedRows2.set(productLogMapper.insert(productLog));
                if (affectedRows1.get() > 0 && affectedRows2.get() > 0){
                    return true;
                } else {
                    return false;
                }
            });
        }

        if (affectedRows1.get() > 0 && affectedRows2.get() > 0) {
            return StatusCode.PRODUCT_ADD_SUCCESS;
        } else {
            return StatusCode.PRODUCT_ADD_FAILED;
        }
    }

    //删除商品new
    public StatusCode Delete(ProductDTO productDTO , String username) {
        Product product = new Product();
        long id = productDTO.getId();
        String name = productDTO.getName();
        AtomicInteger affectedRows1 = new AtomicInteger();           //商品表中受影响的行数
        AtomicInteger affectedRows2 = new AtomicInteger();

        //用户输入商品id->获取名称
        if(id > 0  && productMapper.selectCountByQuery(new QueryWrapper().eq("id",id)) != 0) {
            name = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getName();
        }
        //用户输入商品名称->获取id
        else if(name != null && productMapper.selectCountByQuery(new QueryWrapper().eq("name", name)) != 0){
            id = productMapper.selectOneByQuery(new QueryWrapper().eq("name",name)).getId();
        } else {
            return StatusCode.PRODUCT_NOT_FOUND;
        }

        ProductLog productLog = new ProductLog();
        productLog.setItemId(id);
        productLog.setItemName(name);
        productLog.setAction("删除商品");
        productLog.setCreatedBy(username);

        long finalId = id;

        Db.tx(() -> {
            affectedRows1.set(productMapper.deleteById(finalId));
            affectedRows2.set(productLogMapper.insert(productLog));
            if (affectedRows1.get() > 0 && affectedRows2.get() > 0){
                return true;
            } else {
                return false;
            }
        });

        if (affectedRows1.get() > 0 && affectedRows2.get() > 0) {
            return StatusCode.PRODUCT_DELETE_SUCCESS;
        } else {
            return StatusCode.PRODUCT_DELETE_FAILED;
        }
    }

    //更新商品new
    public StatusCode Update(ProductDTO productDTO , String username) {
        long id = productDTO.getId();
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

        //用户输入商品id->获取其他值
        if(id > 0  && productMapper.selectCountByQuery(new QueryWrapper().eq("id",id)) != 0) {
            if(name == null)
                name = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getName();
            if(price == null)
                price = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getPrice();
            if(cost == null)
                cost = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getCost();
            if(origin == null)
                origin = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getOrigin();
            if(category == null)
                category = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getCategory();
            if(stock > 0)
                stock = stock + productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getStock();
            if(brand == null)
                brand = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getBrand();
            if(productionDate == null)
                productionDate = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getProductionDate();
            if(shelfLife == null)
                shelfLife = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getShelfLife();
        }
        //用户输入商品名称->获取id->获取其他值信息
        else if(name != null && productMapper.selectCountByQuery(new QueryWrapper().eq("name", name)) != 0){
            id = productMapper.selectOneByQuery(new QueryWrapper().eq("name",name)).getId();
            if(price == null)
                price = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getPrice();
            if(cost == null)
                cost = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getCost();
            if(origin == null)
                origin = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getOrigin();
            if(category == null)
                category = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getCategory();
            if(stock > 0)
                stock = stock + productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getStock();
            if(brand == null)
                brand = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getBrand();
            if(productionDate == null)
                productionDate = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getProductionDate();
            if(shelfLife == null)
                shelfLife = productMapper.selectOneByQuery(new QueryWrapper().eq("id",id)).getShelfLife();
        } else {
            return StatusCode.PRODUCT_NOT_FOUND;
        }
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
            affectedRows1.set(productMapper.insertOrUpdate(product));
            affectedRows2.set(productLogMapper.insert(productLog));
            if (affectedRows1.get() > 0 && affectedRows2.get() > 0){
                return true;
            } else {
                return false;
            }
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
