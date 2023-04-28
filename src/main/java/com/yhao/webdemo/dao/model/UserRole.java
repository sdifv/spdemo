package com.yhao.webdemo.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("pms_user_role")
public class UserRole {
    private Integer id;

    private Integer userId;

    private Integer roleId;

    private Date createTime;

    private Date updateTime;
}
