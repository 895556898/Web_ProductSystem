package com.ddl.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

@Data
@Table("User")
public class User{
    @Id
    private long id;

    private String username;

    private String password;

    private String salt;
}
