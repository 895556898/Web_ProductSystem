package com.ddl.entity.dto;

import lombok.Data;

/**
 * @author Shanwer
 * @createDate: 2024/12/4 22:00
 */
@Data
public class PageParamDTO {
    private Integer current;
    private Integer pageSize;
    private String name;
    private String origin;
}
