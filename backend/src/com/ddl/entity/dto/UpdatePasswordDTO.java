package com.ddl.entity.dto;

import lombok.Data;

@Data
public class UpdatePasswordDTO {
    private String username;
    private String oldPassword;
    private String newPassword;
}
