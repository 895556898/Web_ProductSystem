package com.ddl.entity.dto;

import lombok.Data;

/**
 * @author Shanwer
 * @createDate: 2024/11/25 20:25
 */
@Data
public class UserDTO {
    private String username;
    private String password;
    private boolean autoLogin;
}
