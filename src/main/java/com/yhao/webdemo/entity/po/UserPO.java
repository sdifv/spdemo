package com.yhao.webdemo.entity.po;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class UserPO {

    // ref: https://juejin.cn/post/7202162496036077605
    private Integer id;

    private String name;

    private String password;

    public UserPO(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
