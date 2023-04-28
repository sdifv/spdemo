package com.yhao.webdemo.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@TableName("pms_user")
public class User {

    // ref: https://juejin.cn/post/7202162496036077605
    private Integer id;

    private String username;

    private String password;

    private String phone;

    private Date createTime;

    private Date updateTime;

    public User(String name, String password) {
        this.username = name;
        this.password = password;
    }


}
