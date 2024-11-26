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

    //Store in the form of base64
    private String hashedPassword;

    private String salt;

    private String email;

    private String avatar;
}
