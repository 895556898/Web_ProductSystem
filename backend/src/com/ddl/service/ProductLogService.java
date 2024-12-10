package com.ddl.service;

import com.ddl.entity.dto.PageParamDTO;
import com.ddl.entity.vo.ProductLogVO;
import com.ddl.mapper.ProductLogMapper;
import com.mybatisflex.core.MybatisFlexBootstrap;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;

public class ProductLogService {
    ProductLogMapper productLogMapper = MybatisFlexBootstrap.getInstance().getMapper(ProductLogMapper.class);

    public static ProductLogService getInstance(){
        return new ProductLogService();
    }

    public Page<ProductLogVO> listLog(PageParamDTO pageParam){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderBy("created_at", false);//默认降序
        return productLogMapper.paginateWithRelationsAs(pageParam.getCurrent(), pageParam.getPageSize(), queryWrapper, ProductLogVO.class);
    }
}
