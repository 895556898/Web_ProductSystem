package com.ddl.common;

import lombok.Getter;

@Getter
public enum StatusCode {
    REGISTER_SUCCESS(10000, "注册成功", "ok"),
    REGISTER_USERNAME_DUPLICATE(10001, "注册失败，用户已存在", "error"),
    REGISTER_ERROR(10002, "注册失败，未知错误", "error"),
    REGISTER_USERNAME_OR_PASSWORD_EMPTY(10003, "用户名或密码不能为空", "error"),
    LOGIN_SUCCESS(20000, "登录成功", "ok"),
    LOGIN_FAIL(20001, "登录失败，用户名或密码错误", "error"),
    USER_NOT_FOUND(30000, "未知用户名", "error"),
    OLD_PASSWORD_INCORRECT(30001,"旧密码错误","error"),
    PASSWORD_UPDATE_SUCCESS(30002, "密码修改成功", "ok"),
    PASSWORD_UPDATE_FAILED(30003, "密码修改失败，请致电管理员", "error"),
    ACCOUNT_DELETION_SUCCESS(40000, "账户注销成功", "ok"),
    ACCOUNT_DELETION_FAILED(40001, "账户注销失败，请致电管理员", "error");


    final int code;
    final String msg;
    final String status;
    StatusCode(int code, String msg, String status) {
        this.code = code;
        this.msg = msg;
        this.status = status;
    }
}
