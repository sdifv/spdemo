package com.yhao.webdemo.dao.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

    // ref: https://juejin.cn/post/7202162496036077605
    private Integer id;

    private String name;

    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
