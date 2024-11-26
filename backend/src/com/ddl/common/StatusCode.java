package com.ddl.common;

import lombok.Getter;

@Getter
public enum StatusCode {
    REGISTER_SUCCESS(10000, "注册成功", "ok"),
    REGISTER_USERNAME_DUPLICATE(10001, "注册失败，用户已存在", "error"),
    REGISTER_ERROR(10002, "注册失败，未知错误", "error"),
    REGISTER_USERNAME_OR_PASSWORD_EMPTY(10003, "用户名或密码不能为空", "error"),
    LOGIN_SUCCESS(20000, "登录成功", "ok"),
    LOGIN_FAIL(20001, "登录失败，用户名或密码错误", "error");

    final int code;
    final String msg;
    final String status;
    StatusCode(int code, String msg, String status) {
        this.code = code;
        this.msg = msg;
        this.status = status;
    }
}
