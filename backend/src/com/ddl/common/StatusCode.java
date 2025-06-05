package com.ddl.common;

import lombok.Getter;

@Getter
public enum StatusCode {
    REGISTER_SUCCESS(10000, "注册成功", "ok"),
    REGISTER_USERNAME_DUPLICATE(10001, "注册失败，用户已存在", "error"),
    LOGIN_SUCCESS(20000, "登录成功", "ok"),
    LOGIN_FAIL(20001, "登录失败，用户名或密码错误", "error"),
    USER_NOT_FOUND(30000, "未知用户名", "error"),
    OLD_PASSWORD_INCORRECT(30001,"旧密码错误","error"),
    PASSWORD_UPDATE_SUCCESS(30002, "密码修改成功", "ok"),
    PASSWORD_UPDATE_FAILED(30003, "密码修改失败，请致电管理员", "error"),
    ACCOUNT_DELETION_SUCCESS(40000, "账户注销成功", "ok"),
    ACCOUNT_DELETION_FAILED(40001, "账户注销失败，请致电管理员", "error"),
    PRODUCT_ADD_SUCCESS(50000, "商品添加成功", "ok"),
    PRODUCT_ADD_FAILED(50001, "商品添加失败", "error"),
    PRODUCT_DELETE_SUCCESS(50002, "商品删除成功", "ok"),
    PRODUCT_DELETE_FAILED(50003, "商品删除失败", "error"),
    PRODUCT_NOT_FOUND(50004, "商品信息不存在", "error"),
    PRODUCT_UPDATE_SUCCESS(50005, "商品信息更新成功", "ok"),
    PRODUCT_UPDATE_FAILED(50006, "商品信息更新失败", "error"),
    CHECKOUT_FAILURE_USER_NOT_AUTHENTICATED(60000,"",""),
    INTERNAL_SERVER_ERROR(60001,"",""),
    CHECKOUT_SUCCESS(60002,"",""),
    CHECKOUT_FAILURE_EMPTY_CART(60003,"",""),
    CHECKOUT_FAILURE_INSUFFICIENT_STOCK(60003,"",""),
    CHECKOUT_FAILURE_INVALID_ITEM(60003,"",""),
    ;


    final int code;
    final String msg;
    final String status;
    StatusCode(int code, String msg, String status) {
        this.code = code;
        this.msg = msg;
        this.status = status;
    }
}