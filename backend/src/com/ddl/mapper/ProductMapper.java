package com.ddl.mapper;

import com.ddl.entity.Product;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

public interface ProductMapper extends BaseMapper<Product> {
    int updateById(long id);

    //@param attribute 属性名称，如 "id", "name", "price" 等
    //@param value 属性值
    @Select("<script>" +
            "SELECT * FROM product " +
            "<where> " +
            "<choose> " +
            "<when test='attribute == \"id\"'> " +
            "id = #{value} " +
            "</when> " +
            "<when test='attribute == \"name\"'> " +
            "name LIKE CONCAT('%', #{value}, '%') " +
            "</when> " +
            "<when test='attribute == \"price\"'> " +
            "price = #{value} " +
            "</when> " +
            "<when test='attribute == \"origin\"'> " +
            "origin LIKE CONCAT('%', #{value}, '%') " +
            "</when> " +
            "<when test='attribute == \"category\"'> " +
            "category LIKE CONCAT('%', #{value}, '%') " +
            "</when> " +
            "<when test='attribute == \"stock\"'> " +
            "stock LIKE CONCAT('%', #{value}, '%') " +
            "</when> " +
            "<when test='attribute == \"brand\"'> " +
            "brand LIKE CONCAT('%', #{value}, '%') " +
            "</when> " +
            "<when test='attribute == \"productionDate\"'> " +
            "productionDate = #{value} "+
            "</when> " +
            "<when test='attribute == \"shelfLife\"'> " +
            "shelfLife = #{value} "+
            "    <!-- 其他条件分支 -->" +
            "</when> " +
            "</choose> " +
            "</where>" +
            "</script>")
    Optional<Product> selectByAttribute(@Param("attribute") String attribute, @Param("value") String value);
}
