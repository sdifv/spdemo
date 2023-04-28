package com.yhao.webdemo.controller.model;

public enum ResultEnum {

    LOGIN_SUCCESS("登录成功"),
    LOGOUT_SUCCESS("登出成功"),
    USERINFO_ERROR("用户名或密码错误"),
    CAPTCHA_ERROR("验证码错误"),
    AUTHENTICATION_FAILED("认证失败,请先登录"),
    PERMISSION_DENIED("权限不足");

    public String getMessage() {
        return message;
    }

    private String message;


    ResultEnum(String message) {
        this.message = message;
    }
}
