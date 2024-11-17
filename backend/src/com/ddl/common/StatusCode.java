package com.ddl.common;

import lombok.Getter;

@Getter
public enum StatusCode {
    REGISTER_SUCCESS(10000, "注册成功"),
    REGISTER_USERNAME_DUPLICATE(10001, "注册失败，用户已存在"),
    REGISTER_ERROR(10002, "注册失败，未知错误"),
    REGISTER_USERNAME_OR_PASSWORD_EMPTY(10003, "用户名或密码不能为空"),
    LOGIN_SUCCESS(20000, "登录成功"),
    LOGIN_FAIL(20001, "登录失败，用户名或密码错误");

    final int code;
    final String msg;
    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
