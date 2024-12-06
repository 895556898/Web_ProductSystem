package com.ddl.service;

import com.ddl.common.StatusCode;
import com.ddl.entity.ProductLog;
import com.ddl.entity.dto.ProductLogDTO;
import com.ddl.mapper.ProductLogMapper;
import com.mybatisflex.core.MybatisFlexBootstrap;
import com.mybatisflex.core.query.QueryWrapper;

public class ProductLogService {
    ProductLogMapper productLogMapper = MybatisFlexBootstrap.getInstance().getMapper(ProductLogMapper.class);

    public static ProductLogService getInstance(){
        return new ProductLogService();
    }


//    //查找
//    public StatusCode find(ProductLogDTO productLogDTO){
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("id",productLogDTO.getId());
//        ProductLog productLog = productLogMapper.selectOneByQuery(queryWrapper);
//        if(productLog == null){
//            return StatusCode.PRODUCT_LOG_NOT_FOUND;
//        } else {
//            return StatusCode.PRODUCT_LOG_FOUND_SUCCESS;
//        }
//    }
}
