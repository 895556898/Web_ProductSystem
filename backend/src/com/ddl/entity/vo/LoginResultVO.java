package com.ddl.entity.vo;

import lombok.Data;

/**
 * @author Shanwer
 * @createDate: 2024/11/25 21:01
 */
@Data
public class LoginResultVO {
    String status;
    String msg;
    String sessionID;

    public LoginResultVO(String msg, String ok, String sessionID) {
        this.msg = msg;
        this.status = ok;
        this.sessionID = sessionID;
    }
}
