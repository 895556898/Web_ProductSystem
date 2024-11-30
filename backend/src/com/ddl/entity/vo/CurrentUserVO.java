package com.ddl.entity.vo;

import lombok.Data;

/**
 * @author Shanwer
 * @createDate: 2024/11/25 21:36
 */
@Data
public class CurrentUserVO {
    private String name;
    private String avatar;
    private long userID;
    private String email;

    public CurrentUserVO(String username, String avatar, long id, String email) {
        this.name = username;
        this.avatar = avatar;
        this.userID = id;
        this.email = email;
    }
}
