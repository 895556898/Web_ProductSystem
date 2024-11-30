package com.ddl.mapper;

import com.ddl.entity.Product;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    // 这里可以定义额外的数据库操作方法，如果需要的话
}
